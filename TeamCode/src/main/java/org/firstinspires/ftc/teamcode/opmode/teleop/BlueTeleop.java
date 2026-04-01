package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.InterpolationPoints;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.AimCalculator;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.IndicatorRGB;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Interpolator;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.VelocityPair;

@TeleOp(group = "Main")
public class BlueTeleop extends LinearOpMode {
    private Drivetrain drivetrain;
    private Intake intake;
    private Shooter shooter;
    private Limelight limelight;
    private Interpolator interpolator;
    private AimCalculator aimCalculator;
    private ElapsedTime loopTimer = new ElapsedTime();
    private TelemetryManager panels = PanelsTelemetry.INSTANCE.getTelemetry();

    private IndicatorRGB indicator;

    private final boolean IS_RED = false;
    private final boolean USING_INTERPOLATION = true;

    @Override
    public void runOpMode() {


        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        limelight = new Limelight(hardwareMap);
        interpolator = new Interpolator(InterpolationPoints.rpms_2_15);
        indicator = new IndicatorRGB(hardwareMap);
        aimCalculator = new AimCalculator(drivetrain, false);

        if (IS_RED) {
            limelight.setRedGoalPipeline();
        }
        else {
            limelight.setBlueGoalPipeline();
        }


//        if (IS_RED) {
//            drivetrain.setStartingPose(RobotConstants.Auto.LAST_REMEMBERED_POSE);
//        }
//        else {
//            if (RobotConstants.Auto.LAST_REMEMBERED_POSE.getHeading() == 0) {
//                RobotConstants.Auto.LAST_REMEMBERED_POSE = RobotConstants.Auto.LAST_REMEMBERED_POSE.setHeading(Math.toRadians(180));
//            }
//            drivetrain.setStartingPose(RobotConstants.Auto.LAST_REMEMBERED_POSE);
//        }


        boolean zeroButton;
        boolean intakeButton;
        boolean barfButton;
        boolean chargeButton;
        boolean shootButton;
        boolean isShootingWhileMoving = false;

        boolean isCharged = false;
        double lowestRpm = 500;

        double turnAmt = 0;



        waitForStart();

        limelight.start();
        drivetrain.startTeleopDrive();
        drivetrain.setStartingPose(new Pose(17.5/2, 17.75/2, Math.toRadians(90)));

        while (opModeIsActive()) {
            loopTimer.reset();

            aimCalculator.update();
            drivetrain.update();

            intakeButton = (gamepad1.right_bumper || gamepad2.a);
            barfButton = gamepad1.b;
            chargeButton = gamepad2.left_bumper;
            shootButton = gamepad2.right_bumper;
            zeroButton = gamepad1.back;

            AimCalculator.ShotData shotData = aimCalculator.getShotData();

            if (gamepad1.left_bumper) {
                drivetrain.setAimedTeleopDrive(
                        gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                        gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                        false,
                        shotData.angle);
            }
            else {
                drivetrain.setTeleopDrive(
                        gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                        gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                        -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT,
                        false);
            }

            if (zeroButton) {
                if (IS_RED) {
                    drivetrain.zeroHeading();
                }
                else {
                    drivetrain.oneEightyHeading();
                }
            }

            if (intakeButton && barfButton) {
                intake.spit();
            }
            else if (intakeButton || shootButton){
                intake.run();
            }
            else if (barfButton) {
                intake.barf();
            }
            else {
                intake.stop();
            }

            if (chargeButton) {
                if (USING_INTERPOLATION) {
                    shooter.setToVelocityPair(new VelocityPair(shotData.rpm, shotData.rpm));
                }
            }
            else {
                shooter.coast();
            }

            if (intakeButton) {
                shooter.runBackPusher();
            }
            else if (shootButton) {
                shooter.runPusher();
            }
            else if (barfButton) {
                shooter.barfPusher();
            }
            else {
                shooter.stopPusher();
            }

            if (limelight.isAlignedWithGoal())
                indicator.green();
            else
                indicator.blue();

//            telemetry.addLine("SHOOTER");
//            telemetry.addData("LOWEST RPM", lowestRpm);
//            telemetry.addData("Bottom shooter vel", shooter.getVelocityBottom());
//            telemetry.addData("Top shooter vel", shooter.getVelocityTop());
//            telemetry.addLine("DRIVETRAIN");
//            telemetry.addData("X", drivetrain.getPose().getX());
//            telemetry.addData("Y", drivetrain.getPose().getY());
//            telemetry.addData("Heading", Math.toDegrees(drivetrain.getPose().getHeading()));
//            telemetry.addLine("LIMELIGHT");
//            telemetry.addData("Turn Amount", turnAmt);
//            telemetry.addData("Is aligned with goal", limelight.isAlignedWithGoal());
//            telemetry.addData("Distance to target", limelight.getDistanceTarget(IS_RED, telemetry));

            telemetry.addData("Target angle (degrees)", shotData.angle);

            telemetry.addData("Target rpm", shotData.rpm);
            telemetry.addData("Distance from target", shotData.distance);

            telemetry.addData("X", drivetrain.getPose().getX());
            telemetry.addData("Y", drivetrain.getPose().getY());
            telemetry.addData("Heading", Math.toDegrees(drivetrain.getPose().getHeading()));

            telemetry.addData("Timer Loop", loopTimer.milliseconds());

            panels.addData("Current RPM bottom", shooter.getVelocityBottom());
            panels.addData("Current RPM top", shooter.getVelocityTop());

            panels.update(telemetry);
            telemetry.update();

        }

        // update last remembered pose
        RobotConstants.Auto.LAST_REMEMBERED_POSE = drivetrain.getPose();
    }
}
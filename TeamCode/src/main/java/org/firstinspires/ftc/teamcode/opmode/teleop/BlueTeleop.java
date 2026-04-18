package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.CoordinateSystem;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.AimCalculator;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.IndicatorRGB;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.VelocityPair;

@TeleOp(group = "Main")
public class BlueTeleop extends LinearOpMode {
    private Drivetrain drivetrain;
    private Intake intake;
    private Shooter shooter;
    private AimCalculator aimCalculator;
    private ElapsedTime loopTimer = new ElapsedTime();
    private MultipleTelemetry multipleTelemetry;

    private IndicatorRGB indicator;

    private boolean red = false;

    public void setAllianceColor(boolean red) {
        this.red = red;
    }

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        indicator = new IndicatorRGB(hardwareMap);
        aimCalculator = new AimCalculator(drivetrain, this.red);
        multipleTelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        boolean zeroButton;
        boolean intakeButton;
        boolean barfButton;
        boolean chargeButton;
        boolean shootButton;

        waitForStart();

        drivetrain.startTeleopDrive();

        if (RobotConstants.AutoPaths.HAS_POSE) {
            drivetrain.setStartingPose(RobotConstants.AutoPaths.LAST_REMEMBERED_POSE);
        }
        else {
            drivetrain.setStartingPose(new Pose(17.5/2, 17.75/2, Math.toRadians(90)));
        }

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
                        gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT * (red ? -1 : 1),
                        gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT * (red ? -1 : 1),
                        shotData.angle);
            }
            else {
                drivetrain.setTeleopDrive(
                        gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT * (red ? -1 : 1),
                        gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT * (red ? -1 : 1),
                        -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT,
                        false);
            }

            if (zeroButton) {
                if (red) {
                    drivetrain.zeroHeading();
                }
                else {
                    drivetrain.oneEightyHeading();
                }
            }

            if (gamepad1.back) {
                drivetrain.setPose(new Pose(17.5/2, 17.75/2, Math.toRadians(90)));
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

            if (chargeButton)
                shooter.setToVelocityPair(new VelocityPair(shotData.rpm, shotData.rpm));
            else
                shooter.coast();


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

            multipleTelemetry.addData("Target angle (degrees)", shotData.angle);

            multipleTelemetry.addData("Target rpm", shotData.rpm);
            multipleTelemetry.addData("Actual Bottom rpm", shooter.getVelocityBottom());
            multipleTelemetry.addData("Actual top rpm", shooter.getVelocityTop());
            multipleTelemetry.addData("Distance from target", shotData.distance);


            multipleTelemetry.addData("Robot x", pedroToAdvScope(drivetrain.getPose()).getX());
            multipleTelemetry.addData("Robot y", pedroToAdvScope(drivetrain.getPose()).getY());
            multipleTelemetry.addData("Robot heading", pedroToAdvScope(drivetrain.getPose()).getHeading());

            multipleTelemetry.addData("Timer Loop", loopTimer.milliseconds());

            multipleTelemetry.update();

        }

        // update last remembered pose
        RobotConstants.AutoPaths.LAST_REMEMBERED_POSE = drivetrain.getPose();
        RobotConstants.AutoPaths.HAS_POSE = true;
    }

    public Pose pedroToAdvScope(Pose pose) {
        Pose ret = pose.unaryMinus().plus(new Pose(144, 144, 0));
        ret = ret.setHeading(ret.getHeading() * -1).getAsCoordinateSystem(FTCCoordinates.INSTANCE);
        return ret;
    }
}
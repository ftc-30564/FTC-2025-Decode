package org.firstinspires.ftc.teamcode.opmode.teleop;

import static org.firstinspires.ftc.teamcode.util.Poses.pedroToAdvScope;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.AimCalculator;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.IndicatorRGB;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.Logging;
import org.firstinspires.ftc.teamcode.util.VelocityPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TeleOp(group = "Main")
public class BlueTeleop extends LinearOpMode {
    private static final Logger log = LoggerFactory.getLogger(BlueTeleop.class);
    private Drivetrain drivetrain;
    private Intake intake;
    private Shooter shooter;
    private AimCalculator aimCalculator;
    private ElapsedTime loopTimer = new ElapsedTime();
    private MultipleTelemetry multipleTelemetry;
    private TelemetryPacket telemetryPacket;
    private LynxModule controlHub;
    private LynxModule expansionHub;

    private Logging logging;
    private static boolean IS_DEBUGGING = false;

    private IndicatorRGB indicator;

    private boolean red = false;
    private AimCalculator.ShotData shotData;

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
        telemetryPacket = new TelemetryPacket();

        boolean zeroButton = false;
        boolean intakeButton = false;
        boolean barfButton = false;
        boolean aimButton = false;
        boolean chargeButton;
        boolean shootButton;
        boolean isAimed = false;

        logging = new Logging(drivetrain, shooter, hardwareMap);

        waitForStart();

        drivetrain.startTeleopDrive();

        if (!RobotConstants.Drive.HAS_POSE) {
            drivetrain.setStartingPose(new Pose(17.5/2, 17.75/2, Math.toRadians(90)));
        }

        while (opModeIsActive()) {
            loopTimer.reset();

            drivetrain.update();

            intakeButton = (gamepad1.right_bumper || gamepad2.a);
            barfButton = gamepad1.b;
            chargeButton = gamepad2.left_bumper;
            shootButton = gamepad2.right_bumper;
            zeroButton = gamepad1.back;
            aimButton = gamepad1.left_bumper;

            aimCalculator.update();
            shotData = aimCalculator.getShotData();

            if (aimButton) {
                isAimed = drivetrain.setAimedTeleopDrive(
                        gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT * (red ? -1 : 1),
                        gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT * (red ? -1 : 1),
                        shotData.angle);
            }
            else {
                isAimed = false;
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
            else if (shootButton && isAimed) {
                shooter.runPusher();
            }
            else if (barfButton) {
                shooter.barfPusher();
            }
            else {
                shooter.stopPusher();
            }


            if (isAimed) {
                indicator.green();
            }
            else {
                indicator.blue();
            }

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

            if (IS_DEBUGGING) {
                logging.updateTelemetryPacket(telemetryPacket);

                telemetryPacket.put("Aim x", pedroToAdvScope(shotData.pose).getX());
                telemetryPacket.put("Aim y", pedroToAdvScope(shotData.pose).getY());
                telemetryPacket.put("Aim heading", pedroToAdvScope(shotData.pose).getHeading());

                telemetryPacket.put("Shooter/Target angle (degrees)", shotData.angle);
                telemetryPacket.put("Shooter/Target rpm", shotData.rpm);

                telemetryPacket.put("Shooter/Distance from target", shotData.distance);

                telemetryPacket.put("Timer Loop", loopTimer.milliseconds());

                FtcDashboard.getInstance().sendTelemetryPacket(telemetryPacket);
            }
        }

        // update last remembered pose
        RobotConstants.Drive.HAS_POSE = true;
    }
}
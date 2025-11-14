package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Webcam;
import org.firstinspires.ftc.teamcode.util.VelocityPair;
// http://limelight.local:5801
@TeleOp(group = "Main")
public class BlueTeleop extends LinearOpMode {
    private Drivetrain drivetrain;
    private Intake intake;
    private Shooter shooter;
    private Limelight limelight;

    private final boolean IS_RED = false;

    public enum ShootingPosition {
        CLOSE(RobotConstants.Shooter.CLOSE_VELOCITY),
        FAR(RobotConstants.Shooter.FAR_VELOCITY);

        VelocityPair vel;
        ShootingPosition(VelocityPair vel) {
            this.vel = vel;
        }
    }

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        limelight = new Limelight(hardwareMap);

        limelight.setBlueGoalPipeline();

        if (RobotConstants.Auto.LAST_REMEMBERED_POSE.getHeading() == 0) {
            RobotConstants.Auto.LAST_REMEMBERED_POSE = RobotConstants.Auto.LAST_REMEMBERED_POSE.setHeading(Math.toRadians(180));
        }
        drivetrain.setStartingPose(RobotConstants.Auto.LAST_REMEMBERED_POSE);

        ShootingPosition currentPosition = ShootingPosition.CLOSE;
        boolean holding = false;
        boolean zeroButton;
        boolean intakeButton;
        boolean barfButton;
        boolean chargeButton;
        boolean shootButton;
        boolean alignButton;

        double turnAmt = 0;

        waitForStart();

        limelight.start();
        drivetrain.startTeleopDrive();
        drivetrain.resetImu();

        while (opModeIsActive()) {
            intakeButton = (gamepad1.right_bumper || gamepad2.a);
            barfButton = gamepad1.b;
            chargeButton = gamepad2.left_bumper;
            shootButton = gamepad2.right_bumper;
            zeroButton = gamepad1.back;

            drivetrain.update();

            if (true) {
                if (gamepad1.left_bumper) {
                    turnAmt = drivetrain.setGoalCentricDrive(
                            -gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                            -gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                            limelight.getOffsetTarget());
                }
                else {
                    drivetrain.setTeleopDrive(
                            -gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                            -gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                            -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT,
                            false);
                }
            }

            if (zeroButton)
                drivetrain.oneEightyHeading();


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

//            if (gamepad2.rightBumperWasPressed()) {
//                drivetrain.setHoldPoint();
//                drivetrain.holdPoint();
//                holding = true;
//            }
//            if (gamepad2.rightBumperWasReleased()) {
//                drivetrain.startTeleopDrive();
//                holding = false;
//            }

            if (gamepad2.dpad_up) {
                currentPosition = ShootingPosition.CLOSE;
            }
            if (gamepad2.dpad_down) {
                currentPosition = ShootingPosition.FAR;
            }

            if (chargeButton) {
                shooter.setToVelocityPair(currentPosition.vel);
            }
            else {
                shooter.setTopShooterToVelocity(0);
                shooter.setBottomShooterToVelocity(0);
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

            telemetry.addData("Bottom shooter vel", shooter.getVelocityBottom());
            telemetry.addData("Top shooter vel", shooter.getVelocityTop());
            telemetry.addData("Robot X", drivetrain.getPose().getX());
            telemetry.addData("Robot Y", drivetrain.getPose().getY());
            telemetry.addData("Robot Heading", Math.toDegrees(drivetrain.getPose().getHeading()));
            telemetry.addData("Robot IMU Heading", drivetrain.getImuAngleDegrees());

            telemetry.addData("Turn Amount", turnAmt);
            telemetry.update();

        }

        // update last remembered pose
        RobotConstants.Auto.LAST_REMEMBERED_POSE = drivetrain.getPose();
    }
}
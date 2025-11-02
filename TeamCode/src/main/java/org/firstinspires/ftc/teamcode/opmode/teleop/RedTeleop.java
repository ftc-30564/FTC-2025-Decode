package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Webcam;
import org.firstinspires.ftc.teamcode.util.VelocityPair;

@TeleOp(group = "Main")
public class RedTeleop extends LinearOpMode {
    private Drivetrain drivetrain;
    private Intake intake;
    private Shooter shooter;

    private final boolean IS_RED = true;

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


        drivetrain.setStartingPose(new Pose(0, 0, 0));

        ShootingPosition currentPosition = ShootingPosition.CLOSE;
        boolean holding = false;
        boolean zeroButton;
        boolean intakeButton;
        boolean barfButton;
        boolean chargeButton;
        boolean shootButton;
        boolean alignButton;

        waitForStart();

        drivetrain.startTeleopDrive();

        while (opModeIsActive()) {
            intakeButton = (gamepad1.right_bumper || gamepad2.a);
            barfButton = gamepad1.b;
            chargeButton = gamepad2.left_bumper;
            shootButton = gamepad2.right_bumper;
            zeroButton = gamepad1.back;

            telemetry.addData("Bottom shooter vel", shooter.getVelocityBottom());
            telemetry.addData("Top shooter vel", shooter.getVelocityTop());

            drivetrain.update();

            double turnAmt = -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT;

            if (!holding) {
                drivetrain.setTeleopDrive(
                        -gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                        -gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                        turnAmt,
                        false);
                telemetry.addData("Teleop drive", "running");
            }

            if (zeroButton)
                drivetrain.zeroHeading();


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

            if (gamepad2.rightBumperWasPressed()) {
                drivetrain.setHoldPoint();
                drivetrain.holdPoint();
                holding = true;
            }
            if (gamepad2.rightBumperWasReleased()) {
                drivetrain.startTeleopDrive();
                holding = false;
            }

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
            else {
                shooter.stopPusher();
            }



            telemetry.update();

        }
    }
}
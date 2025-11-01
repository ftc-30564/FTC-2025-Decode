package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp
public class MainTeleop extends LinearOpMode {
    Drivetrain drivetrain;
    Intake intake;
    Shooter shooter;

    public enum ShootingPosition {
        MIDDLE(RobotConstants.Shooter.MANUAL_MIDDLE_VELOCITY),
        FAR(RobotConstants.Shooter.MANUAL_FAR_VELOCITY);

        double vel;
        ShootingPosition(double vel) {
            this.vel = vel;
        }
    }

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);

        drivetrain.setStartingPose(new Pose(0, 0, 0));

        ShootingPosition currentPosition = ShootingPosition.MIDDLE;
        boolean holding = false;

        boolean intakeButton;
        boolean barfButton;
        boolean chargeButton;
        boolean shootButton;

        waitForStart();

        drivetrain.startTeleopDrive();

        while (opModeIsActive()) {
            intakeButton = (gamepad1.right_bumper || gamepad2.a);
            barfButton = gamepad1.left_bumper;
            chargeButton = gamepad2.left_bumper;
            shootButton = gamepad2.right_bumper;

            telemetry.addData("Bottom shooter vel", shooter.getVelocityBottom());
            telemetry.addData("Top shooter vel", shooter.getVelocityTop());

            drivetrain.update();

            if (!holding) {
                drivetrain.setTeleopDrive(
                        -gamepad1.left_stick_y * RobotConstants.Drivetrain.FORWARD_SPEEDLIMIT,
                        -gamepad1.left_stick_x * RobotConstants.Drivetrain.STRAFE_SPEEDLIMIT,
                        -gamepad1.right_stick_x * RobotConstants.Drivetrain.TURN_SPEEDLIMIT,
                        false);
            }
            else {
                drivetrain.holdPoint();
            }


            if (intakeButton || shootButton){
                intake.run();
            }
            else if (barfButton) {
                intake.barf();
            }
            else {
                intake.stop();
            }
            else if (intakeButton && barfButton) {
                intake.spit();
            }
            else {
                intake.stop();
            }

            if (gamepad2.rightBumperWasPressed()) {
                drivetrain.setHoldPoint();
                holding = true;
            }
            if (gamepad2.rightBumperWasReleased()) {
                holding = false;
            }

            if (gamepad2.dpad_up) {
                currentPosition = ShootingPosition.MIDDLE;
            }
            if (gamepad2.dpad_down) {
                currentPosition = ShootingPosition.FAR;
            }

            if (chargeButton) {
                shooter.setTopShooterToVelocity(currentPosition.vel);
                shooter.setBottomShooterToVelocity(currentPosition.vel);
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
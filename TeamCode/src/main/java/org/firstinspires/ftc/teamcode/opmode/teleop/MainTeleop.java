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

        waitForStart();

        drivetrain.startTeleopDrive();

        while (opModeIsActive()) {
            telemetry.addData("Bottom shooter vel", shooter.getVelocityBottom());
            telemetry.addData("Top shooter vel", shooter.getVelocityTop());

            drivetrain.update();

            drivetrain.setTeleopDrive(
                    -gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                    -gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                    -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT,
                    false);

            if (gamepad1.right_bumper || gamepad2.a){
                intake.run();
            }
            else if (gamepad1.left_bumper || gamepad2.b) {
                intake.barf();
            }
            else {
                intake.stop();
            }

            if (gamepad2.dpad_up) {
                currentPosition = ShootingPosition.MIDDLE;
            }
            if (gamepad2.dpad_down) {
                currentPosition = ShootingPosition.FAR;
            }

            if (gamepad2.left_bumper) {
                shooter.setTopShooterToVelocity(currentPosition.vel);
                shooter.setBottomShooterToVelocity(currentPosition.vel);
            }
            else {
                shooter.setTopShooterToVelocity(0);
                shooter.setBottomShooterToVelocity(0);
            }

            if (gamepad2.right_bumper) {
                shooter.runPusher();
            }
            else {
                shooter.stopPusher();
            }

            telemetry.update();

        }
    }
}
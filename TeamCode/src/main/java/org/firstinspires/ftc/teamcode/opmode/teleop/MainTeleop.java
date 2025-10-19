package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp
public class MainTeleop extends LinearOpMode {

    public enum ShootingPosition {
        CLOSE(RobotConstants.Shooter.MANUAL_CLOSE_VELOCITY),
        MIDDLE(RobotConstants.Shooter.MANUAL_MIDDLE_VELOCITY),
        FAR(RobotConstants.Shooter.MANUAL_FAR_VELOCITY);

        double vel;
        ShootingPosition(double vel) {
            this.vel = vel;
        }
    }

    @Override
    public void runOpMode() {
        Drivetrain drivetrain = new Drivetrain(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);

        ShootingPosition currentPosition = ShootingPosition.MIDDLE;

        drivetrain.setStartingPose(new Pose(0, 0, 0));
        drivetrain.update();

        waitForStart();

        drivetrain.startTeleopDrive();
        while (opModeIsActive()) {
            telemetry.addData("Bottom shooter vel", shooter.getVelocityBottom());
            telemetry.addData("Top shooter vel", shooter.getVelocityTop());

            drivetrain.update();

            drivetrain.setTeleopDrive(
                    -gamepad1.left_stick_y * RobotConstants.Drivetrain.FORWARD_SPEEDLIMIT,
                    -gamepad1.left_stick_x * RobotConstants.Drivetrain.STRAFE_SPEEDLIMIT,
                    -gamepad1.right_stick_x * RobotConstants.Drivetrain.TURN_SPEEDLIMIT,
                    true);

            if (gamepad1.right_bumper){
                intake.run();
            }
            else if (gamepad1.left_bumper) {
                intake.barf();
            }
            else {
                intake.stop();
            }
            if (gamepad2.dpad_up) {
                currentPosition = ShootingPosition.CLOSE;
            }
            if (gamepad2.dpad_right) {
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
            }
            if (gamepad2.right_bumper) {
                shooter.runPusher();
            }
            else {
                shooter.stopPusher();
            }
            shooter.setBottomShooterToVelocity(200);
            shooter.setTopShooterToVelocity(200);

            telemetry.update();

        }
    }
}
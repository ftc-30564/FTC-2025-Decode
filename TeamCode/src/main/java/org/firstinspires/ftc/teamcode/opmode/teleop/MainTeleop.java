package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp
public class MainTeleop extends LinearOpMode {

    @Override
    public void runOpMode() {
        Drivetrain drivetrain = new Drivetrain(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);

        drivetrain.setStartingPose(new Pose(0, 0, 0));
        drivetrain.update();

        waitForStart();

        drivetrain.startTeleopDrive();
        while (opModeIsActive()) {
            telemetry.addData("Left shooter vel", shooter.getVelocityLeft());
            telemetry.addData("Right shooter vel", shooter.getVelocityRight());

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
            shooter.setPercent(gamepad1.right_stick_y);
            if (gamepad1.dpad_up){
                intake.pusher();
            } else {
                intake.pusherStop();
            }
            telemetry.update();

        }
    }
}
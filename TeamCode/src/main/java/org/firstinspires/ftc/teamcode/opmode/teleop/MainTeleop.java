package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@TeleOp
public class MainTeleop extends LinearOpMode {

    @Override
    public void runOpMode() {
        Follower follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose());
        follower.update();

        waitForStart();

        follower.startTeleopDrive();
        while (opModeIsActive()) {
            follower.setTeleOpDrive(
                    -gamepad1.left_stick_y,
                    -gamepad1.left_stick_x,
                    -gamepad1.right_stick_x,
                    false);
            follower.update();
        }
    }
}

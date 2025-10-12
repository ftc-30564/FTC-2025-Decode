package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp
public class NoDriveTeleop extends LinearOpMode {

    @Override
    public void runOpMode() {
        Intake intake = new Intake(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Left shooter vel", shooter.getVelocityLeft());
            telemetry.addData("Right shooter vel", shooter.getVelocityRight());
            telemetry.addData("Left at target", shooter.leftIsAtVelocity(200));
            telemetry.addData("Right at target", shooter.rightIsAtVelocity(200));


            if (gamepad1.right_bumper){
                intake.run();
            }// 152.47   178.32
            else if (gamepad1.left_bumper) {
                intake.barf();
            }
            else {
                intake.stop();
            }

            shooter.setLeftShooterToVelocity(200);
            shooter.setRightShooterToVelocity(200);

            if (gamepad1.dpad_up){
                intake.pusher();
            } else {
                intake.pusherStop();
            }
            telemetry.update();

        }
    }
}
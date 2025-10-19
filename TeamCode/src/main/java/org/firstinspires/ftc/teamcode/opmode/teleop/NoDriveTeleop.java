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
            telemetry.addData("Left shooter vel", shooter.getVelocityBottom());
            telemetry.addData("Right shooter vel", shooter.getVelocityTop());
            telemetry.addData("Left at target", shooter.bottomIsAtVelocity(200));
            telemetry.addData("Right at target", shooter.topIsAtVelocity(200));


            if (gamepad1.right_bumper){
                intake.run();
            }// 152.47   178.32
            else if (gamepad1.left_bumper) {
                intake.barf();
            }
            else {
                intake.stop();
            }

            shooter.setBottomShooterToVelocity(200);
            shooter.setTopShooterToVelocity(200);

            if (gamepad1.dpad_up){
                intake.pusher();
            } else {
                intake.pusherStop();
            }
            telemetry.update();

        }
    }
}
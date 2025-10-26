package org.firstinspires.ftc.teamcode.opmode.teleop.debug;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp
public class FlywheelTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        Shooter shooter = new Shooter(hardwareMap, telemetry);
        Intake intake = new Intake(hardwareMap);

        double topVel = 0;
        double botVel = 0;

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("top vel", topVel);
            telemetry.addData("bot vel", botVel);

            if (gamepad1.x) {
                intake.run();
            }
            else {
                intake.stop();
            }

            if (gamepad1.dpadUpWasPressed()) {
                botVel += 5;
            }
            if (gamepad1.dpadDownWasPressed()) {
                botVel -= 5;
            }
            if (gamepad1.aWasPressed()) {
                topVel -= 5;
            }
            if (gamepad1.yWasPressed()) {
                topVel += 5;
            }

            if (gamepad1.right_bumper) {
                shooter.runPusher();
            }
            else {
                shooter.stopPusher();
            }

            shooter.setBottomShooterToVelocity(botVel);
            shooter.setTopShooterToVelocity(topVel);

            telemetry.update();
        }
    }
}

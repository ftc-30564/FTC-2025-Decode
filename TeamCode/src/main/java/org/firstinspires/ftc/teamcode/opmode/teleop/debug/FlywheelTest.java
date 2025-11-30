package org.firstinspires.ftc.teamcode.opmode.teleop.debug;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.InterpolationPoints;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Interpolator;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp(group = "Tests")
public class FlywheelTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        Shooter shooter = new Shooter(hardwareMap, telemetry);
        Intake intake = new Intake(hardwareMap);
        Limelight limelight = new Limelight(hardwareMap);
        Interpolator interpolator = new Interpolator(InterpolationPoints.points);

        limelight.setRedGoalPipeline();

        double topVel = 0;
        double botVel = 0;

        waitForStart();

        limelight.start();

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
//            shooter.setPercent(0.4);

            telemetry.addData("Limelight distance", limelight.getDistanceTarget(true, telemetry));
            telemetry.addData("Interpolator velocity", interpolator.getVelocity(limelight.getDistanceTarget(false, telemetry)));
            telemetry.addData("top vel actual", shooter.getVelocityTop());
            telemetry.addData("bot vel actual", shooter.getVelocityBottom());
            telemetry.addData("voltage", hardwareMap.voltageSensor.iterator().next().getVoltage());


            telemetry.update();
        }
    }
}

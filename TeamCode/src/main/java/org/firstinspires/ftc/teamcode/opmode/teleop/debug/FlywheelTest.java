package org.firstinspires.ftc.teamcode.opmode.teleop.debug;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.InterpolationPoints;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.AimCalculator;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Interpolator;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp(group = "Tests")
public class FlywheelTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        Drivetrain drivetrain = new Drivetrain(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap, telemetry);
        Intake intake = new Intake(hardwareMap);
        AimCalculator aimCalculator = new AimCalculator(drivetrain, true);

        double topVel = 0;
        double botVel = 0;

        waitForStart();

        drivetrain.startTeleopDrive();
        drivetrain.setStartingPose(new Pose(17.5/2, 17.75/2, Math.toRadians(90)));

        while (opModeIsActive()) {
            aimCalculator.update();
            drivetrain.update();

            drivetrain.setTeleopDrive(
                    gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                    gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                    -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT,
                    false);

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

            telemetry.addData("top vel actual", shooter.getVelocityTop());
            telemetry.addData("bot vel actual", shooter.getVelocityBottom());
            telemetry.addData("Distance to target", aimCalculator.getShotData().distance);
            telemetry.addData("voltage", hardwareMap.voltageSensor.iterator().next().getVoltage());


            telemetry.update();
        }
    }
}

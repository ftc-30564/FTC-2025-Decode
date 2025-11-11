package org.firstinspires.ftc.teamcode.opmode.teleop.debug;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp
public class ShooterVelocityTuner extends LinearOpMode {
    private Shooter shooter;
    private final double targetVelocity = 200;
    private boolean isRunning = false;
    private boolean isMeasuring = false;

    private double kP = RobotConstants.Shooter.VELOCITY_TOP_P;
    private double kI = 0;
    private double kD = 0;

    private double initialMeasure = 0;
    private double derivativeMeasure = 0;

    @Override
    public void runOpMode() {
        shooter = new Shooter(hardwareMap, telemetry);

        while (opModeIsActive()) {
            if (gamepad1.aWasPressed()) {
                isRunning = !isRunning;
            }
            if (gamepad1.yWasPressed()) {
                isMeasuring = !isMeasuring;
                if (isMeasuring) {
                    initialMeasure = shooter.getVelocityTop();
                }
                else {
                    initialMeasure = 0;
                    derivativeMeasure = 0;
                }
            }


            if (isRunning) {
                if (isMeasuring) {
                    telemetry.addData("Initial Measure", initialMeasure);
                    telemetry.addData("Derivative Measure", derivativeMeasure);

                    if (derivativeMeasure < Math.abs(shooter.getVelocityTop() - initialMeasure))
                        derivativeMeasure = Math.abs(shooter.getVelocityTop() - initialMeasure);

                }
                shooter.setTopShooterToVelocity(targetVelocity);

                if (gamepad1.dpadUpWasPressed())
                    kP += 0.0001;
                if (gamepad1.dpadDownWasPressed())
                    kP -= 0.0001;
                if (gamepad1.dpadLeftWasPressed())
                    kI += 0.00001;
                if (gamepad1.dpadRightWasPressed())
                    kI -= 0.00001;
                if (gamepad1.rightBumperWasPressed())
                    kD += 0.00001;
                if (gamepad1.leftBumperWasPressed())
                    kD -= 0.00001;

                shooter.updateTopShooterPIDF(kP, kI, kD);
            }
            else {
                shooter.setPercent(0);
            }

            telemetry.addData("kP", kP);
            telemetry.addData("kI", kI);
            telemetry.addData("kD", kD);
            telemetry.update();
        }
    }
}

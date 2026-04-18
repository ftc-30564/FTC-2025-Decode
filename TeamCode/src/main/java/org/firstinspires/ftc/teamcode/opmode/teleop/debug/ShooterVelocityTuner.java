package org.firstinspires.ftc.teamcode.opmode.teleop.debug;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp
public class ShooterVelocityTuner extends LinearOpMode {
    private Shooter shooter;
    private final double targetVelocity = 200;
    private boolean isRunning = false;
    private boolean isMeasuring = false;
    private ElapsedTime timer = new ElapsedTime();
    private double measuredTime = 0;

    private double kP = RobotConstants.Shooter.VELOCITY_TOP_P_STANDBY;

    private double derivativeMeasure = 0;

    @Override
    public void runOpMode() {
        shooter = new Shooter(hardwareMap, telemetry);

        while (opModeIsActive()) {
            if (gamepad1.aWasPressed()) {
                isRunning = !isRunning;
                if (isRunning) {
                    timer.reset();
                }
            }
            if (gamepad1.yWasPressed()) {
                isMeasuring = !isMeasuring;
                if (!isMeasuring) {
                    derivativeMeasure = 0;
                }
            }


            if (isRunning) {
                if (isMeasuring) {
                    telemetry.addData("Derivative Measure", derivativeMeasure);

                    if (Math.abs(targetVelocity - shooter.getVelocityTop()) > derivativeMeasure)
                        derivativeMeasure = Math.abs(targetVelocity - shooter.getVelocityTop());

                }
                shooter.setTopShooterToVelocity(targetVelocity);
                if ((shooter.getVelocityTop() > targetVelocity) && (measuredTime == 0)) {
                    measuredTime = timer.milliseconds();
                }

                if (gamepad1.dpadUpWasPressed())
                    kP += 0.0001;
                if (gamepad1.dpadDownWasPressed())
                    kP -= 0.0001;

            }
            else {
                shooter.setPercent(0);
            }

            telemetry.addData("Time", measuredTime);
            telemetry.addData("Current pos", shooter.getVelocityTop());
            telemetry.addData("kP", kP);
            telemetry.update();
        }
    }
}

package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

// Test command to make sure the library works properly
public class WriteToScreenCommand extends Command {
    private Telemetry telemetry;
    private ElapsedTime time = new ElapsedTime();
    private String message;
    private double ms;

    public WriteToScreenCommand(Telemetry telemetry, String message, double ms) {
        this.telemetry = telemetry;
        this.message = message;
        this.ms = ms;
    }

    @Override
    public void initialize() {
        time.reset();
    }

    @Override
    public void loop() {
        telemetry.addData("MESSAGE", message);
    }

    @Override
    public boolean isFinished() {
        return time.milliseconds() >= ms;
    }
}

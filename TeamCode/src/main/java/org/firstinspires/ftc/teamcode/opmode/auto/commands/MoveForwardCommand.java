package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

// Example command
public class MoveForwardCommand extends Command {
    private Drivetrain drivetrain;
    private ElapsedTime time = new ElapsedTime();
    private double speed;
    private double ms;

    public MoveForwardCommand(Drivetrain drivetrain, double speed, double ms) {
        this.drivetrain = drivetrain;
        this.speed = speed;
        this.ms = ms;
    }

    @Override
    public void initialize() {
        time.reset();
    }

    @Override
    public void loop() {
        this.drivetrain.setMecanumDrive(speed, 0, 0);
    }

    @Override
    public void end() {
        this.drivetrain.setMecanumDrive(0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return time.milliseconds() >= ms;
    }
}

package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class RawDrivetrainCommand extends Command {
    private Drivetrain drivetrain;
    private double forward;
    private double strafe;
    private double turn;

    public RawDrivetrainCommand(Drivetrain drivetrain, double forward, double strafe, double turn) {
        this.forward = forward;
        this.strafe = strafe;
        this.turn = turn;

        this.drivetrain = drivetrain;
    }

    @Override
    public void initialize() {
        drivetrain.startTeleopDrive();
    }

    @Override
    public void loop() {
        drivetrain.setTeleopDrive(forward, strafe, turn, true);
    }

    @Override
    public void end() {
        drivetrain.setTeleopDrive(0, 0, 0, true);
    }
}

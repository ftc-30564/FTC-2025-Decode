package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class HoldPositionCommand extends Command {
    private Drivetrain drivetrain;

    /**
     * Command that holds the drivetrain's current position
     * @param drivetrain the drivetrain object
     */
    public HoldPositionCommand(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    @Override
    public void initialize() {
        drivetrain.setHoldPoint();
        drivetrain.holdPoint();
    }

    @Override
    public void loop() {
        drivetrain.update();
    }
}

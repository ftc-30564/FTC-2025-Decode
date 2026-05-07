package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import org.firstinspires.ftc.teamcode.util.command_lib.Command;

import java.util.function.Supplier;

public class InstantCommand extends Command {
    private final Runnable runnable;

    public InstantCommand(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void initialize() {
        this.runnable.run();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

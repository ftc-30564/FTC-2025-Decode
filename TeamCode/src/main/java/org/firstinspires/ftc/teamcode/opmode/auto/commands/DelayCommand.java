package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class DelayCommand extends Command {
    public DelayCommand(long ms) {
        this.ms = ms;
        this.isTimer = true;
    }
}

package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class DelayCommand extends Command {

    /**
     * Command that delays for a specified amount of time
     * @param ms The amount of time to delay for
     */
    public DelayCommand(long ms) {
        this.ms = ms;
        this.isTimer = true;
    }
}

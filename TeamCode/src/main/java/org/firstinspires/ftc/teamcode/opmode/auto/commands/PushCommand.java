package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class PushCommand extends Command {
    private Shooter shooter;

    public PushCommand(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override
    public void loop() {
        this.shooter.runPusher();
    }
}

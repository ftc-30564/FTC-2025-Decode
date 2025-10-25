package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.VelocityPair;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class ShootCommand extends Command {
    private Shooter shooter;
    private VelocityPair velocityPair;

    public ShootCommand(Shooter shooter, VelocityPair velocityPair) {
        this.shooter = shooter;
    }

    @Override
    public void loop() {
        shooter.setToVelocityPair(velocityPair);
        shooter.runPusher();
    }

    @Override
    public void end() {
        shooter.stopPusher();
        shooter.setPercent(0);
    }
}

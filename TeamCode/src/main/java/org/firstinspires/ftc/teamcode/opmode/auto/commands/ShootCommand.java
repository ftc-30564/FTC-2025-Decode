package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class ShootCommand extends Command {
    private Shooter shooter;
    private double vel;
    private boolean isCharged = false;

    public ShootCommand(Shooter shooter, double vel) {
        this.shooter = shooter;
        this.vel = vel;
    }

    @Override
    public void loop() {
        shooter.setBothToVelocity(vel);
        if (shooter.bothAtVelocity(vel)) {
            isCharged = true;
        }
        if (isCharged) {
            // push balls into shooter

        }

    }

    @Override
    public void end() {
        shooter.setPercent(0);
    }
}

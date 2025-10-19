package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class ChargeFlywheelCommand extends Command {
    private Shooter shooter;
    private double vel;

    public ChargeFlywheelCommand(Shooter shooter, double vel) {
        this.shooter = shooter;
        this.vel = vel;
    }

    @Override
    public void loop() {
        shooter.setBothToVelocity(vel);
    }

    @Override
    public boolean isFinished() {
        return shooter.bothAtVelocity(vel);
    }
}

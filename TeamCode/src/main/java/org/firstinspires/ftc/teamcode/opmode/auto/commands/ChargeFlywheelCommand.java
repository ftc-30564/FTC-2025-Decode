package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import org.firstinspires.ftc.teamcode.subsystems.AimCalculator;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.VelocityPair;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class ChargeFlywheelCommand extends Command {
    private final Shooter shooter;
    private final AimCalculator aimCalculator;

    /**
     * A command that charges the flywheel to prepare for shooting
     * @param shooter The shooter subsystem
     */
    public ChargeFlywheelCommand(Shooter shooter, AimCalculator aimCalculator, boolean red) {
        this.shooter = shooter;
        this.aimCalculator = aimCalculator;

        this.aimCalculator.setColor(red);
    }

    @Override
    public void loop() {
        aimCalculator.update();

        shooter.setToVelocityPair(new VelocityPair(this.aimCalculator.getShotData().rpm, this.aimCalculator.getShotData().rpm));
    }
}

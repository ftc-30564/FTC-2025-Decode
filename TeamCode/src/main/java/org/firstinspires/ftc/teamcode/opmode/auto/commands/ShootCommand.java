package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.VelocityPair;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class ShootCommand extends Command {
    private final Shooter shooter;
    private final Intake intake;

    /**
     * A command that runs the shooter
     * @param shooter The shooter object
     * @param intake The intake object
     */
    public ShootCommand(Shooter shooter, Intake intake) {
        this.shooter = shooter;
        this.intake = intake;
    }

    @Override
    public void loop() {
        shooter.runPusher();
        intake.run();
    }

    @Override
    public void end() {
        shooter.stopPusher();
        intake.stop();
    }
}

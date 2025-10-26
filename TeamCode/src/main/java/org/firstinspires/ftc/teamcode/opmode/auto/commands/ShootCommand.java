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
    private VelocityPair velocityPair;

    public ShootCommand(Shooter shooter, Intake intake, VelocityPair velocityPair) {
        this.shooter = shooter;
        this.intake = intake;
        this.velocityPair = velocityPair;
    }

    @Override
    public void loop() {
        shooter.setToVelocityPair(velocityPair);
        shooter.runPusher();
        intake.run();
    }

    @Override
    public void end() {
        shooter.stopPusher();
        intake.stop();
    }
}

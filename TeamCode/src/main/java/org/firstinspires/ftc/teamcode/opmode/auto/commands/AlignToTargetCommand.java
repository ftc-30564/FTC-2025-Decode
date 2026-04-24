package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.AimCalculator;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class AlignToTargetCommand extends Command {
    private Drivetrain drivetrain;
    private AimCalculator aimCalculator;

    public AlignToTargetCommand(Drivetrain drivetrain, boolean red) {
        this.drivetrain = drivetrain;
        this.aimCalculator = new AimCalculator(drivetrain, red);
    }

    @Override
    public void initialize() {
        drivetrain.startTeleopDrive();
    }

    @Override
    public void loop() {
        aimCalculator.update();

        drivetrain.update();
        drivetrain.setAimedTeleopDrive(0, 0, aimCalculator.getShotData().angle);
    }

    @Override
    public void end() {
        drivetrain.setTeleopDrive(0, 0, 0, false);
    }
}

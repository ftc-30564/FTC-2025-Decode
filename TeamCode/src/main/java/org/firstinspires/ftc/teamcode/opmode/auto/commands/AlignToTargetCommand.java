package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.AimCalculator;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class AlignToTargetCommand extends Command {
    private Drivetrain drivetrain;
    private Telemetry telemetry;
    private AimCalculator aimCalculator;
    private boolean red;
    private boolean initialized = false;

    public AlignToTargetCommand(Drivetrain drivetrain, Telemetry telemetry, boolean red) {
        this.drivetrain = drivetrain;
        this.aimCalculator = new AimCalculator(drivetrain, red);
        this.red = red;
        this.telemetry = telemetry;
    }

    @Override
    public void initialize() {
        drivetrain.startTeleopDrive();

        initialized = true;

        telemetry.addData("Initialized", true);
        telemetry.update();
    }

    @Override
    public void loop() {
        aimCalculator.update();

        drivetrain.update();
        drivetrain.setAimedTeleopDrive(0, 0, aimCalculator.getShotData().angle);

        telemetry.update();
    }

    @Override
    public void end() {
        drivetrain.setTeleopDrive(0, 0, 0, false);
    }
}

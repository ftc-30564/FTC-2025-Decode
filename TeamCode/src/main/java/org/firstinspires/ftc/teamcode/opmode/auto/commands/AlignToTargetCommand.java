package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class AlignToTargetCommand extends Command {
    private Drivetrain drivetrain;
    private Limelight limelight;
    private Telemetry telemetry;
    private boolean red;
    private boolean initialized = false;

    public AlignToTargetCommand(Drivetrain drivetrain, Limelight limelight, Telemetry telemetry, boolean red) {
        this.drivetrain = drivetrain;
        this.limelight = limelight;
        this.red = red;
        this.telemetry = telemetry;
    }

    @Override
    public void initialize() {
        if (red) {
            limelight.setRedGoalPipeline();
        }
        else {
            limelight.setBlueGoalPipeline();
        }

        drivetrain.startTeleopDrive();

        initialized = true;

        telemetry.addData("Initialized", true);
        telemetry.update();
    }

    @Override
    public void loop() {
        drivetrain.update();

        drivetrain.setTeleopDrive(0, 0, 0, limelight.getYawTarget());
        telemetry.addData("Was", initialized);
        telemetry.addData("ll target", limelight.getYawTarget());

        telemetry.update();
    }

    @Override
    public void end() {
        drivetrain.setTeleopDrive(0, 0, 0, false);
    }
}

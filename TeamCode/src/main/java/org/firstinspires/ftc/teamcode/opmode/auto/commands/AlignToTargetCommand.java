package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class AlignToTargetCommand extends Command {
    private Drivetrain drivetrain;
    private Limelight limelight;
    private boolean red;

    public AlignToTargetCommand(Drivetrain drivetrain, Limelight limelight, boolean red) {
        this.drivetrain = drivetrain;
        this.limelight = limelight;
        this.red = red;
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
    }

    @Override
    public void loop() {
        drivetrain.setGoalCentricDrive(0, 0, limelight.getOffsetTarget());
    }

    @Override
    public void end() {
        drivetrain.setTeleopDrive(0, 0, 0, false);
    }
}

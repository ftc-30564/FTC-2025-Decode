package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class FollowPathCommand extends Command {
    private Drivetrain drivetrain;
    private PathChain path;
    private Telemetry telemetry;
    private boolean i = false;

    public FollowPathCommand(Drivetrain drivetrain, PathChain path, Telemetry telemetry) {
        this.drivetrain = drivetrain;
        this.path = path;
        this.telemetry = telemetry;
    }

    @Override
    public void initialize() {
        drivetrain.followPath(path, true);
        i = true;
        telemetry.addData("In initialize", true);
        telemetry.update();
    }


    @Override
    public void loop() {
        drivetrain.update();
        telemetry.addData("Path Progress", i);
        telemetry.update();

    }

    @Override
    public boolean isFinished() {
        return !drivetrain.isBusy();
    }
}
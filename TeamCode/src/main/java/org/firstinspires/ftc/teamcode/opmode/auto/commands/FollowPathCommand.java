package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class FollowPathCommand extends Command {
    private Drivetrain drivetrain;
    private PathChain path;

    public FollowPathCommand(Drivetrain drivetrain, PathChain path) {
        this.drivetrain = drivetrain;
        this.path = path;
    }

    @Override
    public void loop() {
        drivetrain.update();
        drivetrain.followPath(path, true);
    }

    @Override
    public boolean isFinished() {
        return !drivetrain.isBusy();
    }
}
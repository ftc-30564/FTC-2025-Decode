package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class FollowPathCommand extends Command {
    private Follower follower;
    private PathChain path;
    private Pose startingPose;

    public FollowPathCommand(Follower follower, Pose startingPose, PathChain path) {
        this.follower = follower;
        this.path = path;
    }

    @Override
    public void initialize() {
        follower.setStartingPose(startingPose);
    }

    @Override
    public void loop() {
        follower.update();
        follower.followPath(path, true);
    }

    @Override
    public boolean isFinished() {
        return !follower.isBusy();
    }
}
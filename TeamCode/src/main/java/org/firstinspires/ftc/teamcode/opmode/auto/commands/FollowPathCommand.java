package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class FollowPathCommand extends Command {
    private Drivetrain drivetrain;
    private PathChain path;

    /**
     * A command that follows a path.
     * @param drivetrain The drivetrain object
     * @param path The path to follow
     */
    public FollowPathCommand(Drivetrain drivetrain, PathChain path) {
        this.drivetrain = drivetrain;
        this.path = path;
    }

    @Override
    public void initialize() {
        drivetrain.followPath(path, true);
    }

    @Override
    public void loop() {
        drivetrain.update();
    }

    @Override
    public boolean isFinished() {
        return !drivetrain.isBusy();
    }
}
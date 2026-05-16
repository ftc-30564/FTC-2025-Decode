package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class FollowPathCommand extends Command {
    private Drivetrain drivetrain;
    private PathChain path;
    private ElapsedTime extraTime;
    private long millis;
    private boolean pathOver = false;

    /**
     * A command that follows a path.
     * @param drivetrain The drivetrain object
     * @param path The path to follow
     */
    public FollowPathCommand(Drivetrain drivetrain, PathChain path) {
        this(drivetrain, path, 0);
    }

    public FollowPathCommand(Drivetrain drivetrain, PathChain path, long millis) {
        this.drivetrain = drivetrain;
        this.path = path;
        this.extraTime = new ElapsedTime();
        this.millis = millis;
    }

    @Override
    public void initialize() {
        drivetrain.followPath(path, true);
        extraTime.reset();
    }

    @Override
    public void loop() {
        drivetrain.update();

        if (!drivetrain.isBusy() && !pathOver) {
            pathOver = true;
            extraTime.reset();
        }
    }

    @Override
    public boolean isFinished() {
        return (!drivetrain.isBusy() && (extraTime.milliseconds() > millis));
    }
}
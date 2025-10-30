package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

// Here is a command. It is created just like a class and
// extends Command.
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

    // This function is called *once* at the beginning of the command. This
    // should be used for initializing, like setting up stuff. Here, we tell
    // the drivetrain to follow the path.
    @Override
    public void initialize() {
        drivetrain.followPath(path, true);
    }


    // This function is called *repeatedly* as the command is being run. Here, we update the drivetrain.
    @Override
    public void loop() {
        drivetrain.update();
    }

    // This function is very important. It lets the command know when it finishes.
    // Here, we want to check to see if the drivetrain has finished following the path.
    // If it has, then we return true. If it hasn't, then we return false.
    @Override
    public boolean isFinished() {
        return !drivetrain.isBusy();
    }
}
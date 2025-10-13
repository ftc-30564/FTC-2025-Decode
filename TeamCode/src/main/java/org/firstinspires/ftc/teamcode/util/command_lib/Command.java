package org.firstinspires.ftc.teamcode.util.command_lib;

import com.qualcomm.robotcore.util.ElapsedTime;

// This is basically the structure for any commands.
//
public class Command {
    public Command[] commands = {this};
    public boolean initialized = false;
    public boolean ended = false;
    public ElapsedTime timer;
    public boolean isTimer = false;
    public double ms = 0;

    public void timerStart() {
        timer.reset();
    }

    public boolean timedOut() {
        if (!isTimer) {
            return false;
        }
        return timer.milliseconds() >= ms;
    }

    /**
     * Called once at the beginning when the command is first called.
     */
    public void initialize() {

    }

    /**
     * Called repeatedly during the command's lifetime.
     */
    public void loop() {

    }

    /**
     * Called once when the command is finished.
     */
    public void end() {

    }

    /**
     * Returns true when the command is finished.
     * @return Whether the command is finished
     */
    public boolean isFinished() {
        return false;
    }

    public Command timeout(double ms) {
        Command ret = this;
        ret.ms = ms;
        ret.isTimer = true;

        return ret;
    }
}

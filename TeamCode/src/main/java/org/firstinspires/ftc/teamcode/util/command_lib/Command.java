package org.firstinspires.ftc.teamcode.util.command_lib;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// This is basically the structure for any commands.
//
public class Command {
    public ArrayList<Command> commands = new ArrayList<>(List.of(this));
    public boolean initialized = false;
    public boolean ended = false;
    public ElapsedTime timer;
    public boolean isTimer = false;
    public long ms = 0;

    public void timerStart() {
        if (isTimer) {
            timer = new ElapsedTime(ms);
            timer.reset();
        }

    }

    public boolean timedOut() {
        if (!isTimer) {
            return false;
        }
        return timer.milliseconds() >= ms;
    }

    public void add(Command command) {

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

    public Command timeout(long ms) {
        Command ret = this;
        ret.ms = ms;
        ret.isTimer = true;

        return ret;
    }
}

package org.firstinspires.ftc.teamcode.util.command_lib;

import java.util.ArrayList;
import java.util.Arrays;

public class RaceCommand extends Command {
    private boolean done = false;

    /**
     * Creates a new RaceCommand. This runs all inner commands in parallel, but exits when just one of the commands has finished.
     * @param commands the commands to run
     */
    public RaceCommand(Command... commands) {
        this.commands = new ArrayList<>(Arrays.asList(commands));
    }

    /**
     * Initializes all the inner commands.
     */
    @Override
    public void initialize() {
        for (Command c : this.commands) {
            c.initialize();
            c.timerStart();
        }
    }

    /**
     * Loops through all the inner commands.
     */
    @Override
    public void loop() {
        for (Command c : this.commands) {
            if (c.isFinished() || c.timedOut()) {  // if any of the commands is finished, then the whole command is done.
                done = true;
            }
            else {
                c.loop();
            }
        }
    }

    /**
     * Ends all the inner commands.
     */
    @Override
    public void end() {
        for (Command c : this.commands) {
            c.end();
        }
    }

    /**
     * Determines if just one of the inner commands have finished.
     * @return true if one of the inner commands have finished, false otherwise
     */
    @Override
    public boolean isFinished() {
        return done;
    }
}

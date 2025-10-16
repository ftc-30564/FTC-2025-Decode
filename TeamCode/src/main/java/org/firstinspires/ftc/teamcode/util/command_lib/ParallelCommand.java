package org.firstinspires.ftc.teamcode.util.command_lib;

import java.util.ArrayList;
import java.util.Arrays;

public class ParallelCommand extends Command {
    private boolean allFinished(ArrayList<Command> commands) {
        for (Command c : commands) {
            if (!c.isFinished() && !c.timedOut()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a new ParallelCommand. This allows you to run multiple commands in parallel.
     * The command exits when all the inner commands have finished.
     * @param commands the commands to run
     */
    public ParallelCommand(Command... commands) {
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
            if (!c.isFinished() && !c.timedOut()) {
                c.loop();
            }
            else {
                c.end();
            }
        }
    }

    /**
     * Determines if all the inner commands have finished.
     * @return true if all the inner commands have finished, false otherwise
     */
    @Override
    public boolean isFinished() {
        return allFinished(this.commands);
    }
}

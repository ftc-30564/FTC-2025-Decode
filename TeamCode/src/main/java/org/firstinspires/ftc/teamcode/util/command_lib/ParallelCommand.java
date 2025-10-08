package org.firstinspires.ftc.teamcode.util.command_lib;

public class ParallelCommand extends Command {
    private boolean allFinished(Command[] commands) {
        for (Command c : commands) {
            if (!c.isFinished()) {
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
        this.commands = commands;
    }

    /**
     * Initializes all the inner commands.
     */
    @Override
    public void initialize() {
        for (Command c : this.commands) {
            c.initialize();
        }
    }

    /**
     * Loops through all the inner commands.
     */
    @Override
    public void loop() {
        for (Command c : this.commands) {
            if (!c.isFinished()) {
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

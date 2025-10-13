package org.firstinspires.ftc.teamcode.util.command_lib;

// A command that runs a bunch of commands in sequence.
// It waits for each command to finish before moving to the next.
public class SequentialCommand extends Command {
    private int currentCommand = 0;
    private boolean done = false;

    /**
     * Creates a new SequentialCommand. This runs a group of commands in sequence until they are all finished.
     * @param commands the commands to run
     */
    public SequentialCommand(Command... commands) {
        this.commands = commands;
    }

    /**
     * Initializes the first command.
     */
    @Override
    public void initialize() {
        this.commands[0].initialize();
        this.commands[0].timerStart();
    }

    /**
     * Loops through the commands. Once one command has finished, it moves onto the next one until the last command.
     */
    @Override
    public void loop() {
        if (done) return;

        this.commands[currentCommand].loop();
        if (this.commands[currentCommand].isFinished() || this.commands[currentCommand].timedOut()) {
            this.commands[currentCommand].end();
            currentCommand ++;

            if (currentCommand >= this.commands.length) {
                done = true;
            }
            else {
                this.commands[currentCommand].initialize();
                this.commands[currentCommand].timerStart();
            }
        }
    }

    /**
     * Returns whether or not the sequential command is finished
     * @return Whether the sequential command is finished
     */
    @Override
    public boolean isFinished() {
        return done;
    }
}

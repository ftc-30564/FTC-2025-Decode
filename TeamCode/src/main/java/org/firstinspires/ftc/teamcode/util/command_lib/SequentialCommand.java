package org.firstinspires.ftc.teamcode.util.command_lib;

// A command that runs a bunch of commands in sequence.
// It waits for each command to finish before moving to the next.
public class SequentialCommand extends Command {
    private int currentCommand = 0;
    private boolean done = false;

    public SequentialCommand(Command... commands) {
        this.commands = commands;
    }

    @Override
    public void initialize() {
        this.commands[0].initialize();
    }

    @Override
    public void loop() {
        if (done) return;

        this.commands[currentCommand].loop();
        if (this.commands[currentCommand].isFinished()) {
            this.commands[currentCommand].end();
            currentCommand ++;

            if (currentCommand >= this.commands.length) {
                done = true;
            }
            else {
                this.commands[currentCommand].initialize();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return done;
    }
}

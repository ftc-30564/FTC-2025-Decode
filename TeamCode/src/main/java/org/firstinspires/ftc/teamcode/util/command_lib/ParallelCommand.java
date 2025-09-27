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

    public ParallelCommand(Command... commands) {
        this.commands = commands;
    }

    @Override
    public void initialize() {
        for (Command c : this.commands) {
            c.initialize();
        }
    }

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

    @Override
    public boolean isFinished() {
        return allFinished(this.commands);
    }
}

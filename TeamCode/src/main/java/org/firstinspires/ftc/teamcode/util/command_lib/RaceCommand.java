package org.firstinspires.ftc.teamcode.util.command_lib;

public class RaceCommand extends Command {
    private boolean done = false;
    public RaceCommand(Command... commands) {
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
            if (c.isFinished()) {  // if any of the commands is finished, then the whole command is done.
                done = true;
            }
            else {
                c.loop();
            }
        }
    }

    @Override
    public void end() {
        for (Command c : this.commands) {
            c.end();
        }
    }

    @Override
    public boolean isFinished() {
        return done;
    }
}

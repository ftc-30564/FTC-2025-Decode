package org.firstinspires.ftc.teamcode.util.command_lib;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.BooleanSupplier;

// Class that handles actually running the commands.
public class CommandScheduler {
    public Command command;

    public CommandScheduler(Command command) {
        this.command = command;
    }

    public CommandScheduler(Command... commands) {
        this.command = new SequentialCommand(commands);
    }

    /**
     * Runs a command non-blocking
     * @return Whether the command is finished
     */
    public boolean run() {
        if (!command.initialized) {
            command.initialize();
            command.timerStart();
            command.loop();
            command.initialized = true;
        }
        else if (command.isFinished() || command.timedOut()) {
            if (command.ended) {
                return true;
            }
            command.end();
        }
        else {
            command.loop();
        }
        return false;
    }
}

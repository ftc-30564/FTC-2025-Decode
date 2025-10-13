package org.firstinspires.ftc.teamcode.util.command_lib;

import java.util.function.BooleanSupplier;

// Class that handles actually running the commands.
public class CommandScheduler {
    /**
     * Runs a command non-blocking
     * @param command The command to run
     * @return Whether the command is finished
     */
    public boolean run(Command command) {
        if (command.isFinished() || command.timedOut()) {
            command.end();
            return true;
        }
        else if (command.initialized) {
            command.loop();
        }
        else {
            command.initialize();
            command.timerStart();
            command.initialized = true;
        }
        return false;
    }
}

package org.firstinspires.ftc.teamcode.util.command_lib;

import java.util.function.BooleanSupplier;

// Class that handles actually running the commands.
public class CommandScheduler {
    /*
    new SequentialCommand (
        new MoveForwardCommand(drivetrain, .5, 1000),
        new ParallelCommand (
            new MoveForwardCommand(drivetrain, 0.25, 3000),
            new IntakeCommand(intake, RUN)
        ),
        new IntakeCommand(intake, STOP, 50)
    )
     */

    // Non blocking run function. Returns true when the command is complete
    public boolean run(Command command) {
        if (command.isFinished()) {
            command.end();
            return true;
        }
        else if (command.initialized) {
            command.loop();
        }
        else {
            command.initialize();
            command.initialized = true;
        }
        return false;
    }
}

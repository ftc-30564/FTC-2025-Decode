package org.firstinspires.ftc.teamcode.util.command_lib;

// This is basically the structure for any commands.
//
public class Command {
    public Command[] commands = {this};
    public boolean initialized = false;

    public void initialize() {

    }

    public void loop() {

    }

    public void end() {

    }

    public boolean isFinished() {
        return false;
    }
}

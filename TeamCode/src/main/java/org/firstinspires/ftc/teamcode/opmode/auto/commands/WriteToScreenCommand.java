package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

// Test command to make sure the library works properly
public class WriteToScreenCommand extends Command {
    private Telemetry telemetry;
    private String message;

    public WriteToScreenCommand(Telemetry telemetry, String message) {
        this.telemetry = telemetry;
        this.message = message;
    }

    @Override
    public void loop() {
        telemetry.addData("MESSAGE", message);
    }
}

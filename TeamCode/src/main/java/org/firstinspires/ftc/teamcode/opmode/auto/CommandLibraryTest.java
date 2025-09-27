package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opmode.auto.commands.WriteToScreenCommand;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;
import org.firstinspires.ftc.teamcode.util.command_lib.CommandScheduler;
import org.firstinspires.ftc.teamcode.util.command_lib.ParallelCommand;
import org.firstinspires.ftc.teamcode.util.command_lib.RaceCommand;
import org.firstinspires.ftc.teamcode.util.command_lib.SequentialCommand;

public class CommandLibraryTest extends LinearOpMode {
    @Override
    public void runOpMode() {

        CommandScheduler commandScheduler = new CommandScheduler();

        Command auto_1 = new SequentialCommand(
                new WriteToScreenCommand(telemetry, "5 seconds", 5000),
                new WriteToScreenCommand(telemetry, "2 seconds", 2000),
                new SequentialCommand(
                        new WriteToScreenCommand(telemetry, "8 seconds", 8000),
                        new WriteToScreenCommand(telemetry, "3 seconds", 3000)
                ),
                new ParallelCommand(
                        new WriteToScreenCommand(telemetry, "parallel 1", 3000),
                        new WriteToScreenCommand(telemetry, "parallel 2", 6000),
                        new WriteToScreenCommand(telemetry, "parallel 3", 9000)
                ),
                new RaceCommand(
                        new WriteToScreenCommand(telemetry, "race 10000", 10000),
                        new WriteToScreenCommand(telemetry, "race 6000", 6000),
                        new WriteToScreenCommand(telemetry, "race 3000", 3000)
                )
        );

        waitForStart();

        while (opModeIsActive()) {
            boolean auto_1_done = commandScheduler.run(auto_1);
            telemetry.update();
        }
    }
}

package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opmode.auto.commands.WriteToScreenCommand;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;
import org.firstinspires.ftc.teamcode.util.command_lib.CommandScheduler;
import org.firstinspires.ftc.teamcode.util.command_lib.ParallelCommand;
import org.firstinspires.ftc.teamcode.util.command_lib.RaceCommand;
import org.firstinspires.ftc.teamcode.util.command_lib.SequentialCommand;

@Autonomous
public class CommandLibraryTest extends LinearOpMode {
    @Override
    public void runOpMode() {

        CommandScheduler commandScheduler = new CommandScheduler();

        Command auto_1 = new SequentialCommand(
                new WriteToScreenCommand(telemetry, "seq 5s", 5000),
                new WriteToScreenCommand(telemetry, "seq 2s", 2000),
                new SequentialCommand(
                        new WriteToScreenCommand(telemetry, "nested 8s", 8000),
                        new WriteToScreenCommand(telemetry, "nested 3s", 3000)
                ),
                new ParallelCommand(
                        new WriteToScreenCommand(telemetry, "parallel 3s", 3000),
                        new WriteToScreenCommand(telemetry, "parallel 6s", 6000),
                        new WriteToScreenCommand(telemetry, "parallel 9s", 9000)
                ),
                new RaceCommand(
                        new WriteToScreenCommand(telemetry, "race 10s", 10000),
                        new WriteToScreenCommand(telemetry, "race 6s", 6000),
                        new WriteToScreenCommand(telemetry, "race 3s", 3000)
                ),
                new WriteToScreenCommand(telemetry, "end 5s", 5000)
        );

        waitForStart();

        while (opModeIsActive()) {
            boolean auto_1_done = commandScheduler.run(auto_1);
            if (auto_1_done) {
                telemetry.addData("command done", true);
            }
            telemetry.update();
        }
    }
}

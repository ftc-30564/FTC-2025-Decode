package org.firstinspires.ftc.teamcode.opmode.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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

        Command auto_1 = new SequentialCommand(
                new WriteToScreenCommand(telemetry, "seq 5s").timeout(5000),
                new WriteToScreenCommand(telemetry, "seq 2s").timeout(2000),
                new SequentialCommand(
                        new WriteToScreenCommand(telemetry, "nested 8s").timeout(8000),
                        new WriteToScreenCommand(telemetry, "nested 3s").timeout(3000)
                ),
                new ParallelCommand(
                        new WriteToScreenCommand(telemetry, "parallel 3s").timeout(3000),
                        new WriteToScreenCommand(telemetry, "parallel 6s").timeout(6000),
                        new WriteToScreenCommand(telemetry, "parallel 9s").timeout(9000)
                ),
                new RaceCommand(
                        new WriteToScreenCommand(telemetry, "race 10s").timeout(10000),
                        new WriteToScreenCommand(telemetry, "race 6s").timeout(6000),
                        new WriteToScreenCommand(telemetry, "race 3s").timeout(3000)
                ),
                new WriteToScreenCommand(telemetry, "end 5s").timeout(5000)
        );

        CommandScheduler commandScheduler = new CommandScheduler(auto_1);

        waitForStart();

        while (opModeIsActive()) {
            boolean auto_1_done = commandScheduler.run();
            if (auto_1_done) {
                telemetry.addData("command done", true);
            }
            telemetry.update();
        }
    }
}

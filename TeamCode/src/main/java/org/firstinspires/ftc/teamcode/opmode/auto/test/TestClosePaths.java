package org.firstinspires.ftc.teamcode.opmode.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.*;

import org.firstinspires.ftc.teamcode.opmode.auto.commands.DelayCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;
import org.firstinspires.ftc.teamcode.util.command_lib.CommandScheduler;
import org.firstinspires.ftc.teamcode.util.command_lib.SequentialCommand;

public class TestClosePaths extends LinearOpMode {
    public Drivetrain drivetrain;
    public final boolean close = true;
    public final boolean red = true;

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);

        drivetrain.setStartingPose(red ? (close ? RED_STARTING_CLOSE : RED_STARTING_FAR) : (close ? BLUE_STARTING_CLOSE : BLUE_STARTING_FAR));

        Command delay = new DelayCommand(3000);

        Command command = new SequentialCommand(
//                startToShootTrajectory(drivetrain, close, red, telemetry),
//                delay,
//                shootToIntakeTrajectory(drivetrain, BallPose.GPP, close, red, telemetry),
//                delay,
//                toShootTrajectory(drivetrain, BallPose.GPP, close, red, telemetry),
//                delay,
//                shootToIntakeTrajectory(drivetrain, BallPose.PGP, close, red, telemetry),
//                delay,
//                toShootTrajectory(drivetrain, BallPose.PGP, close, red, telemetry),
//                delay,
//                shootToIntakeTrajectory(drivetrain, BallPose.PPG, close, red, telemetry),
//                delay,
//                toShootTrajectory(drivetrain, BallPose.PPG, close, red, telemetry),
//                delay
        );

        CommandScheduler scheduler = new CommandScheduler(command);

        waitForStart();
        while (opModeIsActive()) {
            scheduler.run();
        }

    }
}

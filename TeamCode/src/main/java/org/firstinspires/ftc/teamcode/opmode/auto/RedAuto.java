package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.*;
import static org.firstinspires.ftc.teamcode.opmode.auto.AutoCommands.*;

import org.firstinspires.ftc.teamcode.opmode.auto.commands.DelayCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.PushCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;
import org.firstinspires.ftc.teamcode.util.command_lib.CommandScheduler;
import org.firstinspires.ftc.teamcode.util.command_lib.SequentialCommand;

@Autonomous
public class RedAuto extends LinearOpMode {
    public Drivetrain drivetrain;
    public Intake intake;
    public Shooter shooter;
    public AutoCommands autoCommands;

    public final boolean close = true;
    public final boolean red = true;

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        autoCommands = new AutoCommands(drivetrain, shooter, intake, telemetry);

        drivetrain.setStartingPose(red ? (close ? RED_STARTING_CLOSE : RED_STARTING_FAR) : (close ? BLUE_STARTING_CLOSE : BLUE_STARTING_FAR));

        // delay for testing purposes
        Command delay = new DelayCommand(1000);

        Command shootPreload = autoCommands.startAndShoot(close, red);

        Command intakeAndShootPPG = new SequentialCommand(
                autoCommands.driveAndIntakeBalls(BallPose.PPG, close, red),
                autoCommands.goAndShootBalls(BallPose.PPG, close, red)
        );

        Command intakeAndShootPGP = new SequentialCommand(
                autoCommands.driveAndIntakeBalls(BallPose.PGP, close, red),
                autoCommands.goAndShootBalls(BallPose.PGP, close, red)
        );

        Command intakeAndShootGPP = new SequentialCommand(
                autoCommands.driveAndIntakeBalls(BallPose.GPP, close, red),
                autoCommands.goAndShootBalls(BallPose.GPP, close, red)
        );


        CommandScheduler scheduler = new CommandScheduler(
                shootPreload,
                intakeAndShootPPG
                );

        waitForStart();

        shooter.stopPusher();
        while (opModeIsActive()) {
            scheduler.run();

            telemetry.update();
        }

    }
}

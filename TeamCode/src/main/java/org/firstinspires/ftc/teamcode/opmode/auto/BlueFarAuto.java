package org.firstinspires.ftc.teamcode.opmode.auto;

import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.BLUE_STARTING_CLOSE;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.BLUE_STARTING_FAR;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.BallPose;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.RED_STARTING_CLOSE;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.RED_STARTING_FAR;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;
import org.firstinspires.ftc.teamcode.util.command_lib.CommandScheduler;
import org.firstinspires.ftc.teamcode.util.command_lib.SequentialCommand;

@Autonomous
public class BlueFarAuto extends LinearOpMode {
    public Drivetrain drivetrain;
    public Intake intake;
    public Shooter shooter;
    public AutoCommands autoCommands;

    public final boolean close = false;
    public final boolean red = false;

    // This is the autonomous code. This is structured the same as
    // a teleop, but it doesn't use the gamepads.
    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        autoCommands = new AutoCommands(drivetrain, shooter, intake, telemetry);

        // Set the starting position on the field. PedroPathing needs to know
        // this for the other paths to work properly.
        drivetrain.setStartingPose(red ? (close ? RED_STARTING_CLOSE : RED_STARTING_FAR) : (close ? BLUE_STARTING_CLOSE : BLUE_STARTING_FAR));

        // These are different commands. Commands are used to run certain tasks in order, or
        // at the same time. This particular command will drive to the starting position,
        // charge up the flywheel, and once it does both of those things, it will shoot.
        Command shootPreload = autoCommands.startAndShoot(close, red);

        // A SequentialCommand lets you put multiple commands inside, and each command
        // runs in order, sequentially.
        Command intakeAndShootPPG = new SequentialCommand(
                // So, this command will run,
                autoCommands.driveAndIntakeBalls(BallPose.PPG, close, red, new Pose(0, 0, Math.toRadians(3))),
                // and once it's done, then this will run.
                autoCommands.goAndShootBalls(BallPose.PPG, close, red, new Pose(0, 0, Math.toRadians(1)))
        );

        Command intakeAndShootPGP = new SequentialCommand(
                autoCommands.driveAndIntakeBalls(BallPose.PGP, close, red, new Pose(0, 0, Math.toRadians(3))),
                autoCommands.goAndShootBalls(BallPose.PGP, close, red, new Pose())
        );

        Command intakeAndShootGPP = new SequentialCommand(
                autoCommands.driveAndIntakeBalls(BallPose.GPP, close, red, new Pose(0, 0, Math.toRadians(3))),
                autoCommands.goAndShootBalls(BallPose.GPP, close, red, new Pose())
        );


        // CommandScheduler lets us run each command.
        CommandScheduler scheduler = new CommandScheduler(
                shootPreload,   // first, shoot preload
                intakeAndShootPPG,    // then, go intake the PPG balls and shoot them.
                intakeAndShootPGP,
                intakeAndShootGPP
                );

        waitForStart();

        shooter.stopPusher();
        while (opModeIsActive()) {
            // This runs the commands.
            scheduler.run();

            telemetry.update();
        }

    }
}

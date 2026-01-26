package org.firstinspires.ftc.teamcode.opmode.auto;

import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.BLUE_STARTING_CLOSE;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.BLUE_STARTING_FAR;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.BallPose;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.LAST_REMEMBERED_POSE;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.RED_STARTING_CLOSE;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.RED_STARTING_FAR;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.leavePath;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.AutoCommands;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.ChargeFlywheelCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.IndicatorRGB;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.MorseCodePlayer;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.MorseCodeReader;
import org.firstinspires.ftc.teamcode.util.VelocityPair;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;
import org.firstinspires.ftc.teamcode.util.command_lib.CommandScheduler;
import org.firstinspires.ftc.teamcode.util.command_lib.ParallelCommand;
import org.firstinspires.ftc.teamcode.util.command_lib.SequentialCommand;

@Autonomous
public class RedClose12GateTwice extends LinearOpMode {
    public Drivetrain drivetrain;
    public Intake intake;
    public Shooter shooter;
    public Limelight limelight;
    public AutoCommands autoCommands;

    public final boolean close = true;
    public final boolean red = true;

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        limelight = new Limelight(hardwareMap);
        autoCommands = new AutoCommands(drivetrain, shooter, intake, limelight, telemetry);

        MorseCodeReader reader = new MorseCodeReader(hardwareMap);
        MorseCodePlayer player = new MorseCodePlayer(new IndicatorRGB(hardwareMap));
        player.addSequence(reader.getMorseCode());

        limelight.setRedGoalPipeline();

        drivetrain.setStartingPose(red ? (close ? RED_STARTING_CLOSE : RED_STARTING_FAR) : (close ? BLUE_STARTING_CLOSE : BLUE_STARTING_FAR));

        Command shootPreload = autoCommands.startAndShoot(close, red);

        Command intakeAndShootPPG = new SequentialCommand(
                autoCommands.driveAndIntakeBalls(BallPose.PPG, close, red, new Pose(-1, 2, Math.toRadians(1))),
                autoCommands.knockGate(red, true),
                autoCommands.goAndShootBalls(BallPose.PPG, close, red, RobotConstants.Auto.GatePose.FIRST_LINE, new Pose(-3, 2, Math.toRadians(1)))
        );

        Command intakeAndShootPGP = new SequentialCommand(
                autoCommands.driveAndIntakeBalls(BallPose.PGP, close, red, new Pose(0, 1, Math.toRadians(-4))),
                autoCommands.knockGate(red, false),
                autoCommands.goAndShootBalls(BallPose.PGP, close, red, RobotConstants.Auto.GatePose.SECOND_LINE, new Pose(-3, 2, Math.toRadians(0)))
        );

        Command intakeAndShootGPP = new SequentialCommand(
                autoCommands.driveAndIntakeBalls(BallPose.GPP, close, red, new Pose(0, 2, Math.toRadians(-2))),
                autoCommands.goAndShootBalls(BallPose.GPP, close, red, RobotConstants.Auto.GatePose.NONE, new Pose(-3, 2, Math.toRadians(0)))
        );

        Command intakeAndShootHumanPlayer = new SequentialCommand(
                autoCommands.driveAndIntakeBalls(BallPose.HUMAN_PLAYER, close, red, new Pose(0, 0, Math.toRadians(0))),
                autoCommands.goAndShootBalls(BallPose.HUMAN_PLAYER, close, red, RobotConstants.Auto.GatePose.NONE, new Pose(0, 0, Math.toRadians(0)))
        );

        Command leave = new FollowPathCommand(drivetrain, leavePath(drivetrain, close, red));


        CommandScheduler scheduler = new CommandScheduler(
                shootPreload,
                intakeAndShootPPG,
                intakeAndShootPGP,
                intakeAndShootGPP,
                leave
                );

        waitForStart();

        shooter.stopPusher();
        limelight.start();
        while (opModeIsActive()) {
            scheduler.run();
            player.playSequence();

            telemetry.update();
        }

        // update the pose for field centric
        LAST_REMEMBERED_POSE = drivetrain.getPose();

    }
}

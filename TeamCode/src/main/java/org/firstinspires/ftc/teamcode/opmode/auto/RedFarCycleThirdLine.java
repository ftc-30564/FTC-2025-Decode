package org.firstinspires.ftc.teamcode.opmode.auto;

import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.BLUE_STARTING_CLOSE;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.BLUE_STARTING_FAR;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.BallPose;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.LAST_REMEMBERED_POSE;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.RED_STARTING_CLOSE;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.RED_STARTING_FAR;
import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.leavePath;

import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.AutoCommands;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.IndicatorRGB;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.MorseCodePlayer;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.MorseCodeReader;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;
import org.firstinspires.ftc.teamcode.util.command_lib.CommandScheduler;
import org.firstinspires.ftc.teamcode.util.command_lib.SequentialCommand;

@Autonomous(group = "Red")
public class RedFarCycleThirdLine extends LinearOpMode {
    public Drivetrain drivetrain;
    public Intake intake;
    public Shooter shooter;
    public Limelight limelight;
    public AutoCommands autoCommands;
    public Timer loopTimer = new Timer();

    public final boolean close = false;
    public final boolean red = true;

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        limelight = new Limelight(hardwareMap);
        autoCommands = new AutoCommands(drivetrain, shooter, intake, limelight, telemetry);
        limelight.setRedGoalPipeline();

        MorseCodeReader reader = new MorseCodeReader(hardwareMap);
        MorseCodePlayer player = new MorseCodePlayer(new IndicatorRGB(hardwareMap));
        player.addSequence(reader.getMorseCode());

        limelight.setRedGoalPipeline();

        drivetrain.setStartingPose(red ? (close ? RED_STARTING_CLOSE : RED_STARTING_FAR) : (close ? BLUE_STARTING_CLOSE : BLUE_STARTING_FAR));

        Command shootPreload = autoCommands.startAndShoot(close, red);

        Command intakeAndShootGPP = new SequentialCommand(
                autoCommands.driveAndIntakeBallsBounce(BallPose.GPP, close, red, new Pose(8, -2, Math.toRadians(3))),
                autoCommands.goAndShootBalls(BallPose.GPP, close, red, RobotConstants.Auto.GatePose.NONE, new Pose(0, 0, Math.toRadians(-2)))
        );


        Command intakeAndShootGPP2 = new SequentialCommand(
                autoCommands.driveAndIntakeBallsUnbounce(BallPose.GPP, close, red, new Pose(9, -2.1, Math.toRadians(3))),
                autoCommands.goAndShootBalls(BallPose.GPP, close, red, RobotConstants.Auto.GatePose.NONE, new Pose(0, 0, Math.toRadians(-2)))
        );
//
//        Command intakeAndShootGPP3 = new SequentialCommand(
//                autoCommands.driveAndIntakeBallsUnbounce(BallPose.GPP, close, red, new Pose(9, -8, Math.toRadians(3))),
//                autoCommands.goAndShootBalls(BallPose.GPP, close, red, RobotConstants.Auto.GatePose.NONE, new Pose(0, 0, Math.toRadians(-2)))
//        );
//
//        Command intakeAndShootGPP4 = new SequentialCommand(
//                autoCommands.driveAndIntakeBallsUnbounce(BallPose.GPP, close, red, new Pose(9, -5, Math.toRadians(3))),
//                autoCommands.goAndShootBalls(BallPose.GPP, close, red, RobotConstants.Auto.GatePose.NONE, new Pose(0, 0, Math.toRadians(-2)))
//        );
//
//        Command intakeAndShootGPP5 = new SequentialCommand(
//                autoCommands.driveAndIntakeBallsUnbounce(BallPose.GPP, close, red, new Pose(9, -2, Math.toRadians(3))),
//                autoCommands.goAndShootBalls(BallPose.GPP, close, red, RobotConstants.Auto.GatePose.NONE, new Pose(0, 0, Math.toRadians(-2)))
//        );

        Command intakeAndShootHumanPlayer = new SequentialCommand(
                autoCommands.driveAndIntakeBallsUnbounce(BallPose.HUMAN_PLAYER2, close, red, new Pose(0, 0, Math.toRadians(0))).timeout(3000),
                autoCommands.goAndShootBalls(BallPose.HUMAN_PLAYER2, close, red, RobotConstants.Auto.GatePose.NONE, new Pose(0, 0, Math.toRadians(0)))
        );

        Command intakeAndShootHumanPlayer2 = new SequentialCommand(
                autoCommands.driveAndIntakeBallsUnbounce(BallPose.HUMAN_PLAYER2, close, red, new Pose(0, 2, Math.toRadians(0))).timeout(3000),
                autoCommands.goAndShootBalls(BallPose.HUMAN_PLAYER2, close, red, RobotConstants.Auto.GatePose.NONE, new Pose(0, 0, Math.toRadians(0)))
        );

        Command intakeAndShootHumanPlayer3 = new SequentialCommand(
                autoCommands.driveAndIntakeBallsUnbounce(BallPose.HUMAN_PLAYER2, close, red, new Pose(0, -2, Math.toRadians(0))).timeout(3000),
                autoCommands.goAndShootBalls(BallPose.HUMAN_PLAYER2, close, red, RobotConstants.Auto.GatePose.NONE, new Pose(0, 0, Math.toRadians(0)))
        );

        Command intakeAndShootHumanPlayer4 = new SequentialCommand(
                autoCommands.driveAndIntakeBallsUnbounce(BallPose.HUMAN_PLAYER2, close, red, new Pose(0, 0, Math.toRadians(0))).timeout(3000),
                autoCommands.goAndShootBalls(BallPose.HUMAN_PLAYER2, close, red, RobotConstants.Auto.GatePose.NONE, new Pose(0, 0, Math.toRadians(0)))
        );

        Command leave = new FollowPathCommand(drivetrain, leavePath(drivetrain, close, red));


        CommandScheduler scheduler = new CommandScheduler(
                shootPreload,
                intakeAndShootGPP,
                intakeAndShootHumanPlayer,
                intakeAndShootHumanPlayer2,
                intakeAndShootHumanPlayer3,

                leave
        );

        waitForStart();
        limelight.start();
        shooter.stopPusher();
        while (opModeIsActive()) {
            loopTimer.resetTimer();
            scheduler.run();
            player.playSequence();
            telemetry.addData("ms", String.valueOf(loopTimer.getElapsedTime()));
            telemetry.update();
        }

        // update the pose for field centric
        LAST_REMEMBERED_POSE = drivetrain.getPose();

    }
}

package org.firstinspires.ftc.teamcode.opmode.auto;

import static org.firstinspires.ftc.teamcode.RobotConstants.AutoPaths.BallPose;
import static org.firstinspires.ftc.teamcode.RobotConstants.AutoPaths.GatePose;
import static org.firstinspires.ftc.teamcode.RobotConstants.AutoPaths.LAST_REMEMBERED_POSE;
import static org.firstinspires.ftc.teamcode.RobotConstants.AutoPaths.leavePath;
import static org.firstinspires.ftc.teamcode.RobotConstants.AutoPoses.BLUE_STARTING_CLOSE;
import static org.firstinspires.ftc.teamcode.RobotConstants.AutoPoses.BLUE_STARTING_FAR;
import static org.firstinspires.ftc.teamcode.RobotConstants.AutoPoses.RED_STARTING_CLOSE;
import static org.firstinspires.ftc.teamcode.RobotConstants.AutoPoses.RED_STARTING_FAR;
import static org.firstinspires.ftc.teamcode.util.PoseConversion.pedroToAdvScope;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opmode.auto.commands.AutoCommands;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.subsystems.AimCalculator;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.IndicatorRGB;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.MorseCodePlayer;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.Logging;
import org.firstinspires.ftc.teamcode.util.MorseCodeReader;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;
import org.firstinspires.ftc.teamcode.util.command_lib.CommandScheduler;
import org.firstinspires.ftc.teamcode.util.command_lib.SequentialCommand;

@Autonomous(group = "Red")
public class RedCloseEfficientized extends LinearOpMode {
    private Drivetrain drivetrain;
    private Intake intake;
    private Shooter shooter;
    private AutoCommands autoCommands;
    private Logging logging;
    private TelemetryPacket telemetryPacket;

    private final boolean close = true;
    private final boolean red = true;

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        autoCommands = new AutoCommands(drivetrain, shooter, intake, telemetry);

        logging = new Logging(drivetrain, shooter, hardwareMap);

        TelemetryPacket telemetryPacket = new TelemetryPacket();

        MorseCodeReader reader = new MorseCodeReader(hardwareMap);
        MorseCodePlayer player = new MorseCodePlayer(new IndicatorRGB(hardwareMap));
        player.addSequence(reader.getMorseCode());

        drivetrain.setStartingPose(red ? (close ? RED_STARTING_CLOSE : RED_STARTING_FAR) : (close ? BLUE_STARTING_CLOSE : BLUE_STARTING_FAR));

        Command shootPreload = autoCommands.startAndShoot(close, red);

        Command intakeAndShootPPG = new SequentialCommand(
                autoCommands.driveAndIntakeBallsUnbounce(BallPose.PPG, close, red, new Pose()),
                autoCommands.goAndShootBalls(BallPose.PPG, close, red, GatePose.NONE, new Pose())
        );

        Command intakeAndShootPGP = new SequentialCommand(
                //autoCommands.driveAndIntakeBallsUnbounce(BallPose.PGP, close, red, new Pose(3.3, 1, Math.toRadians(-2))),
                autoCommands.driveAndIntakeBallsUnbounce(BallPose.PGP, close, red, new Pose()),
                autoCommands.goAndShootBalls(BallPose.PGP, close, red, GatePose.NONE, new Pose())
        );

        Command intakeAndShootGPP = new SequentialCommand(
                //autoCommands.driveAndIntakeBallsUnbounce(BallPose.GPP, close, red, new Pose(5.7, 2, Math.toRadians(-5))),
                autoCommands.driveAndIntakeBallsUnbounce(BallPose.GPP, close, red, new Pose()),
                autoCommands.goAndShootBalls(BallPose.GPP, close, red, GatePose.NONE, new Pose())
        );

        Command intakeAndShootGate = new SequentialCommand(
                //autoCommands.driveAndIntakeBallsUnbounce(BallPose.GPP, close, red, new Pose(5.7, 2, Math.toRadians(-5))),
                autoCommands.gateTake(red, 100),
                autoCommands.goAndShootBalls(BallPose.GATETAKE, close, red, GatePose.NONE, new Pose())
        );

        Command intakeAndShootGate1 = new SequentialCommand(
                //autoCommands.driveAndIntakeBallsUnbounce(BallPose.GPP, close, red, new Pose(5.7, 2, Math.toRadians(-5))),
                autoCommands.gateTake(red, 800),
                autoCommands.goAndShootBalls(BallPose.GATETAKE, close, red, GatePose.NONE, new Pose())
        );

        Command intakeAndShootGate2 = new SequentialCommand(
                //autoCommands.driveAndIntakeBallsUnbounce(BallPose.GPP, close, red, new Pose(5.7, 2, Math.toRadians(-5))),
                autoCommands.gateTake(red, 600),
                autoCommands.goAndShootBalls(BallPose.GATETAKE, close, red, GatePose.NONE, new Pose())
        );

        Command intakeAndShootHumanPlayer = new SequentialCommand(
                autoCommands.driveAndIntakeBallsBounce(BallPose.HUMAN_PLAYER1, close, red, new Pose(0, 0, Math.toRadians(0))),
                autoCommands.goAndShootBalls(BallPose.HUMAN_PLAYER1, close, red, GatePose.NONE, new Pose(0, 0, Math.toRadians(0)))
        );

        Command leave = new FollowPathCommand(drivetrain, leavePath(drivetrain, close, red));

        CommandScheduler scheduler = new CommandScheduler(
                shootPreload,
                intakeAndShootPGP,
                intakeAndShootGate,
                intakeAndShootGate1,
                intakeAndShootGPP,
                intakeAndShootPPG,
                leave
                );

        waitForStart();

        shooter.stopPusher();
        while (opModeIsActive()) {
            scheduler.run();
            //player.playSequence();

            //logging.updateTelemetryPacket(telemetryPacket);

            //FtcDashboard.getInstance().sendTelemetryPacket(telemetryPacket);
        }

        // update the pose for field centric
        LAST_REMEMBERED_POSE = drivetrain.getPose();

    }
}

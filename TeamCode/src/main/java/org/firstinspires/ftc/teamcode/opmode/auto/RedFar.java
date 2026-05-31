package org.firstinspires.ftc.teamcode.opmode.auto;

import static org.firstinspires.ftc.teamcode.RobotConstants.AutoPoses.*;
import static org.firstinspires.ftc.teamcode.opmode.auto.commands.AutoCommands.BallPose.GPP;
import static org.firstinspires.ftc.teamcode.opmode.auto.commands.AutoCommands.BallPose.PGP;
import static org.firstinspires.ftc.teamcode.opmode.auto.commands.AutoCommands.BallPose.PPG;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Paths;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.AutoCommands;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.MorseCodePlayer;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.Logging;
import org.firstinspires.ftc.teamcode.util.MorseCodeReader;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;
import org.firstinspires.ftc.teamcode.util.command_lib.CommandScheduler;
import org.firstinspires.ftc.teamcode.util.command_lib.SequentialCommand;

@Autonomous(group = "Red")
public class RedFar extends LinearOpMode {
    private Drivetrain drivetrain;
    private Intake intake;
    private Shooter shooter;
    private AutoCommands autoCommands;
    private Logging logging;
    private TelemetryPacket telemetryPacket;

    private boolean red = true;

    public void setAlliance(boolean red) {
        this.red = red;
    }

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        autoCommands = new AutoCommands(drivetrain, shooter, intake, telemetry);

        logging = new Logging(drivetrain, shooter, hardwareMap);

        MorseCodeReader reader = new MorseCodeReader(hardwareMap);

        drivetrain.setStartingPose(red ? RED_STARTING_FAR : BLUE_STARTING_FAR);

        Command shootPreload = autoCommands.startAndShootFar(red);

        Command intakeAndShootHP = new SequentialCommand(
                autoCommands.intakeHPFar(red),
                autoCommands.goAndShootBallsFar(red ? RED_HUMAN_PLAYER_3 : BLUE_HUMAN_PLAYER_3, red)
        );

        Command intakeAndShootHP1 = new SequentialCommand(
                autoCommands.intakeHPFar(red),
                autoCommands.goAndShootBallsFar(red ? RED_HUMAN_PLAYER_3 : BLUE_HUMAN_PLAYER_3, red)
        );

        Command intakeAndShootHP2 = new SequentialCommand(
                autoCommands.intakeHPFar(red),
                autoCommands.goAndShootBallsFar(red ? RED_HUMAN_PLAYER_3 : BLUE_HUMAN_PLAYER_3, red)
        );

        Command intakeAndShootHPOffset = new SequentialCommand(
                autoCommands.intakeHPOffsetFar(red),
                autoCommands.goAndShootBallsFar(red ? RED_HUMAN_PLAYER_5 : BLUE_HUMAN_PLAYER_5, red)
        );

        Command intakeAndShootHPOffset1 = new SequentialCommand(
                autoCommands.intakeHPOffsetFar(red),
                autoCommands.goAndShootBallsFar(red ? RED_HUMAN_PLAYER_5 : BLUE_HUMAN_PLAYER_5, red)
        );

        Command intakeAndShootHPOffset2 = new SequentialCommand(
                autoCommands.intakeHPOffsetFar(red),
                autoCommands.goAndShootBallsFar(red ? RED_HUMAN_PLAYER_5 : BLUE_HUMAN_PLAYER_5, red)
        );

        Command intakeAndShootGPP = new SequentialCommand(
                autoCommands.intakeGPPFar(red),
                autoCommands.goAndShootBallsFar(red ? RED_POST_INTAKE_GPP : BLUE_POST_INTAKE_GPP, red)
        );

        Command intakeAndShootGPP2 = new SequentialCommand(
                autoCommands.intakeGPPFar(red),
                autoCommands.goAndShootBallsFar(red ? RED_POST_INTAKE_GPP : BLUE_POST_INTAKE_GPP, red)
        );

        Command intakeAndShootGPP3 = new SequentialCommand(
                autoCommands.intakeGPPFar(red),
                autoCommands.goAndShootBallsFar(red ? RED_POST_INTAKE_GPP : BLUE_POST_INTAKE_GPP, red)
        );

//        Command intakeAndShootGate = new SequentialCommand(
//                autoCommands.intakeGateClose(red, 500),
//                autoCommands.goAndShootBallsClose(red ? RED_GATETAKE : BLUE_GATETAKE, red)
//        );
//
//        Command intakeAndShootGate1 = new SequentialCommand(
//                autoCommands.intakeGateClose(red, 100),
//                autoCommands.goAndShootBallsClose(red ? RED_GATETAKE : BLUE_GATETAKE, red)
//        );

        //Command leave = new FollowPathCommand(drivetrain, Paths.Far.leave(drivetrain, red));

        CommandScheduler scheduler = new CommandScheduler(
                shootPreload,
                intakeAndShootHP,
                intakeAndShootGPP,
                intakeAndShootGPP2,
                intakeAndShootHP1,
                intakeAndShootGPP3,
                intakeAndShootHP2
//                intakeAndShootHP2
                //intakeAndShootHPOffset2
        );

        waitForStart();

        shooter.stopPusher();
        while (opModeIsActive()) {
            scheduler.run();
            //player.playSequence();

//            logging.updateTelemetryPacket(telemetryPacket);
//
//            FtcDashboard.getInstance().sendTelemetryPacket(telemetryPacket);

            RobotConstants.Drive.HAS_POSE = true;
            RobotConstants.Drive.LAST_REMEMBERED_POSE = drivetrain.getPose();
        }

        // update the pose for teleop
//        RobotConstants.Drive.LAST_REMEMBERED_POSE = drivetrain.getPose();
        RobotConstants.Drive.HAS_POSE = true;
        RobotConstants.Drive.LAST_REMEMBERED_POSE = drivetrain.getPose();
    }
}

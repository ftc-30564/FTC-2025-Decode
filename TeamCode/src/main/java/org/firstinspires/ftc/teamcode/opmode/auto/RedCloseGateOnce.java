package org.firstinspires.ftc.teamcode.opmode.auto;

import static org.firstinspires.ftc.teamcode.RobotConstants.AutoPoses.*;
import static org.firstinspires.ftc.teamcode.opmode.auto.commands.AutoCommands.BallPose.*;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Paths;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.AutoCommands;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.FollowPathCommand;
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
public class RedCloseGateOnce extends LinearOpMode {
    private Drivetrain drivetrain;
    private Intake intake;
    private Shooter shooter;
    private AutoCommands autoCommands;
    private Logging logging;
    private TelemetryPacket telemetryPacket;

    private boolean red = true;

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        autoCommands = new AutoCommands(drivetrain, shooter, intake, telemetry);

        logging = new Logging(drivetrain, shooter, hardwareMap);

        MorseCodeReader reader = new MorseCodeReader(hardwareMap);
        MorseCodePlayer player = new MorseCodePlayer(new IndicatorRGB(hardwareMap));
        player.addSequence(reader.getMorseCode());

        drivetrain.setStartingPose(red ? RED_STARTING_CLOSE : BLUE_STARTING_CLOSE);

        Command shootPreload = autoCommands.startAndShootClose(red);

        Command intakeAndShootPPG = new SequentialCommand(
                autoCommands.intakeLineClose(PPG, red),
                autoCommands.goAndShootBallsClose(red ? RED_POST_INTAKE_PPG : BLUE_POST_INTAKE_PPG, red)
        );

        Command intakeAndShootPGP = new SequentialCommand(
                autoCommands.intakeLineClose(PGP, red),
                autoCommands.hitGate(red, red ? RED_POST_INTAKE_PGP : BLUE_POST_INTAKE_PGP),
                autoCommands.goAndShootBallsClose(red ? RED_GATEMPTY : BLUE_GATEMPTY, red)
        );

        Command intakeAndShootGPP = new SequentialCommand(
                autoCommands.intakeLineClose(GPP, red),
                autoCommands.goAndShootBallsClose(red ? RED_POST_INTAKE_GPP : BLUE_POST_INTAKE_GPP, red)
        );

        Command intakeAndShootGate = new SequentialCommand(
                autoCommands.intakeGateClose(red, 700),
                autoCommands.goAndShootBallsClose(red ? RED_GATETAKE : BLUE_GATETAKE, red)
        );

        Command intakeAndShootGate1 = new SequentialCommand(
                autoCommands.intakeGateClose(red, 700),
                autoCommands.goAndShootBallsClose(red ? RED_GATETAKE : BLUE_GATETAKE, red)
        );

        Command leave = new FollowPathCommand(drivetrain, Paths.Close.leave(drivetrain, red));

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

        // update the pose for teleop
//        RobotConstants.Drive.LAST_REMEMBERED_POSE = drivetrain.getPose();
        RobotConstants.Drive.HAS_POSE = true;

    }
}

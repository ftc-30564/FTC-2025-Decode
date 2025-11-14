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

import org.firstinspires.ftc.teamcode.opmode.auto.commands.AutoCommands;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;
import org.firstinspires.ftc.teamcode.util.command_lib.CommandScheduler;
import org.firstinspires.ftc.teamcode.util.command_lib.SequentialCommand;

@Autonomous
public class BlueCloseGateAuto extends LinearOpMode {
    public Drivetrain drivetrain;
    public Intake intake;
    public Shooter shooter;
    public AutoCommands autoCommands;

    public final boolean close = true;
    public final boolean red = false;

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        autoCommands = new AutoCommands(drivetrain, shooter, intake, telemetry);

        drivetrain.setStartingPose(red ? (close ? RED_STARTING_CLOSE : RED_STARTING_FAR) : (close ? BLUE_STARTING_CLOSE : BLUE_STARTING_FAR));

        Command shootPreload = autoCommands.startAndShoot(close, red);

        Command intakeAndShootPPG = new SequentialCommand(
                autoCommands.driveAndIntakeBalls(BallPose.PPG, close, red, new Pose(0, 0, Math.toRadians(3))),
                autoCommands.knockGate(red),
                autoCommands.goAndShootBalls(BallPose.PPG, close, red, true, new Pose(0, 0, Math.toRadians(-2)))
        );

        Command intakeAndShootPGP = new SequentialCommand(
                autoCommands.driveAndIntakeBalls(BallPose.PGP, close, red, new Pose(0, 0, Math.toRadians(3))),
                autoCommands.goAndShootBalls(BallPose.PGP, close, red, false, new Pose(0, 0, Math.toRadians(-2)))
        );

        Command intakeAndShootGPP = new SequentialCommand(
                autoCommands.driveAndIntakeBalls(BallPose.GPP, close, red, new Pose(0, 2.5, Math.toRadians(3))),
                autoCommands.goAndShootBalls(BallPose.GPP, close, red, false, new Pose(0, 0, Math.toRadians(0)))
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
        while (opModeIsActive()) {
            scheduler.run();

            telemetry.update();
        }

        // update the pose for field centric
        LAST_REMEMBERED_POSE = drivetrain.getPose();

    }
}

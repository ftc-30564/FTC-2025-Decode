package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.*;
import static org.firstinspires.ftc.teamcode.RobotConstants.Shooter.*;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.VelocityPair;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;
import org.firstinspires.ftc.teamcode.util.command_lib.ParallelCommand;
import org.firstinspires.ftc.teamcode.util.command_lib.RaceCommand;
import org.firstinspires.ftc.teamcode.util.command_lib.SequentialCommand;

// This contains the different commands that are used in autonomous.
// They're all in one place just so it's cleaner.
public class AutoCommands {
    private final Drivetrain drivetrain;
    private final Shooter shooter;
    private final Intake intake;
    private final Limelight limelight;
    private final Telemetry telemetry;

    public AutoCommands(Drivetrain drivetrain, Shooter shooter, Intake intake, Limelight limelight, Telemetry telemetry) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;
        this.limelight = limelight;
        this.telemetry = telemetry;
    }

    /**
     * Returns a command where the robot starts and shoots the preload, based on whether: it is starting close or far, or red and blue.
     * @param close if the robot is starting close or far
     * @param red if the robot is red or blue
     * @return a command where the robot drives to shooting position and shoots the preload
     */
    public Command startAndShoot(boolean close, boolean red) {
        VelocityPair vel = close ? CLOSE_VELOCITY : FAR_VELOCITY;

        return new SequentialCommand(
                new ParallelCommand(
                        new FollowPathCommand(drivetrain, startToShootPath(drivetrain, close, red)),
                        new ChargeFlywheelCommand(shooter, vel).timeout(1000)
                ),
                new RaceCommand(
                        new AlignToTargetCommand(drivetrain, limelight, telemetry, red),
                        new ShootCommand(shooter, intake, vel).timeout(SHOOT_TIME_MS)
                )
        ) ;
    }

    /**
     * Returns a command where the robot drives and intakes balls from the shooting position.
     * @param ballPose the group of balls to intake (ex. PPG, PGP, GPP)
     * @param close if the robot shot close or far
     * @param red if the robot is red or blue
     * @param drift an offset pose in case the dead wheels experience drift
     * @return a command where the robot drives and intakes balls from the shooting position
     */
    public Command driveAndIntakeBalls(BallPose ballPose, boolean close, boolean red, Pose drift) {
        return new SequentialCommand(
                //new DelayCommand(500),
                new RaceCommand(
                        new FollowPathCommand(drivetrain, intakeBallsPath(drivetrain, ballPose, close, red, drift)),
                        new IntakeCommand(intake, shooter, Intake.Mode.RUNNING)
                ),
                new IntakeCommand(intake, shooter, Intake.Mode.RUNNING).timeout(200)
        );
    }

    public Command knockGate(boolean red) {
        return new FollowPathCommand(drivetrain, knockGatePath(drivetrain, red));
    }

    /**
     * Returns a command where the robot drives to the shooting position and shoots balls.
     * @param ballPose the group of balls that the robot starts at (ex. PPG, PGP, GPP)
     * @param close if the robot is shooting close or far
     * @param red if the robot is red or blue
     * @param drift a pose that offsets the end position in case the dead wheels experience drift
     * @return a command where the robot drives to the shooting position and shoots balls
     */
    public Command goAndShootBalls(BallPose ballPose, boolean close, boolean red, boolean fromGate, Pose drift) {
        VelocityPair vel = close ? CLOSE_VELOCITY : FAR_VELOCITY;

        return new SequentialCommand(
                new ParallelCommand(
                        new IntakeCommand(intake, shooter, Intake.Mode.RUNNING).timeout(500),
                        new FollowPathCommand(drivetrain, intakeToShootPath(drivetrain, ballPose, close, red, fromGate, drift)),
                        new ChargeFlywheelCommand(shooter, vel).timeout(1000)
                ),
                new RaceCommand(
                        new AlignToTargetCommand(drivetrain, limelight, telemetry, red),   // hold the position while it's shooting, in case it gets bumped during auto
                        new ShootCommand(shooter, intake, vel).timeout(SHOOT_TIME_MS)
                )
        );
    }
}

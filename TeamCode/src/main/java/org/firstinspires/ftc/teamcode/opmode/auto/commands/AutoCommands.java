package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import static org.firstinspires.ftc.teamcode.RobotConstants.AutoPaths.*;
import static org.firstinspires.ftc.teamcode.RobotConstants.Shooter.*;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.AimCalculator;
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
    private final Telemetry telemetry;
    private final AimCalculator aimCalculator;

    public AutoCommands(Drivetrain drivetrain, Shooter shooter, Intake intake, Telemetry telemetry) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;
        this.telemetry = telemetry;
        this.aimCalculator = new AimCalculator(drivetrain);
    }

    /**
     * Returns a command where the robot starts and shoots the preload, based on whether: it is starting close or far, or red and blue.
     * @param close if the robot is starting close or far
     * @param red if the robot is red or blue
     * @return a command where the robot drives to shooting position and shoots the preload
     */
    public Command startAndShoot(boolean close, boolean red) {
        return new SequentialCommand(
                new RaceCommand(
                        new ChargeFlywheelCommand(shooter, aimCalculator, red),
                        new FollowPathCommand(drivetrain, startToShootPath(drivetrain, close, red)),
                        new SequentialCommand(
                                new DelayCommand(950),
                                new ShootCommand(shooter, intake).timeout(SHOOT_TIME_MS)
                        )
                ),
                new InstantCommand(shooter::coast)
        );
    }

    /**
     * Returns a command where the robot drives and intakes balls from the shooting position.
     * @param ballPose the group of balls to intake (ex. PPG, PGP, GPP)
     * @param close if the robot shot close or far
     * @param red if the robot is red or blue
     * @param drift an offset pose in case the dead wheels experience drift
     * @return a command where the robot drives and intakes balls from the shooting position
     */
    public Command driveAndIntakeBallsBounce(RobotConstants.AutoPaths.BallPose ballPose, boolean close, boolean red, Pose drift) {
        return new SequentialCommand(
                //new DelayCommand(500),
                new RaceCommand(
                        new FollowPathCommand(drivetrain, intakeBallsPathBounce(drivetrain, ballPose, close, red, drift)).timeout(5000),
                        new IntakeCommand(intake, shooter, Intake.Mode.RUNNING)
                )
               // new IntakeCommand(intake, shooter, Intake.Mode.RUNNING).timeout(100)
        );
    }

    public Command driveAndIntakeBallsUnbounce(BallPose ballPose, boolean close, boolean red, Pose drift) {
        return new SequentialCommand(
                //new DelayCommand(500),
                new RaceCommand(
                        new FollowPathCommand(drivetrain, intakeBallsPathUnbounce(drivetrain, ballPose, close, red, drift)).timeout(5000),
                        new IntakeCommand(intake, shooter, Intake.Mode.RUNNING)
                )
                // new IntakeCommand(intake, shooter, Intake.Mode.RUNNING).timeout(100)
        );
    }

    public Command gateTake(boolean red, long delay) {
        return new SequentialCommand(
                //new DelayCommand(500),
                new RaceCommand(
                        new FollowPathCommand(drivetrain, gateTakePath(drivetrain, red)),
                        new IntakeCommand(intake, shooter, Intake.Mode.RUNNING)
                ),
                new RaceCommand(
                        new DelayCommand(delay),
                        new IntakeCommand(intake, shooter, Intake.Mode.RUNNING)
                )

                // new IntakeCommand(intake, shooter, Intake.Mode.RUNNING).timeout(100)
        );

    }

    public Command knockGate(boolean red, boolean fromFirstLine) {
        return new SequentialCommand(
                new ParallelCommand(
                        new FollowPathCommand(drivetrain, fromFirstLine ? knockGateFromFirstPath(drivetrain, red) : knockGateFromSecondPath(drivetrain, red)).timeout(2500),
                        new IntakeCommand(intake, shooter, Intake.Mode.RUNNING).timeout(700)
                ),
                new DelayCommand(0)
        );
    }

    /**
     * Returns a command where the robot drives to the shooting position and shoots balls.
     * @param ballPose the group of balls that the robot starts at (ex. PPG, PGP, GPP)
     * @param close if the robot is shooting close or far
     * @param red if the robot is red or blue
     * @param drift a pose that offsets the end position in case the dead wheels experience drift
     * @return a command where the robot drives to the shooting position and shoots balls
     */
    public Command goAndShootBalls(BallPose ballPose, boolean close, boolean red, GatePose gatePose, Pose drift) {
        return new SequentialCommand(new RaceCommand(
                new ChargeFlywheelCommand(shooter, aimCalculator, red),
                new SequentialCommand(
                        new ParallelCommand(
                                new FollowPathCommand(drivetrain, intakeToShootPath(drivetrain, ballPose, close, red, gatePose, drift)),
                                new IntakeCommand(intake, shooter, Intake.Mode.RUNNING).timeout(500)
                        ),
                        new RaceCommand(
                                new ShootCommand(shooter, intake).timeout(SHOOT_TIME_MS),
                                new AlignToTargetCommand(drivetrain, red)
                        )

                )
        ), new InstantCommand(shooter::coast));
    }
}
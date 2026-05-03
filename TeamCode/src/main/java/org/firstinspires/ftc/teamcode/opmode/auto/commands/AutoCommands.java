package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import static org.firstinspires.ftc.teamcode.Paths.*;

import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Paths;
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

    private final long SHOOT_TIME_MS = 600;

    public AutoCommands(Drivetrain drivetrain, Shooter shooter, Intake intake, Telemetry telemetry) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;
        this.telemetry = telemetry;
        this.aimCalculator = new AimCalculator(drivetrain);
    }

    public enum BallPose {
        PPG,
        PGP,
        GPP
    }

    public Command startAndShootClose(boolean red) {
        return new SequentialCommand(
                new RaceCommand(
                        new ChargeFlywheelCommand(shooter, aimCalculator, red),
                        new FollowPathCommand(drivetrain, Close.startToShoot(drivetrain, red)),
                        new SequentialCommand(
                                new DelayCommand(900),
                                new ShootCommand(shooter, intake).timeout(SHOOT_TIME_MS)
                        )
                ),
                new InstantCommand(shooter::coast)
        );
    }

    public Command intakeLineClose(BallPose ballPose, boolean red) {
        PathChain pathChain = new PathChain();

        switch (ballPose) {
            case PPG:
                pathChain = Close.intakePPG(drivetrain, red);
                break;
            case PGP:
                pathChain = Close.intakePGP(drivetrain, red);
                break;
            case GPP:
                pathChain = Close.intakeGPP(drivetrain, red);
                break;
        }

        return new SequentialCommand(
                //new DelayCommand(500),
                new RaceCommand(
                        new FollowPathCommand(drivetrain, pathChain).timeout(5000),
                        new IntakeCommand(intake, shooter, Intake.Mode.RUNNING)
                )
                // new IntakeCommand(intake, shooter, Intake.Mode.RUNNING).timeout(100)
        );
    }

    public Command hitGate(boolean red, Pose from) {
        return new FollowPathCommand(drivetrain, Close.hitGate(drivetrain, red, from));
    }

    public Command intakeGateClose(boolean red, long delay) {
        return new SequentialCommand(
                //new DelayCommand(500),
                new RaceCommand(
                        new FollowPathCommand(drivetrain, Close.intakeGate(drivetrain, red), delay),
                        new IntakeCommand(intake, shooter, Intake.Mode.RUNNING)
                )

                // new IntakeCommand(intake, shooter, Intake.Mode.RUNNING).timeout(100)
        );
    }

//    public Command knockGate(boolean red, boolean fromFirstLine) {
//        return new SequentialCommand(
//                new ParallelCommand(
//                        new FollowPathCommand(drivetrain, fromFirstLine ? knockGateFromFirstPath(drivetrain, red) : knockGateFromSecondPath(drivetrain, red)).timeout(2500),
//                        new IntakeCommand(intake, shooter, Intake.Mode.RUNNING).timeout(700)
//                ),
//                new DelayCommand(0)
//        );
//    }

    public Command goAndShootBallsClose(Pose from, boolean red) {
        return new SequentialCommand(new RaceCommand(
                new ChargeFlywheelCommand(shooter, aimCalculator, red),
                new SequentialCommand(
                        new ParallelCommand(
                                new FollowPathCommand(drivetrain, Close.shoot(drivetrain, from, red)),
                                new IntakeCommand(intake, shooter, Intake.Mode.RUNNING).timeout(1500)
                        ),
                        new RaceCommand(
                                new ShootCommand(shooter, intake).timeout(SHOOT_TIME_MS),
                                new AlignToTargetCommand(drivetrain, red)
                        )

                )
        ), new InstantCommand(shooter::coast));
    }


    public Command startAndShootFar(boolean red) {
        return new RaceCommand(
                new ChargeFlywheelCommand(shooter, aimCalculator, red),
                new SequentialCommand(
                        new FollowPathCommand(drivetrain, Far.startToShoot(drivetrain, red)),
                        new ShootCommand(shooter, intake).timeout(SHOOT_TIME_MS)
                )
        );
    }

    public Command intakeGPPFar(boolean red) {
        return new SequentialCommand(
                //new DelayCommand(500),
                new RaceCommand(
                        new FollowPathCommand(drivetrain, Close.intakeGPP(drivetrain, red)).timeout(5000),
                        new IntakeCommand(intake, shooter, Intake.Mode.RUNNING)
                )
                // new IntakeCommand(intake, shooter, Intake.Mode.RUNNING).timeout(100)
        );
    }

    public Command intakeHPFar(boolean red) {
        return new SequentialCommand(
                //new DelayCommand(500),
                new RaceCommand(
                        new FollowPathCommand(drivetrain, Far.intakeHumanPlayer(drivetrain, red)).timeout(5000),
                        new IntakeCommand(intake, shooter, Intake.Mode.RUNNING)
                )
                // new IntakeCommand(intake, shooter, Intake.Mode.RUNNING).timeout(100)
        );
    }

    public Command intakeHPOffsetFar(boolean red) {
        return new SequentialCommand(
                //new DelayCommand(500),
                new RaceCommand(
                        new FollowPathCommand(drivetrain, Far.intakeHumanPlayerOffsetABit(drivetrain, red)).timeout(5000),
                        new IntakeCommand(intake, shooter, Intake.Mode.RUNNING)
                )
                // new IntakeCommand(intake, shooter, Intake.Mode.RUNNING).timeout(100)
        );
    }

    public Command goAndShootBallsFar(Pose from, boolean red) {
        return new SequentialCommand(new RaceCommand(
                new ChargeFlywheelCommand(shooter, aimCalculator, red),
                new SequentialCommand(
                        new ParallelCommand(
                                new FollowPathCommand(drivetrain, Far.shoot(drivetrain, from, red)),
                                new IntakeCommand(intake, shooter, Intake.Mode.RUNNING).timeout(1500)
                        ),
                        new RaceCommand(
                                new ShootCommand(shooter, intake).timeout(SHOOT_TIME_MS),
                                new AlignToTargetCommand(drivetrain, red)
                        )
                )
        ), new InstantCommand(shooter::coast));
    }
}
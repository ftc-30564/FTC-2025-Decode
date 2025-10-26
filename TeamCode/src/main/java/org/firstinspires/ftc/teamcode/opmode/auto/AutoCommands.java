package org.firstinspires.ftc.teamcode.opmode.auto;

import static org.firstinspires.ftc.teamcode.RobotConstants.Auto.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.ChargeFlywheelCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.PushCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.RawDrivetrainCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.ShootCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.VelocityPair;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;
import org.firstinspires.ftc.teamcode.util.command_lib.ParallelCommand;
import org.firstinspires.ftc.teamcode.util.command_lib.RaceCommand;
import org.firstinspires.ftc.teamcode.util.command_lib.SequentialCommand;

public class AutoCommands {
    private Drivetrain drivetrain;
    private Shooter shooter;
    private Intake intake;
    private Telemetry telemetry;

    public AutoCommands(Drivetrain drivetrain, Shooter shooter, Intake intake, Telemetry telemetry) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;
        this.telemetry = telemetry;
    }

    // Path Commands
    public Command startToShootTrajectory(boolean close, boolean red) {
        return new FollowPathCommand(drivetrain, startToShootPath(drivetrain, close, red), telemetry);
    }

    public Command shootToIntakeTrajectory(BallPose ballPose, boolean close, boolean red) {
        return new FollowPathCommand(drivetrain, shootToIntakePath(drivetrain, ballPose, close, red), telemetry);
    }

    public Command toShootTrajectory(BallPose ballPose, boolean close, boolean red) {
        return new FollowPathCommand(drivetrain, toShootPath(drivetrain, ballPose, close, red), telemetry);
    }

    // Intake Commands
    public Command runIntake() {
        return new IntakeCommand(intake, Intake.Mode.RUNNING);
    }

    // Shoot Commands
    public Command chargeClose() {
        return new ChargeFlywheelCommand(shooter, new VelocityPair(215, 215));
    }

    public Command chargeFar() {
        return new ChargeFlywheelCommand(shooter, new VelocityPair(220, 220));
    }

    public Command shootClose() {
        return new ShootCommand(shooter, intake, new VelocityPair(215, 215));
    }

    public Command shootFar() {
        return new ShootCommand(shooter, intake, new VelocityPair(220, 220));
    }




    // Commands that should be used in auto
    public Command startAndShoot(boolean close, boolean red) {
        return new SequentialCommand(
                new RaceCommand(
                        startToShootTrajectory(close, red),
                        chargeClose()
                ),
                shootClose().timeout(SHOOT_TIME_MS)
        ) ;
    }

    public Command driveAndIntakeBalls(BallPose ballPose, boolean close, boolean red) {
        return new SequentialCommand(
                new RaceCommand(
                        shootToIntakeTrajectory(ballPose, close, red),
                        runIntake()
                )
//                new RaceCommand(
//                        new RawDrivetrainCommand(drivetrain, 0.2, 0, 0).timeout(2000),
//                        runIntake()
//                )
        );
    }

    public Command goAndShootBalls(BallPose ballPose, boolean close, boolean red) {
        return new SequentialCommand(
                new RaceCommand(
                        toShootTrajectory(ballPose, close, red),
                        chargeClose()
                ),
                shootClose().timeout(SHOOT_TIME_MS)
        );
    }
}

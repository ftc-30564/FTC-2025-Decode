package org.firstinspires.ftc.teamcode.opmode.auto;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.ChargeFlywheelCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.ShootCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;
import org.firstinspires.ftc.teamcode.util.command_lib.CommandScheduler;
import org.firstinspires.ftc.teamcode.util.command_lib.ParallelCommand;
import org.firstinspires.ftc.teamcode.util.command_lib.SequentialCommand;

// Placeholder auto
public class MainAuto extends LinearOpMode {
    private Follower follower;
    private Intake intake;
    private Shooter shooter;

    @Override
    public void runOpMode() {
        follower = Constants.createFollower(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);

        CommandScheduler scheduler = new CommandScheduler();

        Command command = new SequentialCommand(
                new ParallelCommand(
                        new FollowPathCommand(follower, RobotConstants.Auto.BLUE_STARTING_CLOSE, RobotConstants.Auto.startToShoot(follower, true, false)),
                        new ChargeFlywheelCommand(shooter, 200).timeout(1000)
                ),
                new ShootCommand(shooter, 200),
                new ParallelCommand(
                        new FollowPathCommand(follower, RobotConstants.Auto.BLUE_SHOOT_CLOSE, RobotConstants.Auto.shootToIntake(follower, RobotConstants.Auto.BallPose.BLUE_PPG, true, false)),
                        new IntakeCommand(intake, Intake.Mode.INTAKING).timeout(1000)
                )
        );

        waitForStart();
        while (opModeIsActive()) {
            scheduler.run(command);
        }
    }
}

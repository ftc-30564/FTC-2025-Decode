package org.firstinspires.ftc.teamcode.opmode.teleop.debug;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.TempUnit;
import org.firstinspires.ftc.teamcode.InterpolationPoints;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.AimCalculator;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Interpolator;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp(group = "Tests")
public class AutoStarting extends LinearOpMode {

    @Override
    public void runOpMode() {
        Drivetrain drivetrain = new Drivetrain(hardwareMap);

        MultipleTelemetry mT = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        waitForStart();

        drivetrain.startTeleopDrive();
        drivetrain.setStartingPose(RobotConstants.AutoPoses.RED_STARTING_CLOSE);

        while (opModeIsActive()) {
            drivetrain.update();

            drivetrain.setTeleopDrive(
                    gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                    gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                    -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT,
                    false);


            mT.addData("Robot x", pedroToAdvScope(drivetrain.getPose()).getX());
            mT.addData("Robot y", pedroToAdvScope(drivetrain.getPose()).getY());
            mT.addData("Robot heading", pedroToAdvScope(drivetrain.getPose()).getHeading());

            mT.update();
        }
    }

    public Pose pedroToAdvScope(Pose pose) {
        Pose ret = pose.unaryMinus().plus(new Pose(144, 144, 0));
        ret = ret.setHeading(ret.getHeading() * -1).getAsCoordinateSystem(FTCCoordinates.INSTANCE);
        return ret;
    }

}

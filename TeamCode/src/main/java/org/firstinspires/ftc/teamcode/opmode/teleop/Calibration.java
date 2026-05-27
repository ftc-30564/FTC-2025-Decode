package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@TeleOp
public class Calibration extends LinearOpMode {
    private Drivetrain drivetrain;

    @Override
    public void runOpMode() {
        this.drivetrain = new Drivetrain(hardwareMap);

        waitForStart();

        this.drivetrain.startTeleopDrive();

        while (opModeIsActive()) {
            this.drivetrain.update();

            telemetry.addLine("CALIBRATION");

            telemetry.addLine("Press Dpad_Down to reset position.");

            if (gamepad1.dpad_down) {
                this.drivetrain.setPose(new Pose(10.5, 10.5, Math.toRadians(90)));
            }

            outputPose("Robot Pose", drivetrain.getPose());
            outputPose("Red Gatetake", RobotConstants.AutoPoses.RED_GATETAKE);
            outputPose("Blue Gatetake", RobotConstants.AutoPoses.BLUE_GATETAKE);
            outputPose("Red Start Auto Close", RobotConstants.AutoPoses.RED_STARTING_CLOSE);
            outputPose("Blue Start Auto Close", RobotConstants.AutoPoses.BLUE_STARTING_CLOSE);

            telemetry.update();
        }
    }

    public void outputPose(String name, Pose pose) {
        telemetry.addData(name + " x", pose.getX());
        telemetry.addData(name + " y", pose.getY());
        telemetry.addData(name + " theta", Math.toDegrees(pose.getHeading()));

    }
}

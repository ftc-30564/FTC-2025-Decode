package org.firstinspires.ftc.teamcode.opmode.teleop.debug;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;

@TeleOp(group = "Tests")
public class AlignToTargetTest extends LinearOpMode {
    public Drivetrain drivetrain;
    public Limelight limelight;

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        limelight = new Limelight(hardwareMap);

        limelight.setRedGoalPipeline();

        drivetrain.setStartingPose(new Pose(0, 0, 0));

        waitForStart();

        drivetrain.startTeleopDrive();
        limelight.start();

        while (opModeIsActive()) {

            drivetrain.update();

            if (gamepad1.left_bumper) {
                drivetrain.setTeleopDrive(
                        -gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                        -gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                        -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT,
                        limelight.getYawTarget());
            }
            else {
                drivetrain.setTeleopDrive(
                        -gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                        -gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                        -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT,
                        false);
            }

            telemetry.addData("Limelight offset", limelight.getYawTarget());
            telemetry.update();
        }
    }
}

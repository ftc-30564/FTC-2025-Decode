package org.firstinspires.ftc.teamcode.opmode.teleop.debug;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Webcam;

@TeleOp(group = "Tests")
public class AlignToTargetTest extends LinearOpMode {
    public Drivetrain drivetrain;
    public Webcam webcam;


    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        webcam = new Webcam(hardwareMap);

        drivetrain.setStartingPose(new Pose(0, 0, 0));

        webcam.init();

        waitForStart();
        drivetrain.startTeleopDrive();

        while (opModeIsActive()) {

            drivetrain.update();

            double turnAmt = -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT;

            if (gamepad1.right_bumper) {
                turnAmt = webcam.getOffsetApriltagInches(24, telemetry) * -0.03;
            }

            drivetrain.setTeleopDrive(
                    -gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                    -gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                    turnAmt,
                    false);

            telemetry.update();
        }

        webcam.close();
    }
}

package org.firstinspires.ftc.teamcode.opmode.teleop.debug;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Webcam;

@TeleOp(group = "Tests")
public class WebcamTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        Webcam webcam = new Webcam(hardwareMap);

        webcam.init();
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Red offset inches", webcam.getOffsetApriltagInches(24, telemetry));
            telemetry.addData("Red offset degrees", webcam.getOffsetApriltagDegrees(24));

            telemetry.update();
        }

        webcam.close();
    }
}

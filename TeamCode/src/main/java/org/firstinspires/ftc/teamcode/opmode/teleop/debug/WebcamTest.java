package org.firstinspires.ftc.teamcode.opmode.teleop.debug;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Webcam;

@TeleOp
public class WebcamTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        Webcam webcam = new Webcam(hardwareMap);

        webcam.init();
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Red apriltag position", webcam.getOffsetRedTarget());

            telemetry.update();
        }

        webcam.close();
    }
}

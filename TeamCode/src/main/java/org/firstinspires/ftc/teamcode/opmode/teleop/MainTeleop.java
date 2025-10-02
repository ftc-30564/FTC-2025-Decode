package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class MainTeleop extends LinearOpMode {

    @Override
    public void runOpMode() {
        DcMotor motor = hardwareMap.get(DcMotor.class, "motor");

        waitForStart();

        while (opModeIsActive()) {
            motor.setPower(0.5);
        }
    }
}

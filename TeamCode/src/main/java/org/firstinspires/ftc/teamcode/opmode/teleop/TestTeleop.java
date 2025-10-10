package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class TestTeleop extends LinearOpMode {

    @Override
    public void runOpMode() {
        DcMotorEx frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeft");
        DcMotorEx backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeft");
        DcMotorEx frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRight");
        DcMotorEx backRightMotor = hardwareMap.get(DcMotorEx.class, "backRight");

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                frontLeftMotor.setPower(0.25);
            }
            else if (gamepad1.dpad_down) {
                backRightMotor.setPower(0.25);
            }
            else if (gamepad1.dpad_left) {
                backLeftMotor.setPower(0.25);
            }
            else if (gamepad1.dpad_right) {
                frontRightMotor.setPower(0.25);
            }
            else {
                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0);
            }
        }
    }
}

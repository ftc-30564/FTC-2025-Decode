package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class MainTeleop extends LinearOpMode {

    @Override
    public void runOpMode() {
        Intake intake = new Intake(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.right_bumper){
                intake.run();
            }
            else if (gamepad1.left_bumper) {
                intake.barf();
            }
            else {
                intake.stop();
            }
        }
    }
}

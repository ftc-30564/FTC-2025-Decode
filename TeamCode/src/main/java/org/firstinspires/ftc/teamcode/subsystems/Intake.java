package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Intake {
    private CRServo firstIntake;
    private CRServo secondIntake;

    public Intake(HardwareMap hardwareMap) {
      firstIntake = hardwareMap.get(CRServo.class, "firstIntake");
      secondIntake = hardwareMap.get(CRServo.class, "secondIntake");

    }
    public void run() {
        firstIntake.setPower(Constants.FIRST_INTAKE_RUN_SPEED);
        secondIntake.setPower(Constants.SECOND_INTAKE_RUN_SPEED);
    }

    public void stop() {
        firstIntake.setPower(0);
        secondIntake.setPower(0);
    }
    public void barf() {
        firstIntake.setPower(-Constants.FIRST_INTAKE_RUN_SPEED);
        secondIntake.setPower(-Constants.SECOND_INTAKE_RUN_SPEED);
    }
}
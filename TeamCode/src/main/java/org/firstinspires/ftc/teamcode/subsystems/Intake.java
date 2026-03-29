package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotConstants;

public class Intake {
    private DcMotor firstIntake;

    public static enum Mode {
        RUNNING,
        BARFING,
        RESTING
    }

    public Intake(HardwareMap hardwareMap) {
      firstIntake = hardwareMap.get(DcMotor.class, RobotConstants.Intake.FIRST_INTAKE_NAME);
    }
    public void run() {
        firstIntake.setPower(RobotConstants.Intake.FIRST_INTAKE_RUN_SPEED);
    }

    public void stop() {
        firstIntake.setPower(0);
    }
    public void barf() {
        firstIntake.setPower(-RobotConstants.Intake.FIRST_INTAKE_RUN_SPEED);
    }
    public void spit() {
        firstIntake.setPower(1);
    }
}
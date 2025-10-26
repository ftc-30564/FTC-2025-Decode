package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotConstants;

public class Intake {
    private CRServo firstIntake;
    private CRServo secondIntake;

    public static enum Mode {
        RUNNING,
        BARFING,
        RESTING
    }

    public Intake(HardwareMap hardwareMap) {
      firstIntake = hardwareMap.get(CRServo.class, RobotConstants.Intake.FIRST_INTAKE_NAME);
      secondIntake = hardwareMap.get(CRServo.class, RobotConstants.Intake.SECOND_INTAKE_NAME);

    }
    public void run() {
        firstIntake.setPower(RobotConstants.Intake.FIRST_INTAKE_RUN_SPEED);
        secondIntake.setPower(RobotConstants.Intake.SECOND_INTAKE_RUN_SPEED);
    }

    public void stop() {
        firstIntake.setPower(0);
        secondIntake.setPower(0);
    }
    public void barf() {
        firstIntake.setPower(-RobotConstants.Intake.FIRST_INTAKE_RUN_SPEED);
        secondIntake.setPower(-RobotConstants.Intake.SECOND_INTAKE_RUN_SPEED);
    }
}
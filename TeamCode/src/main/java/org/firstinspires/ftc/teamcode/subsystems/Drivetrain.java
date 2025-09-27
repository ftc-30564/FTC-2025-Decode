package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drivetrain {
    // define all your variables here
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    public Drivetrain(HardwareMap hardwareMap) {
        // set your variables here
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
    }

    // functions
    public void setMecanumDrive(double forward, double strafe, double turn) {
        frontLeftMotor.setPower(forward + turn + strafe);
        frontRightMotor.setPower(forward - turn - strafe);
        backLeftMotor.setPower(forward + turn - strafe);
        backRightMotor.setPower(forward - turn + strafe);
    }
}

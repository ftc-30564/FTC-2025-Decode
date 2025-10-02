package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    private DcMotorEx leftShooter;
    private DcMotorEx rightShooter;

    public Shooter(HardwareMap hardwareMap) {
        leftShooter = hardwareMap.get(DcMotorEx.class, "leftShooter");
        rightShooter = hardwareMap.get(DcMotorEx.class, "rightShooter");

        rightShooter.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setPercent(double percent) {
      leftShooter.setPower(percent);
      rightShooter.setPower(percent);
    }
}


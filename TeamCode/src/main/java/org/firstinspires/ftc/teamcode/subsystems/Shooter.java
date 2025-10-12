package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.RobotConfigNameable;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.RobotConstants;

public class Shooter {
    private DcMotorEx leftShooter;
    private DcMotorEx rightShooter;

    public Shooter(HardwareMap hardwareMap) {
        leftShooter = hardwareMap.get(DcMotorEx.class, RobotConstants.Shooter.LEFT_FLYWHEEL_NAME);
        rightShooter = hardwareMap.get(DcMotorEx.class, RobotConstants.Shooter.RIGHT_FLYWHEEL_NAME);

        leftShooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftShooter.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setPercent(double percent) {
      leftShooter.setPower(percent);
      rightShooter.setPower(percent);
    }

    public double getVelocityLeft() {
        return leftShooter.getVelocity(AngleUnit.DEGREES);
    }

    public double getVelocityRight() {
        return rightShooter.getVelocity(AngleUnit.DEGREES);
    }

    public void setLeftShooterToVelocity(double targetVelocity) {
        double currentVelocity = getVelocityLeft();
        double percent = (targetVelocity * RobotConstants.Shooter.VELOCITY_LEFT_FEEDFORWARD) + ((targetVelocity - currentVelocity) * RobotConstants.Shooter.VELOCITY_LEFT_P);
        leftShooter.setPower(percent);
    }

    public void setRightShooterToVelocity(double targetVelocity) {
        double currentVelocity = getVelocityRight();
        double percent = (targetVelocity * RobotConstants.Shooter.VELOCITY_RIGHT_FEEDFORWARD) + ((targetVelocity - currentVelocity) * RobotConstants.Shooter.VELOCITY_RIGHT_P);
        rightShooter.setPower(percent);
    }

    public boolean leftIsAtVelocity(double targetVelocity) {
        double currentVelocity = getVelocityLeft();
        return (currentVelocity >= targetVelocity - RobotConstants.Shooter.VELOCITY_DEADBAND) && (targetVelocity + RobotConstants.Shooter.VELOCITY_DEADBAND >= currentVelocity);
    }

    public boolean rightIsAtVelocity(double targetVelocity) {
        double currentVelocity = getVelocityRight();
        return (currentVelocity >= targetVelocity - RobotConstants.Shooter.VELOCITY_DEADBAND) && (targetVelocity + RobotConstants.Shooter.VELOCITY_DEADBAND >= currentVelocity);
    }
}
package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.RobotConfigNameable;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.util.VelocityPair;

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

    public double getVelocityBottom() {
        return leftShooter.getVelocity(AngleUnit.DEGREES);
    }

    public double getVelocityTop() {
        return rightShooter.getVelocity(AngleUnit.DEGREES);
    }

    public void setToVelocityPair(VelocityPair pair) {
        setBottomShooterToVelocity(pair.bottom);
        setTopShooterToVelocity(pair.top);
    }

    public boolean isAtVelocityPair(VelocityPair pair) {
        return bottomIsAtVelocity(pair.bottom) && topIsAtVelocity(pair.top);
    }

    public void setBottomShooterToVelocity(double targetVelocity) {
        double currentVelocity = getVelocityBottom();
        double percent = (targetVelocity * RobotConstants.Shooter.VELOCITY_LEFT_FEEDFORWARD) + ((targetVelocity - currentVelocity) * RobotConstants.Shooter.VELOCITY_LEFT_P);
        leftShooter.setPower(percent);
    }

    public void setTopShooterToVelocity(double targetVelocity) {
        double currentVelocity = getVelocityTop();
        double percent = (targetVelocity * RobotConstants.Shooter.VELOCITY_RIGHT_FEEDFORWARD) + ((targetVelocity - currentVelocity) * RobotConstants.Shooter.VELOCITY_RIGHT_P);
        rightShooter.setPower(percent);
    }

    public boolean bottomIsAtVelocity(double targetVelocity) {
        double currentVelocity = getVelocityBottom();
        return (currentVelocity >= targetVelocity - RobotConstants.Shooter.VELOCITY_DEADBAND) && (targetVelocity + RobotConstants.Shooter.VELOCITY_DEADBAND >= currentVelocity);
    }

    public boolean topIsAtVelocity(double targetVelocity) {
        double currentVelocity = getVelocityTop();
        return (currentVelocity >= targetVelocity - RobotConstants.Shooter.VELOCITY_DEADBAND) && (targetVelocity + RobotConstants.Shooter.VELOCITY_DEADBAND >= currentVelocity);
    }
}
package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.util.VelocityPair;

public class Shooter {
    private DcMotorEx bottomFlywheel;
    private DcMotorEx topFlywheel;
    private CRServo shooterPusher;

    public Shooter(HardwareMap hardwareMap) {
        shooterPusher = hardwareMap.get(CRServo.class, RobotConstants.Intake.PUSHER_NAME);

        shooterPusher.setDirection(DcMotorSimple.Direction.REVERSE);

        bottomFlywheel = hardwareMap.get(DcMotorEx.class, RobotConstants.Shooter.LEFT_FLYWHEEL_NAME);
        topFlywheel = hardwareMap.get(DcMotorEx.class, RobotConstants.Shooter.RIGHT_FLYWHEEL_NAME);

        bottomFlywheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomFlywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        bottomFlywheel.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setPercent(double percent) {
      bottomFlywheel.setPower(percent);
      topFlywheel.setPower(percent);
    }

    public double getVelocityBottom() {
        return bottomFlywheel.getVelocity(AngleUnit.DEGREES);
    }

    public double getVelocityTop() {
        return topFlywheel.getVelocity(AngleUnit.DEGREES);
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
        bottomFlywheel.setPower(percent);
    }

    public void setTopShooterToVelocity(double targetVelocity) {
        double currentVelocity = getVelocityTop();
        double percent = (targetVelocity * RobotConstants.Shooter.VELOCITY_RIGHT_FEEDFORWARD) + ((targetVelocity - currentVelocity) * RobotConstants.Shooter.VELOCITY_RIGHT_P);
        topFlywheel.setPower(percent);
    }

    public boolean bottomIsAtVelocity(double targetVelocity) {
        double currentVelocity = getVelocityBottom();
        return (currentVelocity >= targetVelocity - RobotConstants.Shooter.VELOCITY_DEADBAND) && (targetVelocity + RobotConstants.Shooter.VELOCITY_DEADBAND >= currentVelocity);
    }

    public boolean topIsAtVelocity(double targetVelocity) {
        double currentVelocity = getVelocityTop();
        return (currentVelocity >= targetVelocity - RobotConstants.Shooter.VELOCITY_DEADBAND) && (targetVelocity + RobotConstants.Shooter.VELOCITY_DEADBAND >= currentVelocity);
    }

    public void runPusher() {
        shooterPusher.setPower(1);
    }

    public void stopPusher() {
        shooterPusher.setPower(0);
    }

    public void barfPusher() {
        shooterPusher.setPower(-1);
    }

}
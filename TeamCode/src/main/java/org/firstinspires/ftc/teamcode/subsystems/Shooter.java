package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.util.VelocityPair;

public class Shooter {
    private DcMotorEx bottomFlywheel;
    private DcMotorEx topFlywheel;
    private DcMotorEx shooterPusher;
    private Telemetry telemetry;
    private HardwareMap hardwareMap;
    private boolean isShooting = false;
    private double voltage = 12.0;
    private double velocityBottom = 0;
    private double velocityTop = 0;

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        shooterPusher = hardwareMap.get(DcMotorEx.class, RobotConstants.Intake.PUSHER_NAME);
        shooterPusher.setDirection(DcMotorSimple.Direction.REVERSE);

        bottomFlywheel = hardwareMap.get(DcMotorEx.class, RobotConstants.Shooter.LEFT_FLYWHEEL_NAME);
        bottomFlywheel.setDirection(DcMotorSimple.Direction.REVERSE);
        topFlywheel = hardwareMap.get(DcMotorEx.class, RobotConstants.Shooter.RIGHT_FLYWHEEL_NAME);

        bottomFlywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        topFlywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
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

    public void update() {
        this.voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();
        this.velocityBottom = getVelocityBottom();
        this.velocityTop = getVelocityTop();
    }

    public void setBottomShooterToVelocity(double targetVelocity) {
        double currentVelocity = this.velocityBottom;
        // voltage compensation


        double velocityBottomFeedforward12v = (RobotConstants.Shooter.VELOCITY_BOTTOM_FEEDFORWARD) * (12.6 / 12);
        double adjustedFeedforward = velocityBottomFeedforward12v * (12 / voltage);

        double percent = (targetVelocity * adjustedFeedforward) + (RobotConstants.Shooter.IS_P_ENABLED ? ((targetVelocity - currentVelocity) * (isShooting ? RobotConstants.Shooter.VELOCITY_BOTTOM_P_SHOOT : RobotConstants.Shooter.VELOCITY_BOTTOM_P_STANDBY)) : 0);
        bottomFlywheel.setPower(percent);
    }

    public void setTopShooterToVelocity(double targetVelocity) {
        double currentVelocity = this.velocityTop;
        // voltage compensation
        double velocityTopFeedforward12v = (RobotConstants.Shooter.VELOCITY_TOP_FEEDFORWARD) * (12.6 / 12);
        double adjustedFeedforward = velocityTopFeedforward12v * (12 / voltage);

        double percent = (targetVelocity * adjustedFeedforward) + (RobotConstants.Shooter.IS_P_ENABLED ? ((targetVelocity - currentVelocity) * (isShooting ? RobotConstants.Shooter.VELOCITY_TOP_P_SHOOT : RobotConstants.Shooter.VELOCITY_TOP_P_STANDBY)) : 0);
        topFlywheel.setPower(percent);
    }

    public void coast() {
        bottomFlywheel.setPower(0.3);
        topFlywheel.setPower(0.3);
    }

    public boolean bottomIsAtVelocity(double targetVelocity) {
        double currentVelocity = getVelocityBottom();
        return (currentVelocity >= targetVelocity - RobotConstants.Shooter.VELOCITY_DEADBAND) && (targetVelocity + RobotConstants.Shooter.VELOCITY_DEADBAND >= currentVelocity);
    }

    public boolean topIsAtVelocity(double targetVelocity) {
        double currentVelocity = getVelocityTop();
        return (currentVelocity >= targetVelocity - RobotConstants.Shooter.VELOCITY_DEADBAND) && (targetVelocity + RobotConstants.Shooter.VELOCITY_DEADBAND >= currentVelocity);
    }

    public double getTopCurrent() {
        return topFlywheel.getCurrent(CurrentUnit.MILLIAMPS);
    }

    public double getBottomCurrent() {
        return bottomFlywheel.getCurrent(CurrentUnit.MILLIAMPS);
    }

    public void runPusher() {
        shooterPusher.setPower(0.85);
        this.isShooting = true;
    }

    public void stopPusher() {
        shooterPusher.setPower(0);
        this.isShooting = false;
    }

    public void barfPusher() {
        shooterPusher.setPower(-0.8);
        this.isShooting = false;
    }

    public void runBackPusher(){shooterPusher.setPower(-0.4);
    this.isShooting = false;}
}
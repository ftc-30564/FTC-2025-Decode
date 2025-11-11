package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.util.VelocityPair;

public class Shooter {
    private DcMotorEx bottomFlywheel;
    private DcMotorEx topFlywheel;
    private CRServo shooterPusher;
    private Telemetry telemetry;

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        shooterPusher = hardwareMap.get(CRServo.class, RobotConstants.Intake.PUSHER_NAME);
        shooterPusher.setDirection(DcMotorSimple.Direction.REVERSE);

        bottomFlywheel = hardwareMap.get(DcMotorEx.class, RobotConstants.Shooter.LEFT_FLYWHEEL_NAME);
        bottomFlywheel.setDirection(DcMotorSimple.Direction.REVERSE);

        topFlywheel = hardwareMap.get(DcMotorEx.class, RobotConstants.Shooter.RIGHT_FLYWHEEL_NAME);

        bottomFlywheel.setVelocityPIDFCoefficients(RobotConstants.Shooter.VELOCITY_BOTTOM_P, 0, 0, RobotConstants.Shooter.VELOCITY_BOTTOM_FEEDFORWARD);
        topFlywheel.setVelocityPIDFCoefficients(RobotConstants.Shooter.VELOCITY_TOP_P, 0, 0, RobotConstants.Shooter.VELOCITY_TOP_FEEDFORWARD);
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
//        double currentVelocity = getVelocityBottom();
//        double percent = (targetVelocity * RobotConstants.Shooter.VELOCITY_BOTTOM_FEEDFORWARD) + ((targetVelocity - currentVelocity) * RobotConstants.Shooter.VELOCITY_BOTTOM_P);
//        bottomFlywheel.setPower(percent);

        bottomFlywheel.setVelocity(targetVelocity, AngleUnit.DEGREES);
    }

    public void setTopShooterToVelocity(double targetVelocity) {
//        double currentVelocity = getVelocityTop();
//        double percent = (targetVelocity * RobotConstants.Shooter.VELOCITY_TOP_FEEDFORWARD) + ((targetVelocity - currentVelocity) * RobotConstants.Shooter.VELOCITY_TOP_P);
//        topFlywheel.setPower(percent);

        topFlywheel.setVelocity(targetVelocity, AngleUnit.DEGREES);
    }

    public void updateTopShooterPIDF(double p, double i, double d) {
        topFlywheel.setVelocityPIDFCoefficients(p, i, d, RobotConstants.Shooter.VELOCITY_TOP_FEEDFORWARD);
    }

    public void updateBottomShooterPIDF(double p, double i, double d) {
        bottomFlywheel.setVelocityPIDFCoefficients(p, i, d, RobotConstants.Shooter.VELOCITY_BOTTOM_FEEDFORWARD);
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

    public void runBackPusher(){
        shooterPusher.setPower(-.45);
    }
}
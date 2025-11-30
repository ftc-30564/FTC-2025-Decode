package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
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
    private HardwareMap hardwareMap;
    private double bottomVelocityP = RobotConstants.Shooter.VELOCITY_BOTTOM_P;
    private double topVelocityP = RobotConstants.Shooter.VELOCITY_TOP_P;

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        shooterPusher = hardwareMap.get(CRServo.class, RobotConstants.Intake.PUSHER_NAME);
        shooterPusher.setDirection(DcMotorSimple.Direction.REVERSE);

        bottomFlywheel = hardwareMap.get(DcMotorEx.class, RobotConstants.Shooter.LEFT_FLYWHEEL_NAME);
        bottomFlywheel.setDirection(DcMotorSimple.Direction.REVERSE);

        topFlywheel = hardwareMap.get(DcMotorEx.class, RobotConstants.Shooter.RIGHT_FLYWHEEL_NAME);
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
        // voltage compensation
        double voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();
        double adjustedFeedforward = RobotConstants.Shooter.VELOCITY_BOTTOM_FEEDFORWARD_12V * (12 / voltage);

        telemetry.addData("voltage", voltage);

        double percent = (targetVelocity * adjustedFeedforward) + ((targetVelocity - currentVelocity) * bottomVelocityP);
        bottomFlywheel.setPower(percent);
    }

    public void setTopShooterToVelocity(double targetVelocity) {
        double currentVelocity = getVelocityTop();
        // voltage compensation
        double adjustedFeedforward = RobotConstants.Shooter.VELOCITY_TOP_FEEDFORWARD_12V * (12 / hardwareMap.voltageSensor.iterator().next().getVoltage());

        double percent = (targetVelocity * adjustedFeedforward) + ((targetVelocity - currentVelocity) * topVelocityP);
        topFlywheel.setPower(percent);
    }

    public void updateTopShooterP(double p) {
        this.topVelocityP = p;
    }

    public void updateBottomShooterP(double p) {
        this.bottomVelocityP = p;
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

    public void runBackPusher(){shooterPusher.setPower(-.45);}
}
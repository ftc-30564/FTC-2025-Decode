package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Drivetrain {
    private Follower follower;
    private Pose holdPoint;
    private IMU imu;
    private double lastError = 0;
    private long lastTime = System.nanoTime();

    private double imuOffset = 0;

    public Drivetrain(HardwareMap hardwareMap) {
        follower = Constants.createFollower(hardwareMap);
        imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.UP;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

        imu.initialize(new IMU.Parameters(orientationOnRobot));
    }

    public void startTeleopDrive(){
        follower.startTeleopDrive();
    }

    public void setTeleopDrive(double forward, double strafe, double turn, boolean isRobotCentric){
        follower.setTeleOpDrive(forward, strafe, turn, isRobotCentric);
    }

    public double setTeleopDrive(double forward, double strafe, double turn, double errorDegrees){
        long now = System.nanoTime();
        double dt = (now - lastTime) / 1e9;
        lastTime = now;

        double derivative = RobotConstants.Drive.DRIVE_SNAP_TO_ANGLE_D * ((errorDegrees - lastError) / dt);
        double targetPower = (errorDegrees * RobotConstants.Drive.DRIVE_SNAP_TO_ANGLE_P)
                + (derivative * RobotConstants.Drive.DRIVE_SNAP_TO_ANGLE_D);

        if (targetPower > 0.3)
            targetPower = 0.3;

        if (targetPower < -0.3)
            targetPower = -0.3;

        if (errorDegrees != 0) {
            lastError = errorDegrees;
            setTeleopDrive(forward, strafe, targetPower, false);
            return targetPower;
        }
        follower.setTeleOpDrive(forward, strafe, turn, false);
        return 0;
    }

    public void resetImu() {
        imu.resetYaw();
    }
    public void resetImu(double offset) {
        imuOffset = offset;
        imu.resetYaw();
    }

    public double getImuAngleDegrees() {
        return  imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + imuOffset;
    }

    public void update(){
        follower.update();
    }

    public Pose getPose(){
        return follower.getPose();
    }

    public void setPose(Pose pose) {
        follower.setPose(pose);
    }

    public void setStartingPose(Pose pose){
        follower.setStartingPose(pose);
    }

    public void holdPoint(){
        follower.holdPoint(holdPoint);
    }

    public void setHoldPoint(){
        holdPoint = getPose();
    }

    public void zeroHeading() {
        setPose(new Pose(getPose().getX(), getPose().getY(),0));
    }

    public void setMaxPower(double power){follower.setMaxPower(power);}

    public void oneEightyHeading() {
        setPose(new Pose(getPose().getX(), getPose().getY(),Math.toRadians(180)));
    }

    public double getDistanceFromGoal(boolean red){
        Pose goalPose = RobotConstants.Auto.BLUE_GOAL_POSE;
        Pose robotPose = getPose();

        return Math.sqrt(
                Math.pow(goalPose.getY() - robotPose.getY(), 2) + Math.pow(goalPose.getX() - robotPose.getX(), 2));
    }

    public double getAngleFromGoalDegrees(boolean red){
        Pose goalPose = RobotConstants.Auto.BLUE_GOAL_POSE;
        Pose robotPose = getPose();

        return Math.toDegrees(Math.atan2(goalPose.getY() - robotPose.getY(),goalPose.getX() - robotPose.getX()));
    }

    public void followPath(PathChain pathChain, boolean holdEnd) {
        follower.followPath(pathChain, false);
    }

    public boolean isBusy() {
        return follower.isBusy();
    }

    public PathBuilder pathBuilder() {
        return follower.pathBuilder();
    }
}
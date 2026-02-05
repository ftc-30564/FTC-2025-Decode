package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Drivetrain {
    private Follower follower;
    private IMU imu;
    private double lastError = 0;
    private long autoAlignTime = System.nanoTime();
    private long lastVelocityTime = System.nanoTime();
    private Pose lastRecordedPose = new Pose();
    private boolean red = false;

    private double imuOffsetRadians = 0;

    public Drivetrain(HardwareMap hardwareMap) {
        this(hardwareMap, false);
    }

    public Drivetrain(HardwareMap hardwareMap, boolean red) {
        follower = Constants.createFollower(hardwareMap);
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.UP;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        this.red = red;
    }


    public void startTeleopDrive(){
        follower.startTeleopDrive();
    }

    public void setTeleopDrive(double forward, double strafe, double turn, boolean isRobotCentric){
        follower.setTeleOpDrive(forward, strafe, turn, isRobotCentric);
    }

    public double setTeleopDrive(double forward, double strafe, double turn, double errorDegrees){
        long now = System.nanoTime();
        double dt = (now - autoAlignTime) / 1e9;
        autoAlignTime = now;

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

    // auto aim but it's based on coordinates and imu
    public void setGoalCentricDriveV2(double forward, double strafe, double turn, Telemetry telemetry) {
        // blue for now
        double errorDegrees = Math.toDegrees(getImuAngleRadians()) - getAngleFromGoalDegrees();

        double targetPower = getAlignPowerWhileMoving(telemetry);

        if (targetPower > 0.4)
            targetPower = 0.4;

        if (targetPower < -0.4)
            targetPower = -0.4;

        setTeleopDrive(forward, strafe, targetPower, false);
    }

    /**
     * Drivetrain velocity relative to goal. In inches per second
     * @return Pair, where a = side by side, b = back and forth
     */
    public double getAlignPowerWhileMoving(Telemetry telemetry) {
        long currentTime = System.nanoTime();
        double diffSeconds = (currentTime - lastVelocityTime) / 1e9;

        double robotGoalVectorX = getGoalPose().getX() - getPose().getX();
        double robotGoalVectorY = getGoalPose().getY() - getPose().getY();

        double fx = Math.cos(getImuAngleRadians() + Math.PI);
        double fy = Math.sin(getImuAngleRadians() + Math.PI);

        double forwardPos = robotGoalVectorX * fx + robotGoalVectorY * fy;
        double lateralPos = robotGoalVectorX * fy - robotGoalVectorY * fx;

        double robotVelocityX = (1/diffSeconds) * getPose().minus(lastRecordedPose).getX();
        double robotVelocityY = (1/diffSeconds) * getPose().minus(lastRecordedPose).getY();

        double aimError = Math.toDegrees(Math.atan2(lateralPos, forwardPos));
        double leadAngle = (robotVelocityX * fy - robotVelocityY * fx) * 0.3;  // constant for now
        double totalError = leadAngle - aimError;

        telemetry.addData("Robot vel X", robotVelocityX);
        telemetry.addData("Robot vel Y", robotVelocityY);

        telemetry.addData("Aim error", aimError);
        telemetry.addData("Lead angle", leadAngle);

        telemetry.addData("Lateral vel", (robotVelocityX * fy - robotVelocityY * fx));

        double outputPower = totalError * 0.03;


        lastVelocityTime = currentTime;
        lastRecordedPose = getPose();

        return outputPower;
    }

    public void resetImu() {
        imu.resetYaw();
    }
    public void resetImu(double offset) {
        imuOffsetRadians = (offset - getImuAngleRadians());
    }

    public double getImuAngleRadians() {
        return AngleUnit.normalizeRadians(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS) + imuOffsetRadians);
    }

    public void update(){
        follower.update();
    }

    public Pose getPose(){
        return follower.getPose();
    }

    public void setStartingPose(Pose pose){
        follower.setStartingPose(pose);
        resetImu(pose.getHeading());

        lastRecordedPose = pose;
        lastVelocityTime = System.nanoTime();
    }

    public void setPose(Pose pose) {
        follower.setPose(pose);
    }

    public void zeroHeading() {
        setPose(new Pose(getPose().getX(), getPose().getY(),0));
        resetImu();
    }

    public void setMaxPower(double power){follower.setMaxPower(power);}

    public void oneEightyHeading() {
        setPose(new Pose(getPose().getX(), getPose().getY(),Math.PI));
        resetImu(Math.PI);
    }

    public Pose getGoalPose() {
        return red ? RobotConstants.Auto.RED_GOAL_POSE : RobotConstants.Auto.BLUE_GOAL_POSE;
    }

    // returns distance from goal from coordinate standpoint. expects robot pose to be accurate
    public double getDistanceFromGoal(){
        return getDistanceFromGoal(getPose());
    }

    public double getDistanceFromGoal(Pose pose){
        return getGoalPose().distanceFrom(pose);
    }

    public double getAngleFromGoalDegrees(){
        Pose robotPose = getPose();

        return Math.toDegrees(Math.atan2(getGoalPose().getY() - robotPose.getY(), getGoalPose().getX() - robotPose.getX()));
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
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

    public Drivetrain(HardwareMap hardwareMap) {
        follower = Constants.createFollower(hardwareMap);
    }

    public void startTeleopDrive(){
        follower.startTeleopDrive();
    }

    public void setTeleopDrive(double forward, double strafe, double turn, boolean isRobotCentric){
        follower.setTeleOpDrive(forward, strafe, turn, isRobotCentric);
    }

    public double setTeleopDrive(double forward, double strafe, double turn, double errorDegrees){
        double targetPower = (errorDegrees * RobotConstants.Drive.DRIVE_SNAP_TO_ANGLE_P);

        if (targetPower > 0.3)
            targetPower = 0.3;

        if (targetPower < -0.3)
            targetPower = -0.3;

        if (errorDegrees != 0) {
            setTeleopDrive(forward, strafe, targetPower, false);
            return targetPower;
        }
        follower.setTeleOpDrive(forward, strafe, turn, false);
        return 0;
    }

    public void update(){
        follower.update();
    }

    public Pose getPose() {
        return follower.getPose();
    }

    public void setStartingPose(Pose pose){
        follower.setStartingPose(pose);
    }

    public void setPose(Pose pose) {
        follower.setPose(pose);
    }

    public void zeroHeading() {
        setPose(new Pose(getPose().getX(), getPose().getY(),0));
    }

    public void setMaxPower(double power){follower.setMaxPower(power);}

    public void oneEightyHeading() {
        setPose(new Pose(getPose().getX(), getPose().getY(),Math.PI));
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
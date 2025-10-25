package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Drivetrain {
    private Follower follower;
    private Pose holdPoint;

    public Drivetrain(HardwareMap hardwareMap) {
        follower = Constants.createFollower(hardwareMap);
    }

    public void startTeleopDrive(){
        follower.startTeleopDrive();
    }
    public void setTeleopDrive(double forward, double strafe, double turn, boolean isRobotCentric){
        follower.setTeleOpDrive(forward, strafe, turn, isRobotCentric);
    }
    public void update(){
        follower.update();
    }
    public Pose getPose(){
        return follower.getPose();
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
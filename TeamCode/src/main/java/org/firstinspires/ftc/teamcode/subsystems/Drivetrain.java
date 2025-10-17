package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
    public void update(){
        follower.update();
    }
    public Pose getPose(){
        return follower.getPose();
    }
    public void setStartingPose(Pose pose){
        follower.setStartingPose(pose);
    }
}
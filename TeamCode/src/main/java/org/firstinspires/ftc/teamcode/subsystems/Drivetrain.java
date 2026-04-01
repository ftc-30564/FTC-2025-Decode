package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;
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

    private final PIDFController pidfController = new PIDFController(
            new PIDFCoefficients(
                    RobotConstants.Drive.DRIVE_SNAP_TO_ANGLE_P,
                    0,
                    RobotConstants.Drive.DRIVE_SNAP_TO_ANGLE_D,
                    0
            )
    );

    public Drivetrain(HardwareMap hardwareMap) {
        follower = Constants.createFollower(hardwareMap);
    }

    public void startTeleopDrive(){
        follower.startTeleopDrive();
    }

    public void setTeleopDrive(double forward, double strafe, double turn, boolean isRobotCentric){
        follower.setTeleOpDrive(forward, strafe, turn, isRobotCentric);
    }

    public void setAimedTeleopDrive(double forward, double strafe, boolean red, double angle){
        double targetAngle = angle;
        double error = targetAngle - (getPose().getHeading());

        //telemetry.addData("error before", Math.toDegrees(error));

        if (Math.abs(error) > Math.PI) {
            error -= (Math.PI * 2) * Math.signum(error);
        }

        //telemetry.addData("error after", Math.toDegrees(error));

        pidfController.updateError(error);

        double targetPower = pidfController.run();

//        if (targetPower > )
//            targetPower = 0.8;
//
//        if (targetPower < -0.8)
//            targetPower = -0.8;

        //telemetry.addData("Target power", targetPower);

        follower.setTeleOpDrive(forward, strafe, targetPower, false);

    }

    public void update(){
        follower.update();
    }

    public Pose getPose() {
        return follower.getPose();//new Pose(-follower.getPose().getY(), follower.getPose().getX(), follower.getHeading());
    }

    public Vector getVelocity() {
        return follower.getVelocity();
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
package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.hardware.HardwareMap;

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

    public boolean setAimedTeleopDrive(double forward, double strafe, double angle){
        double targetAngle = angle;
        double error = targetAngle - (getPose().getHeading());

        if (Math.abs(error) > Math.PI) {
            error -= (Math.PI * 2) * Math.signum(error);
        }

        pidfController.setP(RobotConstants.Drive.DRIVE_SNAP_TO_ANGLE_P);
        pidfController.setD(RobotConstants.Drive.DRIVE_SNAP_TO_ANGLE_D);

        pidfController.updateError(error);

        double targetPower = pidfController.run();

        follower.setTeleOpDrive(forward, strafe, targetPower, false);

        return error < Math.toRadians(RobotConstants.Drive.IS_ALIGNED_MARGIN) && error > Math.toRadians(-RobotConstants.Drive.IS_ALIGNED_MARGIN);

    }

    public void update(){
        follower.update();
    }

    public Pose getPose() {
        return follower.getPose();
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
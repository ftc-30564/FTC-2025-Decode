package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;

import org.firstinspires.ftc.teamcode.InterpolationPoints;
import org.firstinspires.ftc.teamcode.RobotConstants;

public class AimCalculator {
    private final Drivetrain drivetrain;
    private boolean red;
    private Pose pose;
    private Vector velocity;

    private Interpolator rpmInterpolator;
    private Interpolator tofInterpolator;

    public Pose redGoal = new Pose(145, 130);
    public Pose blueGoal = new Pose(14,143);

    public static class ShotData {
        public double rpm;
        public double angle;
        public double distance;
        public Pose pose;

        public ShotData(double rpm, double angle, double distance, Pose pose) {
            this.rpm = rpm;
            this.angle = angle;
            this.distance = distance;
            this.pose = pose;
        }
    }

    public AimCalculator(Drivetrain drivetrain, boolean red) {
        this.drivetrain = drivetrain;
        this.red = red;

        this.rpmInterpolator = new Interpolator(InterpolationPoints.rpms_4_19);
        this.tofInterpolator = new Interpolator(InterpolationPoints.tof);
    }

    public ShotData getShotData() {
        //redGoal = new Pose(RobotConstants.Drive.GOAL_POSE_X,RobotConstants.Drive.GOAL_POSE_Y);
        Pose goalPose;
        if (this.red) {
            goalPose = redGoal;
        }
        else {
            goalPose = blueGoal;
        }

        Pose newPose = goalPose;

        double distance = 0;
        double timeOfFlight;

        distance = this.pose.distanceFrom(newPose);
        timeOfFlight = tofInterpolator.get(distance);

        newPose = newPose.minus(
                new Pose(
                        timeOfFlight * this.velocity.getXComponent(),
                        timeOfFlight * this.velocity.getYComponent()
                )
        );

        // iterate 15 times to accurately calculate the target position
//        for (int x = 0; x < 15; x ++) {
//            distance = this.pose.distanceFrom(newPose);
//            timeOfFlight = tofInterpolator.get(distance);
//
//            newPose = newPose.minus(
//                    new Pose(
//                            timeOfFlight * this.velocity.getXComponent(),
//                            timeOfFlight * this.velocity.getYComponent()
//                    )
//            );
//        }

        double newRpm = rpmInterpolator.get(this.pose.distanceFrom(newPose));
        double newAngle = targetAngle(newPose);

        return new ShotData(
                newRpm,
                newAngle,
                distance,
                newPose
        );
    }

    public void update() {
        this.pose = drivetrain.getPose();
        this.velocity = drivetrain.getVelocity();
    }

    public double targetAngle(Pose pose) {
        Pose poseDifference = this.pose.minus(pose);
        return Math.atan2(poseDifference.getY(), poseDifference.getX());
    }
}

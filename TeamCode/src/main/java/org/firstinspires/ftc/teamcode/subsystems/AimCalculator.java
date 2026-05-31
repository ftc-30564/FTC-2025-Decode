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

    public Pose redGoal;
    public Pose blueGoal;

    public Pose newPose;
    public Pose goalPose = blueGoal;

    public double offset = 0;

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

    public AimCalculator(Drivetrain drivetrain) {
        this(drivetrain, false);
    }

    public AimCalculator(Drivetrain drivetrain, boolean red) {
        this.drivetrain = drivetrain;
        this.red = red;

        this.rpmInterpolator = new Interpolator(InterpolationPoints.rpms_5_11);
        this.tofInterpolator = new Interpolator(InterpolationPoints.tof);
    }

    public void setColor(boolean red) {
        this.red = red;
    }

    public ShotData getShotData(boolean calculateSotm) {
        blueGoal = new Pose(RobotConstants.Drive.BLUE_GOAL_POSE_X, RobotConstants.Drive.BLUE_GOAL_POSE_Y);
        redGoal = new Pose(RobotConstants.Drive.RED_GOAL_POSE_X, RobotConstants.Drive.RED_GOAL_POSE_Y);

        if (this.red) {
            goalPose = redGoal;
        }
        else {
            goalPose = blueGoal;
        }

        newPose = goalPose;

        double distance = this.pose.distanceFrom(newPose);
        double timeOfFlight;

        double lookAheadTargetDistance = distance;

//        newPose = newPose.minus(
//                new Pose(
//                        timeOfFlight * this.velocity.getXComponent(),
//                        timeOfFlight * this.velocity.getYComponent()
//                )
//        );
        if (calculateSotm) {
            // iterate 15 times to accurately calculate the target position
            for (int x = 0; x < 15; x ++) {
                timeOfFlight =
                        tofInterpolator.get(lookAheadTargetDistance);

                double offsetX = this.velocity.getXComponent() * timeOfFlight;
                double offsetY = this.velocity.getYComponent() * timeOfFlight;

                newPose = goalPose.minus(new Pose(offsetX, offsetY));
                lookAheadTargetDistance = newPose.distanceFrom(this.pose);
            }
        }



        double newRpm = rpmInterpolator.get(lookAheadTargetDistance);
        double newAngle = targetAngle(newPose);

        return new ShotData(
                newRpm,
                newAngle - Math.toRadians(offset),
                distance,
                newPose
        );
    }

    public void update() {
        update(0);
    }

    public void update(double offset) {
        this.pose = drivetrain.getPose();
        this.velocity = drivetrain.getVelocity();
        this.offset = offset;
    }

    public double targetAngle(Pose pose) {
        Pose poseDifference = this.pose.minus(pose);
        return Math.atan2(poseDifference.getY(), poseDifference.getX());
    }
}

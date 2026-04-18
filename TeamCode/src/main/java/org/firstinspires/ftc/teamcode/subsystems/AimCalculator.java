package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;

import org.firstinspires.ftc.teamcode.InterpolationPoints;

public class AimCalculator {
    private final Drivetrain drivetrain;
    private boolean red;
    private Pose pose;
    private Vector velocity;

    private Interpolator rpmInterpolator;
    private Interpolator tofInterpolator;

    public final Pose redGoal = new Pose(131,131);
    public final Pose blueGoal = new Pose(23,131);

    public static class ShotData {
        public double rpm;
        public double angle;
        public double distance;

        public ShotData(double rpm, double angle, double distance) {
            this.rpm = rpm;
            this.angle = angle;
            this.distance = distance;
        }
    }

    public AimCalculator(Drivetrain drivetrain, boolean red) {
        this.drivetrain = drivetrain;
        this.red = red;

        this.rpmInterpolator = new Interpolator(InterpolationPoints.rpms_2_15);
        this.tofInterpolator = new Interpolator(InterpolationPoints.tof);
    }

    public ShotData getShotData() {
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
                distance
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

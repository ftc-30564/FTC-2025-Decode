package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class Constants {
    public static class Auto {
        // Here are the various positions for autonomous. Each position is stored
        // as a Pose object, which takes an x, y, and a heading (in Radians). This means
        // we must convert each angle into radians via Math.toRadians(deg).
        public static final Pose RED_STARTING_CLOSE = new Pose(105.21, 135.17, Math.toRadians(270));
        public static final Pose RED_SHOOT_CLOSE  = new Pose(86.5,  92.4, Math.toRadians(224));
        public static final Pose RED_SHOOT_FAR    = new Pose(83.4,  18.3, Math.toRadians(238));
        public static final Pose RED_POSITION_PPG = new Pose(101.2, 83.9, 0);
        public static final Pose RED_POSITION_PGP = new Pose(101.2, 59.8, 0);
        public static final Pose RED_POSITION_GPP = new Pose(101.2, 35.4, 0);

        // This just mirrors the red positions.
        public static final Pose BLUE_STARTING_CLOSE = RED_STARTING_CLOSE.mirror();
        public static final Pose BLUE_SHOOT_CLOSE  = RED_SHOOT_CLOSE.mirror();
        public static final Pose BLUE_SHOOT_FAR    = RED_SHOOT_FAR.mirror();
        public static final Pose BLUE_POSITION_PPG = RED_POSITION_PPG.mirror();
        public static final Pose BLUE_POSITION_PGP = RED_POSITION_PGP.mirror();
        public static final Pose BLUE_POSITION_GPP = RED_POSITION_GPP.mirror();

        public static enum BallPose {
            RED_PPG(RED_POSITION_PPG),
            RED_PGP(RED_POSITION_PGP),
            RED_GPP(RED_POSITION_GPP),
            BLUE_PPG(BLUE_POSITION_PPG),
            BLUE_PGP(BLUE_POSITION_PGP),
            BLUE_GPP(BLUE_POSITION_GPP);

            private Pose pose;

            BallPose(Pose pose) {
                this.pose = pose;
            }
        }

        public static PathChain startCloseToShoot(Follower follower, boolean isRed) {
            if (isRed) {
                return follower.pathBuilder()
                        .addPath(new BezierLine(RED_STARTING_CLOSE, RED_SHOOT_CLOSE))
                        .setLinearHeadingInterpolation(RED_STARTING_CLOSE.getHeading(), RED_SHOOT_CLOSE.getHeading())
                        .build();
            }
            return follower.pathBuilder()
                    .addPath(new BezierLine(BLUE_STARTING_CLOSE, BLUE_SHOOT_CLOSE))
                    .setLinearHeadingInterpolation(BLUE_STARTING_CLOSE.getHeading(), BLUE_SHOOT_CLOSE.getHeading())
                    .build();
        }

        public static PathChain fromCloseShootToIntake(Follower follower, BallPose ballPose, boolean isRed) {
            if (isRed) {
                return follower.pathBuilder()
                        .addPath(new BezierLine(RED_SHOOT_CLOSE, ballPose.pose))
                        .setLinearHeadingInterpolation(RED_SHOOT_CLOSE.getHeading(), ballPose.pose.getHeading())
                        .build();
            }
            return follower.pathBuilder()
                    .addPath(new BezierLine(BLUE_SHOOT_CLOSE, ballPose.pose))
                    .setLinearHeadingInterpolation(BLUE_SHOOT_CLOSE.getHeading(), ballPose.pose.getHeading())
                    .build();
        }

        public static PathChain fromIntakeToCloseShoot(Follower follower, BallPose ballPose, boolean isRed) {
            if (isRed) {
                return follower.pathBuilder()
                        .addPath(new BezierLine(ballPose.pose, RED_SHOOT_CLOSE))
                        .setLinearHeadingInterpolation(ballPose.pose.getHeading(), RED_SHOOT_CLOSE.getHeading())
                        .build();
            }
            return follower.pathBuilder()
                    .addPath(new BezierLine(ballPose.pose, BLUE_SHOOT_CLOSE))
                    .setLinearHeadingInterpolation(ballPose.pose.getHeading(), BLUE_SHOOT_CLOSE.getHeading())
                    .build();
        }
    }
}

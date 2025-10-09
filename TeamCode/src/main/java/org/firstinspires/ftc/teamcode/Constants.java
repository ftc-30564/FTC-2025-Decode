package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import java.net.BindException;

public class Constants {
    public static class Auto {
        // Here are the various positions for autonomous. Each position is stored
        // as a Pose object, which takes an x, y, and a heading (in Radians). This means
        // when we create a Pose object we must convert each angle into radians via Math.toRadians(deg).

        // Starting positions
        public static final Pose RED_STARTING_CLOSE = new Pose(105.21, 135.17, Math.toRadians(270));
        public static final Pose RED_STARTING_FAR = new Pose(84, 8.32, Math.toRadians(270));
        // Shooting positions
        public static final Pose RED_SHOOT_CLOSE  = new Pose(86.5,  92.4, Math.toRadians(224));
        public static final Pose RED_SHOOT_FAR    = new Pose(83.4,  18.3, Math.toRadians(238));
        // Intake positions. PRE_INTAKE means the position right before it reaches the first ball.
        public static final Pose RED_PRE_INTAKE_PPG = new Pose(101.2, 83.9, 0);
        public static final Pose RED_PRE_INTAKE_PGP = new Pose(101.2, 59.8, 0);
        public static final Pose RED_PRE_INTAKE_GPP = new Pose(101.2, 35.4, 0);
        // POST_INTAKE means the position right after it intakes the balls.
        public static final Pose RED_POST_INTAKE_PPG = new Pose(124.5, 83.9, 0);
        public static final Pose RED_POST_INTAKE_PGP = new Pose(124.5, 59.8, 0);
        public static final Pose RED_POST_INTAKE_GPP = new Pose(124.5, 35.4, 0);

        // This just mirrors the red positions.
        public static final Pose BLUE_STARTING_CLOSE = RED_STARTING_CLOSE.mirror();
        public static final Pose BLUE_STARTING_FAR = RED_STARTING_FAR.mirror();
        public static final Pose BLUE_SHOOT_CLOSE  = RED_SHOOT_CLOSE.mirror();
        public static final Pose BLUE_SHOOT_FAR    = RED_SHOOT_FAR.mirror();
        public static final Pose BLUE_PRE_INTAKE_PPG = RED_PRE_INTAKE_PPG.mirror();
        public static final Pose BLUE_PRE_INTAKE_PGP = RED_PRE_INTAKE_PGP.mirror();
        public static final Pose BLUE_PRE_INTAKE_GPP = RED_PRE_INTAKE_GPP.mirror();
        public static final Pose BLUE_POST_INTAKE_PPG = RED_POST_INTAKE_PPG.mirror();
        public static final Pose BLUE_POST_INTAKE_PGP = RED_POST_INTAKE_PGP.mirror();
        public static final Pose BLUE_POST_INTAKE_GPP = RED_POST_INTAKE_GPP.mirror();

        // This is an enum that holds the different ball positions.
        public static enum BallPose {
            RED_PPG(RED_PRE_INTAKE_PPG, RED_POST_INTAKE_PPG),
            RED_PGP(RED_PRE_INTAKE_PGP, RED_POST_INTAKE_PGP),
            RED_GPP(RED_PRE_INTAKE_GPP, RED_POST_INTAKE_GPP),
            BLUE_PPG(BLUE_PRE_INTAKE_PPG, BLUE_POST_INTAKE_PPG),
            BLUE_PGP(BLUE_PRE_INTAKE_PGP, BLUE_POST_INTAKE_PGP),
            BLUE_GPP(BLUE_PRE_INTAKE_GPP, BLUE_POST_INTAKE_GPP);

            private Pose pre;
            private Pose post;

            /**
             * Enum that holds the different ball positions. It takes in two Pose objects, pre and post.
             * @param pre The robot position right before intaking the ball
             * @param post The robot position right after intaking the ball
             */
            BallPose(Pose pre, Pose post) {
                this.pre = pre;
                this.post = post;
            }
        }

        /**
         * Generates a PathChain that starts at a starting position and ends at a shooting position.
         * @param follower The follower class
         * @param close Whether the robot starts close or far
         * @param isRed Whether the robot is red or blue. True if red, false if blue.
         * @return The generated PathChain
         */
        public static PathChain startToShoot(Follower follower, boolean close, boolean isRed) {
            // this is an inline if statement that will determine the correct starting and ending position,
            // depending on whether it is close or far, and red or blue;
            Pose start = isRed ? (close ? RED_STARTING_CLOSE : RED_STARTING_FAR) : (close ? BLUE_STARTING_CLOSE : BLUE_STARTING_FAR);
            Pose end = isRed ? (close ? RED_SHOOT_CLOSE : RED_SHOOT_FAR) : (close ? BLUE_SHOOT_CLOSE : BLUE_SHOOT_FAR);

            return follower.pathBuilder()
                    .addPath(new BezierLine(start, end))
                    .setLinearHeadingInterpolation(start.getHeading(), end.getHeading())
                    .build();
        }

        /**
         * Generates a PathChain that starts at a shooting position and ends having intaked a line of balls.
         * @param follower The follower class
         * @param ballPose The ball position to drive and intake from
         * @param close Whether the robot starts close or far
         * @param isRed Whether the robot is red or blue. True if red, false if blue.
         * @return The generated PathChain
         */
        public static PathChain shootToIntake(Follower follower, BallPose ballPose, boolean close, boolean isRed) {
            Pose start = isRed ? (close ? RED_SHOOT_CLOSE : RED_SHOOT_FAR) : (close ? BLUE_SHOOT_CLOSE : BLUE_SHOOT_FAR);

            return follower.pathBuilder()
                    .addPath(new BezierLine(start, ballPose.pre))
                    .setLinearHeadingInterpolation(start.getHeading(), ballPose.pre.getHeading())
                    .addPath(new BezierLine(ballPose.pre, ballPose.post))
                    .setLinearHeadingInterpolation(ballPose.pre.getHeading(), ballPose.post.getHeading())
                    .build();
        }

        /**
         * Generates a PathChain that starts at an intaking position and ends at a shooting position.
         * @param follower The follower class
         * @param ballPose The ball position that the robot starts at
         * @param close Whether the robot shoots close or far
         * @param isRed Whether the robot is red or blue. True if red, false if blue.
         * @return The generated PathChain
         */
        public static PathChain intakeToShoot(Follower follower, BallPose ballPose, boolean close, boolean isRed) {
            Pose end = isRed ? (close ? RED_SHOOT_CLOSE : RED_SHOOT_FAR) : (close ? BLUE_SHOOT_CLOSE : BLUE_SHOOT_FAR);

            return follower.pathBuilder()
                    .addPath(new BezierLine(ballPose.post, end))
                    .setLinearHeadingInterpolation(ballPose.post.getHeading(), end.getHeading())
                    .build();
        }
    }
public class Constants {
    public static double FIRST_INTAKE_RUN_SPEED = 1;
    public static double SECOND_INTAKE_RUN_SPEED = 1;
}

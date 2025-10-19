package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.opencv.core.Point;

public class RobotConstants {
    public static class Drivetrain {
        public static final String FRONT_LEFT_MOTOR_NAME = "frontLeft";
        public static final String FRONT_RIGHT_MOTOR_NAME = "frontRight";
        public static final String BACK_LEFT_MOTOR_NAME = "backRight";
        public static final String BACK_RIGHT_MOTOR_NAME = "backLeft";

        public static final String DEAD_WHEEL_LEFT_NAME = "frontLeft";
        public static final String DEAD_WHEEL_RIGHT_NAME = "frontRight";
        public static final String DEAD_WHEEL_PERP_NAME = "backLeft";

        public static final DcMotorSimple.Direction FRONT_LEFT_MOTOR_DIRECTION = DcMotorSimple.Direction.REVERSE;
        public static final DcMotorSimple.Direction FRONT_RIGHT_MOTOR_DIRECTION = DcMotorSimple.Direction.FORWARD;
        public static final DcMotorSimple.Direction BACK_LEFT_MOTOR_DIRECTION = DcMotorSimple.Direction.REVERSE;
        public static final DcMotorSimple.Direction BACK_RIGHT_MOTOR_DIRECTION = DcMotorSimple.Direction.FORWARD;

        public static final double DEAD_WHEEL_LEFT_DIRECTION = Encoder.FORWARD;
        public static final double DEAD_WHEEL_RIGHT_DIRECTION = Encoder.FORWARD;
        public static final double DEAD_WHEEL_PERP_DIRECTION = Encoder.FORWARD;

        public static final double DEAD_WHEEL_LEFT_OFFSET = 1;
        public static final double DEAD_WHEEL_RIGHT_OFFSET = -1;
        public static final double DEAD_WHEEL_PERP_OFFSET = 1;

        public static final double FORWARD_SPEEDLIMIT = 1;
        public static final double STRAFE_SPEEDLIMIT = 0.7;
        public static final double TURN_SPEEDLIMIT = 0.7;

        public static final Pose RED_GOAL_POSE = new Pose(137.5, 143.2, 0);
        public static final Pose BLUE_GOAL_POSE = RED_GOAL_POSE.mirror();

        public static final double DRIVE_SNAP_TO_ANGLE_P = 0.005;
    }

    public static class Shooter {
        public static final String LEFT_FLYWHEEL_NAME = "rightShooter";
        public static final String RIGHT_FLYWHEEL_NAME = "leftShooter";

        // (TargetVelocity * FeedForward) + ((TargetVelocity - CurrentVelocity) * P) = Percent
        public static final double VELOCITY_LEFT_FEEDFORWARD = 0.5 / 152.47;
        public static final double VELOCITY_RIGHT_FEEDFORWARD = 0.5 / 178.32;
        public static final double VELOCITY_LEFT_P = 0.01;
        public static final double VELOCITY_RIGHT_P = 0.01;
        public static final double VELOCITY_DEADBAND = 10;

        // Manual velocities, cause the limelight might not arrive in time
        public static final double MANUAL_CLOSE_TRIANGLE_TOP_VELOCITY = 200;
        public static final double MANUAL_CLOSE_TRIANGLE_MIDDLE_VELOCITY = 150;

        public static final double MANUAL_FAR_TRIANGLE_VELOCITY = 300;
    }

    public static class Intake {
        public static final String FIRST_INTAKE_NAME = "firstIntake";
        public static final String SECOND_INTAKE_NAME = "secondIntake";
        public static final String PUSHER_NAME = "shooterPusher";

        public static double FIRST_INTAKE_RUN_SPEED = 1;
        public static double SECOND_INTAKE_RUN_SPEED = 1;
    }

    public static class Auto {
        // Here are the various positions for autonomous. Each position is stored
        // as a Pose object, which takes an x, y, and a heading (in Radians). This means
        // when we create a Pose object we must convert each angle into radians via Math.toRadians(deg).

        public static final Pose RED_GOAL_POSE = new Pose(127, 132.67, 0);
        // Starting positions
        public static final Pose RED_STARTING_CLOSE = new Pose(105.21, 135.17, Math.toRadians(270));
        public static final Pose RED_STARTING_FAR = new Pose(84, 8.32, Math.toRadians(270));
        // Shooting positions
        public static final Pose RED_SHOOT_CLOSE = new Pose(86.5, 92.4, Math.toRadians(224));
        public static final Pose RED_SHOOT_FAR = new Pose(83.4, 18.3, Math.toRadians(238));
        // Intake positions. PRE_INTAKE means the position right before it reaches the first ball.
        public static final Pose RED_PRE_INTAKE_PPG = new Pose(101.2, 83.9, 0);
        public static final Pose RED_PRE_INTAKE_PGP = new Pose(101.2, 59.8, 0);
        public static final Pose RED_PRE_INTAKE_GPP = new Pose(101.2, 35.4, 0);
        // POST_INTAKE means the position right after it intakes the balls.
        public static final Pose RED_POST_INTAKE_PPG = new Pose(124.5, 83.9, 0);
        public static final Pose RED_POST_INTAKE_PGP = new Pose(124.5, 59.8, 0);
        public static final Pose RED_POST_INTAKE_GPP = new Pose(124.5, 35.4, 0);

        public static final Pose BLUE_GOAL_POSE = RED_GOAL_POSE.mirror();
        // This just mirrors the red positions.
        public static final Pose BLUE_STARTING_CLOSE = RED_STARTING_CLOSE.mirror();
        public static final Pose BLUE_STARTING_FAR = RED_STARTING_FAR.mirror();
        public static final Pose BLUE_SHOOT_CLOSE = RED_SHOOT_CLOSE.mirror();
        public static final Pose BLUE_SHOOT_FAR = RED_SHOOT_FAR.mirror();
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

            BallPose(Pose pre, Pose post) {
                this.pre = pre;
                this.post = post;
            }
        }

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

        public static PathChain shootToIntake(Follower follower, BallPose ballPose, boolean close, boolean isRed) {
            Pose start = isRed ? (close ? RED_SHOOT_CLOSE : RED_SHOOT_FAR) : (close ? BLUE_SHOOT_CLOSE : BLUE_SHOOT_FAR);

            return follower.pathBuilder()
                    .addPath(new BezierLine(start, ballPose.pre))
                    .setLinearHeadingInterpolation(start.getHeading(), ballPose.pre.getHeading())
                    .addPath(new BezierLine(ballPose.pre, ballPose.post))
                    .setLinearHeadingInterpolation(ballPose.pre.getHeading(), ballPose.post.getHeading())
                    .build();
        }

        public static PathChain intakeToShoot(Follower follower, BallPose ballPose, boolean close, boolean isRed) {
            Pose end = isRed ? (close ? RED_SHOOT_CLOSE : RED_SHOOT_FAR) : (close ? BLUE_SHOOT_CLOSE : BLUE_SHOOT_FAR);

            return follower.pathBuilder()
                    .addPath(new BezierLine(ballPose.post, end))
                    .setLinearHeadingInterpolation(ballPose.post.getHeading(), end.getHeading())
                    .build();
        }
    }
}
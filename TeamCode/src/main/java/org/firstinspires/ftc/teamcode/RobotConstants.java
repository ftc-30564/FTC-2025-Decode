package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.VelocityPair;

// The @Config allows us to configure these numbers in FtcDashboard
public class RobotConstants {
    @Config
    public static class Drive {
        // front and back is swapped
        public static final String FRONT_LEFT_MOTOR_NAME = "backRight"; //backRight
        public static final String FRONT_RIGHT_MOTOR_NAME = "backLeft"; //backLeft
        public static final String BACK_LEFT_MOTOR_NAME = "frontLeft"; //frontLeft
        public static final String BACK_RIGHT_MOTOR_NAME = "frontRight"; // frontRight

        public static final String DEAD_WHEEL_LEFT_NAME = "frontLeft";
        public static final String DEAD_WHEEL_RIGHT_NAME = "backRight";
        public static final String DEAD_WHEEL_PERP_NAME = "backLeft";

        public static final DcMotorSimple.Direction FRONT_LEFT_MOTOR_DIRECTION = DcMotorSimple.Direction.REVERSE;
        public static final DcMotorSimple.Direction FRONT_RIGHT_MOTOR_DIRECTION = DcMotorSimple.Direction.FORWARD;
        public static final DcMotorSimple.Direction BACK_LEFT_MOTOR_DIRECTION = DcMotorSimple.Direction.REVERSE;
        public static final DcMotorSimple.Direction BACK_RIGHT_MOTOR_DIRECTION = DcMotorSimple.Direction.FORWARD;

        public static final double DEAD_WHEEL_LEFT_DIRECTION = Encoder.REVERSE;
        public static final double DEAD_WHEEL_RIGHT_DIRECTION = Encoder.REVERSE;
        public static final double DEAD_WHEEL_PERP_DIRECTION = Encoder.REVERSE;

        // right one should always be negative
        public static final double DEAD_WHEEL_FORWARD_OFFSET = -6.14;
        public static final double DEAD_WHEEL_PERP_OFFSET = -6.8;

        public static final double FORWARD_SPEEDLIMIT = 1;
        public static final double STRAFE_SPEEDLIMIT = 1;
        public static final double TURN_SPEEDLIMIT = 1;

        public static final Pose RED_GOAL_POSE = new Pose(137.5, 143.2, 0);
        public static final Pose BLUE_GOAL_POSE = RED_GOAL_POSE.mirror();

        public static double DRIVE_SNAP_TO_ANGLE_P = 1.55;
        public static double DRIVE_SNAP_TO_ANGLE_D = 0.1;
    }
    @Config
    public static class Shooter {
        public static final String LEFT_FLYWHEEL_NAME = "rightShooter";
        public static final String RIGHT_FLYWHEEL_NAME = "leftShooter";

        // (TargetVelocity * FeedForward) + ((TargetVelocity - CurrentVelocity) * P) = Percent
        public static double VELOCITY_BOTTOM_FEEDFORWARD = 0.0028;//0.00301; 137
        public static double VELOCITY_TOP_FEEDFORWARD = 0.0028;//0.0029;  145
        public static final double VELOCITY_BOTTOM_FEEDFORWARD_12V = (VELOCITY_BOTTOM_FEEDFORWARD) * (12.6 / 12);
        public static final double VELOCITY_TOP_FEEDFORWARD_12V = (VELOCITY_TOP_FEEDFORWARD) * (12.6 / 12);

        public static double VELOCITY_BOTTOM_P_STANDBY = 0.047;
        public static double VELOCITY_TOP_P_STANDBY = 0.047;

        public static double VELOCITY_BOTTOM_P_SHOOT = 0.08;
        public static double VELOCITY_TOP_P_SHOOT = 0.08;
        public static boolean IS_P_ENABLED = true;

        public static final double VELOCITY_DEADBAND = 10;

        public static final VelocityPair CLOSE_VELOCITY = new VelocityPair(154, 154);
        public static final VelocityPair FAR_VELOCITY = new VelocityPair(160, 160);
    }

    @Config
    public static class Intake {
        public static final String FIRST_INTAKE_NAME = "firstIntake";
        public static final String SECOND_INTAKE_NAME = "secondIntake";
        public static final String PUSHER_NAME = "shooterPusher";

        public static double FIRST_INTAKE_RUN_SPEED = 0.95;
    }
    @Config
    public static class AutoPoses {
        public static Pose RED_GOAL_POSE = new Pose(127, 132.67, 0);
        // Starting positions
        public static Pose RED_STARTING_CLOSE = new Pose(110, 134, Math.toRadians(270));
        public static Pose RED_STARTING_FAR = new Pose(89, 8.15, Math.toRadians(270));
        // Shooting positions
        public static Pose RED_SHOOT_CLOSE       = new Pose(84, 76.7, Math.toRadians(227));
        public static Pose RED_SHOOT_FAR         = new Pose(87, 18.4, Math.toRadians(238));

        public static Pose RED_PRE_INTAKE_PPG    = new Pose(86,    83.9, 0);
        public static Pose RED_POST_INTAKE_PPG   = new Pose(123.5, 83.9, 0);

        public static Pose RED_PRE_INTAKE_PGP    = new Pose(88,    59.2, 0);
        public static Pose RED_POST_INTAKE_PGP   = new Pose(130, 58.8, 0);

        public static Pose RED_PRE_INTAKE_GPP    = new Pose(89,    34, 0);
        public static Pose RED_POST_INTAKE_GPP   = new Pose(125.5, 35.4, 0);

        public static Pose RED_PRE_INTAKE_HUMAN  = new Pose(133.5, 18.5, Math.toRadians(330));//new Pose(128.5, 35, Math.toRadians(270));
        public static Pose RED_POST_INTAKE_HUMAN = new Pose(134, 11, Math.toRadians(330));//new Pose(128.5, 13, Math.toRadians(270));

        public static Pose RED_PRE_INTAKE_HUMAN2 = new Pose(112, 10.4, Math.toRadians(10));
        public static Pose RED_POST_INTAKE_HUMAN2 = new Pose(138, 13, Math.toRadians(10));

        public static Pose RED_LINEUP_1          = new Pose(120, 81, Math.toRadians(270));
        public static Pose RED_LINEUP_2          = new Pose(118, 61, Math.toRadians(270));

        public static Pose RED_HIT_GATE_1 = new Pose(127,   75.5, Math.toRadians(270)); // 3, 3
        public static Pose RED_HIT_GATE_2 = new Pose(126,   69, Math.toRadians(270)); // 3, 3

        public static Pose RED_LEAVE_CLOSE       = new Pose(99.5,  78.4, Math.toRadians(270));
        public static Pose RED_LEAVE_FAR         = new Pose(91,    26.5, Math.toRadians(270));

        public static Pose BLUE_GOAL_POSE = new Pose(0, 132.67, 0);

        public static Pose BLUE_STARTING_CLOSE   = new Pose(34.0, 134, Math.toRadians(270)); // 144 - 110
        public static Pose BLUE_STARTING_FAR     = new Pose(55.0, 8.15,   Math.toRadians(270)); // 144 - 89

        public static Pose BLUE_SHOOT_CLOSE      = new Pose(59.5, 78,  Math.toRadians(307)); // 144 - 84
        public static Pose BLUE_SHOOT_FAR        = new Pose(57.0, 18.4,  Math.toRadians(289)); // 144 - 87

        public static Pose BLUE_PRE_INTAKE_PPG   = new Pose(58.0, 83.9,  Math.toRadians(180)); // 144 - 86
        public static Pose BLUE_POST_INTAKE_PPG  = new Pose(20.5, 83.9,  Math.toRadians(180)); // 144 - 125.5

        public static Pose BLUE_PRE_INTAKE_PGP   = new Pose(56.0, 60.2,  Math.toRadians(180)); // 144 - 88
        public static Pose BLUE_POST_INTAKE_PGP  = new Pose(14, 58.8,  Math.toRadians(180)); // 144 - 131.5

        public static Pose BLUE_PRE_INTAKE_GPP   = new Pose(52.0, 35.4,  Math.toRadians(180)); // 144 - 92
        public static Pose BLUE_POST_INTAKE_GPP  = new Pose(12.5, 35.4,  Math.toRadians(180)); // 144 - 130.5

        public static Pose BLUE_PRE_INTAKE_HUMAN  = new Pose(10.5, 18.5, Math.toRadians(210));
        public static Pose BLUE_POST_INTAKE_HUMAN = new Pose(10, 11, Math.toRadians(210));

        public static Pose BLUE_PRE_INTAKE_HUMAN2 = new Pose(40, 10.4, Math.toRadians(180));
        public static Pose BLUE_POST_INTAKE_HUMAN2 = new Pose(6, 13, Math.toRadians(180));

        public static Pose BLUE_LINEUP_1         = new Pose(26, 78, Math.toRadians(270));
        public static Pose BLUE_LINEUP_2         = new Pose(26, 67, Math.toRadians(270));

        public static Pose BLUE_HIT_GATE_1 = new Pose(18.25, 75.8, Math.toRadians(270)); // 144 - 127
        public static Pose BLUE_HIT_GATE_2         = new Pose(18.25, 74.8, Math.toRadians(270)); // 144 - 127
        public static Pose BLUE_LEAVE_CLOSE      = new Pose(44.5, 78.4, Math.toRadians(270)); // 144 - 99.5
        public static Pose BLUE_LEAVE_FAR        = new Pose(53.0, 26.5, Math.toRadians(270)); // 144 - 91
    }

    public static class AutoPaths {
        // Here are the various positions for autonomous. Each position is stored
        // as a Pose object, which takes an x, y, and a heading (in Radians). This means
        // when we create a Pose object we must convert each angle into radians via Math.toRadians(deg).

        public static Pose LAST_REMEMBERED_POSE = new Pose(0, 0, 0);
        public static boolean HAS_POSE = false;

        public static final long SHOOT_TIME_MS = 1250;//1600;

        // This is an enum that holds the different ball positions.
        public enum BallPose {
            PPG,
            PGP,
            GPP,
            HUMAN_PLAYER1,
            HUMAN_PLAYER2
        }

        public enum GatePose {
            NONE,
            FIRST_LINE,
            SECOND_LINE
        }

        public static Pose getPreBallPose(BallPose ballPose, boolean red) {
            switch (ballPose) {
                case GPP:
                    return red ? AutoPoses.RED_PRE_INTAKE_GPP : AutoPoses.BLUE_PRE_INTAKE_GPP;
                case PGP:
                    return red ? AutoPoses.RED_PRE_INTAKE_PGP : AutoPoses.BLUE_PRE_INTAKE_PGP;
                case PPG:
                    return red ? AutoPoses.RED_PRE_INTAKE_PPG : AutoPoses.BLUE_PRE_INTAKE_PPG;
                case HUMAN_PLAYER1:
                    return red ? AutoPoses.RED_PRE_INTAKE_HUMAN : AutoPoses.BLUE_PRE_INTAKE_HUMAN;
                case HUMAN_PLAYER2:
                    return red ? AutoPoses.RED_PRE_INTAKE_HUMAN2 : AutoPoses.BLUE_PRE_INTAKE_HUMAN2;
            }

            throw new IllegalArgumentException("ballPose isn't GPP, PGP, PPG, or human somehow");
        }

        public static Pose getPostBallPose(BallPose ballPose, boolean red) {
            switch (ballPose) {
                case GPP:
                    return red ? AutoPoses.RED_POST_INTAKE_GPP : AutoPoses.BLUE_POST_INTAKE_GPP;
                case PGP:
                    return red ? AutoPoses.RED_POST_INTAKE_PGP : AutoPoses.BLUE_POST_INTAKE_PGP;
                case PPG:
                    return red ? AutoPoses.RED_POST_INTAKE_PPG : AutoPoses.BLUE_POST_INTAKE_PPG;
                case HUMAN_PLAYER1:
                    return red ? AutoPoses.RED_POST_INTAKE_HUMAN : AutoPoses.BLUE_POST_INTAKE_HUMAN;
                case HUMAN_PLAYER2:
                    return red ? AutoPoses.RED_POST_INTAKE_HUMAN2 : AutoPoses.BLUE_POST_INTAKE_HUMAN2;
            }

            throw new IllegalArgumentException("ballPose isn't GPP, PGP, PPG, or human");
        }

        public static PathChain startToShootPath(Drivetrain drivetrain, boolean close, boolean isRed) {
            // this is an inline if statement that will determine the correct starting and ending position,
            // depending on whether it is close or far, and red or blue;
            Pose start = isRed ? (close ? AutoPoses.RED_STARTING_CLOSE : AutoPoses.RED_STARTING_FAR) : (close ? AutoPoses.BLUE_STARTING_CLOSE : AutoPoses.BLUE_STARTING_FAR);
            Pose end = isRed ? (close ? AutoPoses.RED_SHOOT_CLOSE : AutoPoses.RED_SHOOT_FAR) : (close ? AutoPoses.BLUE_SHOOT_CLOSE : AutoPoses.BLUE_SHOOT_FAR);

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, end))
                    .setLinearHeadingInterpolation(start.getHeading(), end.getHeading())
                    .build();
        }

        public static PathChain intakeBallsPathBounce(Drivetrain drivetrain, BallPose ballPose, boolean close, boolean isRed, Pose drift) {
            Pose start = isRed ? (close ? AutoPoses.RED_SHOOT_CLOSE : AutoPoses.RED_SHOOT_FAR) : (close ? AutoPoses.BLUE_SHOOT_CLOSE : AutoPoses.BLUE_SHOOT_FAR);

            Pose ballPre = getPreBallPose(ballPose, isRed);
            Pose ballPost = getPostBallPose(ballPose, isRed);

            ballPre = ballPre.plus(drift);
            ballPost = ballPost.plus(drift);

            final double NUDGE_AMOUNT_OUT = 1.5;
            Pose nudgePose = new Pose(ballPost.getX() - (Math.signum(ballPost.getX() - 72)*NUDGE_AMOUNT_OUT), ballPost.getY(), ballPost.getHeading());

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, ballPre))
                    .setLinearHeadingInterpolation(start.getHeading(), ballPre.getHeading())

                    .addPath(new BezierLine(ballPre, ballPost))
                    .setLinearHeadingInterpolation(ballPre.getHeading(), ballPost.getHeading())
                    .setBrakingStart(10)

                    // nudge routine to help with intaking (for now)
                    .addPath(new BezierLine(ballPost, nudgePose))
                    .setLinearHeadingInterpolation(ballPost.getHeading(), nudgePose.getHeading())
                    .setBrakingStart(10)


                    .addPath(new BezierLine(nudgePose, ballPost))
                    .setLinearHeadingInterpolation(nudgePose.getHeading(), ballPost.getHeading())


                    .build();
        }

        public static PathChain intakeBallsPathUnbounce(Drivetrain drivetrain, BallPose ballPose, boolean close, boolean isRed, Pose drift) {
            Pose start = isRed ? (close ? AutoPoses.RED_SHOOT_CLOSE : AutoPoses.RED_SHOOT_FAR) : (close ? AutoPoses.BLUE_SHOOT_CLOSE : AutoPoses.BLUE_SHOOT_FAR);

            Pose ballPre = getPreBallPose(ballPose, isRed);
            Pose ballPost = getPostBallPose(ballPose, isRed);

            ballPre = ballPre.plus(drift);
            ballPost = ballPost.plus(drift);

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, ballPre))
                    .setLinearHeadingInterpolation(start.getHeading(), ballPre.getHeading())

                    .addPath(new BezierLine(ballPre, ballPost))
                    .setLinearHeadingInterpolation(ballPre.getHeading(), ballPost.getHeading())
                    .setTValueConstraint(0.97)
                    .setBrakingStart(10)

                    .build();
        }

        public static PathChain knockGateFromFirstPath(Drivetrain drivetrain, boolean isRed) {
            Pose start = (isRed ? AutoPoses.RED_POST_INTAKE_PPG : AutoPoses.BLUE_POST_INTAKE_PPG);

            Pose lineUp = isRed ? AutoPoses.RED_LINEUP_1 : AutoPoses.BLUE_LINEUP_1;
            Pose end = isRed ? AutoPoses.RED_HIT_GATE_1 : AutoPoses.BLUE_HIT_GATE_1;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, lineUp))
                    .setLinearHeadingInterpolation(start.getHeading(), lineUp.getHeading())
                    .addPath(new BezierLine(lineUp, end))
                    .setLinearHeadingInterpolation(lineUp.getHeading(), end.getHeading())
                    .build();
        }

        public static PathChain knockGateFromSecondPath(Drivetrain drivetrain, boolean isRed) {
            Pose start = (isRed ? AutoPoses.RED_POST_INTAKE_PGP : AutoPoses.BLUE_POST_INTAKE_PGP);

            Pose lineUp = isRed ? AutoPoses.RED_LINEUP_2 : AutoPoses.BLUE_LINEUP_2;
            Pose end = isRed ? AutoPoses.RED_HIT_GATE_2 : AutoPoses.BLUE_HIT_GATE_2;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, lineUp))
                    .setLinearHeadingInterpolation(start.getHeading(), lineUp.getHeading())
                    .addPath(new BezierLine(lineUp, end))
                    .setLinearHeadingInterpolation(lineUp.getHeading(), end.getHeading())
                    .build();
        }

        public static PathChain intakeToShootPath(Drivetrain drivetrain, BallPose ballPose, boolean close, boolean isRed, GatePose gatePose, Pose drift) {
            Pose start = null;
            if (gatePose == GatePose.NONE) {
                start = getPostBallPose(ballPose, isRed);
            }
            else if (gatePose == GatePose.FIRST_LINE) {
                start = (isRed ? AutoPoses.RED_HIT_GATE_1 : AutoPoses.BLUE_HIT_GATE_1);
            }
            else if (gatePose == GatePose.SECOND_LINE) {
                start = (isRed ? AutoPoses.RED_HIT_GATE_2 : AutoPoses.BLUE_HIT_GATE_2);
            }

            Pose end = isRed ? (close ? AutoPoses.RED_SHOOT_CLOSE : AutoPoses.RED_SHOOT_FAR) : (close ? AutoPoses.BLUE_SHOOT_CLOSE : AutoPoses.BLUE_SHOOT_FAR);
            end = end.plus(drift);

            if (start == null)
                throw(new IllegalArgumentException("gatePose wasn't NONE, FIRST_LINE, or SECOND_LINE"));

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, end))
                    .setLinearHeadingInterpolation(start.getHeading(), end.getHeading())
                    .build();
        }

        public static PathChain leavePath(Drivetrain drivetrain, boolean close, boolean isRed) {
            Pose start = isRed ? (close ? AutoPoses.RED_SHOOT_CLOSE : AutoPoses.RED_SHOOT_FAR) : (close ? AutoPoses.BLUE_SHOOT_CLOSE : AutoPoses.BLUE_SHOOT_FAR);
            Pose end = isRed ? (close ? AutoPoses.RED_LEAVE_CLOSE : AutoPoses.RED_LEAVE_FAR) : (close ? AutoPoses.BLUE_LEAVE_CLOSE : AutoPoses.BLUE_LEAVE_FAR);

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, end))
                    .setLinearHeadingInterpolation(start.getHeading(), end.getHeading())
                    .build();
        }
    }
}
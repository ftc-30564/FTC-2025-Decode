package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.Poses;
import org.firstinspires.ftc.teamcode.util.VelocityPair;

// The @Config allows us to configure these numbers in FtcDashboard
public class RobotConstants {
    @Config
    public static class Drive {
        public static boolean IS_DEBUGGING = false;

        public static Pose LAST_REMEMBERED_POSE = new Pose(0, 0, 0);
        public static boolean HAS_POSE = false;

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

        // good but loop time: 1.6, 0.15
        public static double DRIVE_SNAP_TO_ANGLE_P = 1.55;
        public static double DRIVE_SNAP_TO_ANGLE_D = 0.1;

        // good but loop time: 1.6, 0.15
        public static double DRIVE_SOTM_P = 1.8;
        public static double DRIVE_SOTM_D = 0.1;

        public static double IS_ALIGNED_MARGIN = 5;
        public static double IS_ALIGNED_VELOCITY_RADIANS = 2;

        public static double BLUE_GOAL_POSE_X = 18;
        public static double BLUE_GOAL_POSE_Y = 138;

        public static double RED_GOAL_POSE_X = 140;
        public static double RED_GOAL_POSE_Y = 131;

        public static double FIELD_LEN = 145;
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

        public static double VELOCITY_BOTTOM_P_STANDBY = 0.033;
        public static double VELOCITY_TOP_P_STANDBY = 0.033;

        public static double VELOCITY_BOTTOM_P_SHOOT = 0.033;
        public static double VELOCITY_TOP_P_SHOOT = 0.033;
        public static boolean IS_P_ENABLED = true;

        public static final double VELOCITY_DEADBAND = 10;

        public static final VelocityPair CLOSE_VELOCITY = new VelocityPair(120, 120);
        public static final VelocityPair FAR_VELOCITY = new VelocityPair(130, 130);
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
        public static Pose RED_STARTING_CLOSE = new Pose(123.64, 123.12, Math.toRadians(218));
        public static Pose RED_STARTING_FAR = new Pose(87.01, 10.5, Math.toRadians(-91.05));
        public static Pose RED_SHOOT_CLOSE       = new Pose(87, 76.7, Math.toRadians(227));
        public static Pose RED_SHOOT_FAR         = new Pose(87.24, 14.45, Math.toRadians(-109));

        public static Pose RED_PRE_INTAKE_PPG    = new Pose(90,    83.9, Math.toRadians(1));
        public static Pose RED_POST_INTAKE_PPG   = new Pose(127, 83.9, Math.toRadians(1));

        public static Pose RED_PRE_INTAKE_PGP    = new Pose(93,    57.5, Math.toRadians(-2));
        public static Pose RED_POST_INTAKE_PGP   = new Pose(133.5, 57.5, Math.toRadians(-2));

        public static Pose RED_PRE_INTAKE_GPP    = new Pose(100,    36, Math.toRadians(-5));
        public static Pose RED_POST_INTAKE_GPP   = new Pose(132, 37.4, Math.toRadians(-5));

        public static Pose RED_GATEMPTY = new Pose(126.4, 62.5, Math.toRadians(0));//new Pose(11.61, 55.87, Math.toRadians(-89.57))

        public static Pose RED_HUMAN_PLAYER_1 = new Pose(110.71, 15.57, Math.toRadians(-19.57));
        public static Pose RED_HUMAN_PLAYER_2 = new Pose(131.77, 14.30, Math.toRadians(-27.08));
        public static Pose RED_HUMAN_PLAYER_3 = new Pose(130.01, 11.02, Math.toRadians(-23.80));
        public static Pose RED_HUMAN_PLAYER_4 = new Pose(100.38, 15.92, Math.toRadians(2.18));
        public static Pose RED_HUMAN_PLAYER_5 = new Pose(133.39, 26.20, Math.toRadians(13.56));

        public static Pose RED_GATETAKE = new Pose(131.8+0.5, 59, Math.toRadians(34.2));

        public static Pose RED_LEAVE_CLOSE       = new Pose(92, 71.7, Math.toRadians(227));
        public static Pose RED_LEAVE_FAR         = new Pose(91,    26.5, Math.toRadians(270));

        public static Pose BLUE_STARTING_CLOSE   = new Pose(23, 123, Math.toRadians(-39.37));
        public static Pose BLUE_STARTING_FAR     = Poses.mirrorPose(RED_STARTING_FAR);

        public static Pose BLUE_SHOOT_CLOSE      = Poses.mirrorPose(RED_SHOOT_CLOSE).plus(new Pose(0, 0, Math.toRadians(-8.5)));
        public static Pose BLUE_SHOOT_FAR        = Poses.mirrorPose(RED_SHOOT_FAR).plus(new Pose(0, 0, Math.toRadians(-6)));

        public static Pose BLUE_PRE_INTAKE_PPG   = Poses.mirrorPose(RED_PRE_INTAKE_PPG);
        public static Pose BLUE_POST_INTAKE_PPG  = Poses.mirrorPose(RED_POST_INTAKE_PPG);

        public static Pose BLUE_PRE_INTAKE_PGP   = Poses.mirrorPose(RED_PRE_INTAKE_PGP);
        public static Pose BLUE_POST_INTAKE_PGP  = Poses.mirrorPose(RED_POST_INTAKE_PGP);

        public static Pose BLUE_PRE_INTAKE_GPP   = Poses.mirrorPose(RED_PRE_INTAKE_GPP);
        public static Pose BLUE_POST_INTAKE_GPP  = Poses.mirrorPose(RED_POST_INTAKE_GPP);

//        public static Pose BLUE_PRE_INTAKE_HUMAN  = new Pose(10.5, 18.5, Math.toRadians(210));
//        public static Pose BLUE_POST_INTAKE_HUMAN = new Pose(10, 11, Math.toRadians(210));
//
//        public static Pose BLUE_PRE_INTAKE_HUMAN2 = new Pose(40, 10.4, Math.toRadians(180));
//        public static Pose BLUE_POST_INTAKE_HUMAN2 = new Pose(6, 13, Math.toRadians(180));
//
//        public static Pose BLUE_LINEUP_1         = new Pose(26, 78, Math.toRadians(270));
//        public static Pose BLUE_LINEUP_2         = new Pose(26, 67, Math.toRadians(270));

        public static Pose BLUE_GATETAKE = new Pose(13.7, 60.59, Math.toRadians(142.9));

        public static Pose BLUE_GATEMPTY = Poses.mirrorPose(RED_GATEMPTY);

        public static Pose BLUE_HUMAN_PLAYER_1 = Poses.mirrorPose(RED_HUMAN_PLAYER_1);
        public static Pose BLUE_HUMAN_PLAYER_2 = Poses.mirrorPose(RED_HUMAN_PLAYER_2);
        public static Pose BLUE_HUMAN_PLAYER_3 = Poses.mirrorPose(RED_HUMAN_PLAYER_3);
        public static Pose BLUE_HUMAN_PLAYER_4 = Poses.mirrorPose(RED_HUMAN_PLAYER_4);
        public static Pose BLUE_HUMAN_PLAYER_5 = Poses.mirrorPose(RED_HUMAN_PLAYER_5);

        public static Pose BLUE_LEAVE_CLOSE = Poses.mirrorPose(RED_LEAVE_CLOSE);
        public static Pose BLUE_LEAVE_FAR   = Poses.mirrorPose(RED_LEAVE_FAR);
    }
}
package org.firstinspires.ftc.teamcode;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.Poses;

import static org.firstinspires.ftc.teamcode.RobotConstants.AutoPoses.*;

public class Paths {
    public static class Close {
        public static PathChain startToShoot(Drivetrain drivetrain, boolean isRed) {

            Pose start = isRed ? RED_STARTING_CLOSE : BLUE_STARTING_CLOSE;
            Pose end = isRed ? RED_SHOOT_CLOSE : BLUE_SHOOT_CLOSE;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, end))
                    .setLinearHeadingInterpolation(start.getHeading(), end.getHeading())
                    .build();
        }

        // TODO
        public static PathChain startToIntakePGP(Drivetrain drivetrain, boolean isRed) {

            Pose start = isRed ? RED_STARTING_CLOSE : BLUE_STARTING_CLOSE;
            Pose end = isRed ? RED_SHOOT_CLOSE : BLUE_SHOOT_CLOSE;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, end))
                    .setLinearHeadingInterpolation(start.getHeading(), end.getHeading())
                    .build();
        }

        // MARK: PPG
        public static PathChain intakePPG(Drivetrain drivetrain, boolean isRed) {
            Pose start = isRed ? RED_SHOOT_CLOSE : BLUE_SHOOT_CLOSE;

            Pose pre = isRed ? RED_PRE_INTAKE_PPG : BLUE_PRE_INTAKE_PPG;
            Pose post = isRed ? RED_POST_INTAKE_PPG : BLUE_POST_INTAKE_PPG;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, pre))
                    .setLinearHeadingInterpolation(start.getHeading(), pre.getHeading())

                    .addPath(new BezierLine(pre, post))
                    .setLinearHeadingInterpolation(pre.getHeading(), post.getHeading())

                    //.setTValueConstraint(0.97)

                    .build();
        }

        public static PathChain hitGate(Drivetrain drivetrain, boolean isRed, Pose from) {
            Pose end = isRed ? RED_GATEMPTY : BLUE_GATEMPTY;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(from, end))
                    .setLinearHeadingInterpolation(from.getHeading(), end.getHeading())

                    .build();
        }

        // MARK PGP
        public static PathChain intakePGP(Drivetrain drivetrain, boolean isRed) {
            Pose start = isRed ? RED_SHOOT_CLOSE : BLUE_SHOOT_CLOSE;

            Pose pre = isRed ? RED_PRE_INTAKE_PGP : BLUE_PRE_INTAKE_PGP;
            Pose post = isRed ? RED_POST_INTAKE_PGP : BLUE_POST_INTAKE_PGP;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, pre))
                    .setLinearHeadingInterpolation(start.getHeading(), pre.getHeading())

                    .addPath(new BezierLine(pre, post))
                    .setLinearHeadingInterpolation(pre.getHeading(), post.getHeading())

                    //.setTValueConstraint(0.97)

                    .build();
        }

        // MARK: GPP
        public static PathChain intakeGPP(Drivetrain drivetrain, boolean isRed) {
            Pose start = isRed ? RED_SHOOT_CLOSE : BLUE_SHOOT_CLOSE;

            Pose pre = isRed ? RED_PRE_INTAKE_GPP : BLUE_PRE_INTAKE_GPP;
            Pose post = isRed ? RED_POST_INTAKE_GPP : BLUE_POST_INTAKE_GPP;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, pre))
                    .setLinearHeadingInterpolation(start.getHeading(), pre.getHeading())

                    .addPath(new BezierLine(pre, post))
                    .setLinearHeadingInterpolation(pre.getHeading(), post.getHeading())

//                    .setTValueConstraint(0.97)

                    .build();
        }

        public static PathChain intakeGate(Drivetrain drivetrain, boolean isRed) {
            Pose start = isRed ? RED_SHOOT_CLOSE : BLUE_SHOOT_CLOSE;
            Pose end = isRed ? RED_GATETAKE : BLUE_GATETAKE;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, end))
                    .setLinearHeadingInterpolation(start.getHeading(), end.getHeading())

                    .setBrakingStart(15)
                    .build();
        }

        public static PathChain shoot(Drivetrain drivetrain, Pose from, boolean isRed) {
            Pose end = isRed ? RED_SHOOT_CLOSE : BLUE_SHOOT_CLOSE;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(from, end))
                    .setLinearHeadingInterpolation(from.getHeading(), end.getHeading())

                    //.setTValueConstraint(1.0)
                    .build();
        }

        public static PathChain leave(Drivetrain drivetrain, boolean isRed) {
            Pose start = isRed ? RED_SHOOT_CLOSE : BLUE_SHOOT_CLOSE;
            Pose end = isRed ? RED_LEAVE_CLOSE : BLUE_LEAVE_CLOSE;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, end))
                    .setLinearHeadingInterpolation(start.getHeading(), end.getHeading())
                    .build();
        }
    }

    public static class Far {
        public static PathChain startToShoot(Drivetrain drivetrain, boolean isRed) {

            Pose start = isRed ? RED_STARTING_FAR : BLUE_STARTING_FAR;
            Pose end = isRed ? RED_SHOOT_FAR : BLUE_SHOOT_FAR;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, end))
                    .setLinearHeadingInterpolation(start.getHeading(), end.getHeading())
                    .build();
        }

        public static PathChain intakeHumanPlayer(Drivetrain drivetrain, boolean isRed) {
            Pose start = isRed ? RED_SHOOT_FAR : BLUE_SHOOT_FAR;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, RED_HUMAN_PLAYER_1))
                    .setLinearHeadingInterpolation(start.getHeading(), RED_HUMAN_PLAYER_1.getHeading())

                    .addPath(new BezierLine(RED_HUMAN_PLAYER_1, RED_HUMAN_PLAYER_2))
                    .setLinearHeadingInterpolation(RED_HUMAN_PLAYER_1.getHeading(), RED_HUMAN_PLAYER_2.getHeading())

                    .addPath(new BezierLine(RED_HUMAN_PLAYER_2, RED_HUMAN_PLAYER_3))
                    .setLinearHeadingInterpolation(RED_HUMAN_PLAYER_2.getHeading(), RED_HUMAN_PLAYER_3.getHeading())

                    .build();
        }

        public static PathChain intakeHumanPlayerOffsetABit(Drivetrain drivetrain, boolean isRed) {
            Pose start = isRed ? RED_SHOOT_FAR : BLUE_SHOOT_FAR;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, RED_HUMAN_PLAYER_4))
                    .setLinearHeadingInterpolation(start.getHeading(), RED_HUMAN_PLAYER_4.getHeading())

                    .addPath(new BezierLine(RED_HUMAN_PLAYER_4, RED_HUMAN_PLAYER_5))
                    .setLinearHeadingInterpolation(RED_HUMAN_PLAYER_4.getHeading(), RED_HUMAN_PLAYER_5.getHeading())

                    .build();
        }

        // MARK: GPP
        public static PathChain intakeGPP(Drivetrain drivetrain, boolean isRed) {
            Pose start = isRed ? RED_SHOOT_FAR : BLUE_SHOOT_FAR;

            Pose pre = isRed ? RED_PRE_INTAKE_GPP : BLUE_PRE_INTAKE_GPP;
            Pose post = isRed ? RED_POST_INTAKE_GPP : BLUE_POST_INTAKE_GPP;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(start, pre))
                    .setLinearHeadingInterpolation(start.getHeading(), pre.getHeading())

                    .addPath(new BezierLine(pre, post))
                    .setLinearHeadingInterpolation(pre.getHeading(), post.getHeading())

//                    .setTValueConstraint(0.97)

                    .build();
        }

        public static PathChain shoot(Drivetrain drivetrain, Pose from, boolean isRed) {
            Pose end = isRed ? RED_SHOOT_FAR : BLUE_SHOOT_FAR;

            return drivetrain.pathBuilder()
                    .addPath(new BezierLine(from, end))
                    .setLinearHeadingInterpolation(from.getHeading(), end.getHeading())

                    //.setTValueConstraint(1.0)
                    .build();
        }
    }
}

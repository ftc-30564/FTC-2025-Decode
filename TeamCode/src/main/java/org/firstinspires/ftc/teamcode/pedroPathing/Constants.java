package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.ThreeWheelConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotConstants;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants();

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public static MecanumConstants driveConstants = new MecanumConstants()
            .leftFrontMotorName(RobotConstants.Drivetrain.FRONT_LEFT_MOTOR_NAME)
            .leftRearMotorName(RobotConstants.Drivetrain.BACK_LEFT_MOTOR_NAME)
            .rightFrontMotorName(RobotConstants.Drivetrain.FRONT_RIGHT_MOTOR_NAME)
            .rightRearMotorName(RobotConstants.Drivetrain.BACK_RIGHT_MOTOR_NAME)
            .leftFrontMotorDirection(RobotConstants.Drivetrain.FRONT_LEFT_MOTOR_DIRECTION)
            .leftRearMotorDirection(RobotConstants.Drivetrain.BACK_LEFT_MOTOR_DIRECTION)
            .rightFrontMotorDirection(RobotConstants.Drivetrain.FRONT_RIGHT_MOTOR_DIRECTION)
            .rightRearMotorDirection(RobotConstants.Drivetrain.BACK_RIGHT_MOTOR_DIRECTION)
            .xVelocity(78.261926752421046666666666666667)
            .yVelocity(61.494551922189565);

    public static ThreeWheelConstants localizerConstants = new ThreeWheelConstants()
            .forwardTicksToInches(.001989436789)   // tune this
            .strafeTicksToInches(.001989436789)   // ditto
            .turnTicksToInches(.001989436789)   // ditto
            .leftPodY(RobotConstants.Drivetrain.DEAD_WHEEL_LEFT_OFFSET)    // ditto
            .rightPodY(RobotConstants.Drivetrain.DEAD_WHEEL_RIGHT_OFFSET)    // ditto
            .strafePodX(RobotConstants.Drivetrain.DEAD_WHEEL_PERP_OFFSET)    // ditto
            .leftEncoder_HardwareMapName(RobotConstants.Drivetrain.DEAD_WHEEL_LEFT_NAME)
            .rightEncoder_HardwareMapName(RobotConstants.Drivetrain.DEAD_WHEEL_RIGHT_NAME)
            .strafeEncoder_HardwareMapName(RobotConstants.Drivetrain.DEAD_WHEEL_PERP_NAME)
            .leftEncoderDirection(RobotConstants.Drivetrain.DEAD_WHEEL_LEFT_DIRECTION)
            .rightEncoderDirection(RobotConstants.Drivetrain.DEAD_WHEEL_RIGHT_DIRECTION)
            .strafeEncoderDirection(RobotConstants.Drivetrain.DEAD_WHEEL_PERP_DIRECTION);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .threeWheelLocalizer(localizerConstants)
                .mecanumDrivetrain(driveConstants)
                .pathConstraints(pathConstraints)
                .build();
    }


}

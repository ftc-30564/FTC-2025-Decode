package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.ftc.localization.constants.ThreeWheelConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.RobotConstants;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(12.7006)
            .forwardZeroPowerAcceleration(-32)
            .lateralZeroPowerAcceleration(-56)
            .centripetalScaling(0.006)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.25, 0, 0.03, 0))
            .headingPIDFCoefficients(new PIDFCoefficients(2.0, 0, 0.1, 0.01))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.05, 0, 0.000001, 0.6, 0)) //p// 0.01
            .secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(0.03, 0, 0.000003, 0.6, 0.01))
            .useSecondaryDrivePIDF(true);



    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .leftFrontMotorName(RobotConstants.Drive.FRONT_LEFT_MOTOR_NAME)
            .leftRearMotorName(RobotConstants.Drive.BACK_LEFT_MOTOR_NAME)
            .rightFrontMotorName(RobotConstants.Drive.FRONT_RIGHT_MOTOR_NAME)
            .rightRearMotorName(RobotConstants.Drive.BACK_RIGHT_MOTOR_NAME)
            .leftFrontMotorDirection(RobotConstants.Drive.FRONT_LEFT_MOTOR_DIRECTION)
            .leftRearMotorDirection(RobotConstants.Drive.BACK_LEFT_MOTOR_DIRECTION)
            .rightFrontMotorDirection(RobotConstants.Drive.FRONT_RIGHT_MOTOR_DIRECTION)
            .rightRearMotorDirection(RobotConstants.Drive.BACK_RIGHT_MOTOR_DIRECTION)
            .xVelocity(76)
            .yVelocity(60.3)
            .useBrakeModeInTeleOp(true);

    public static PinpointConstants pinpointConstants = new PinpointConstants()
            .forwardPodY(RobotConstants.Drive.DEAD_WHEEL_FORWARD_OFFSET)
            .strafePodX(RobotConstants.Drive.DEAD_WHEEL_PERP_OFFSET)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pinpointLocalizer(pinpointConstants)
                .mecanumDrivetrain(driveConstants)
                .pathConstraints(pathConstraints)
                .build();
    }


}

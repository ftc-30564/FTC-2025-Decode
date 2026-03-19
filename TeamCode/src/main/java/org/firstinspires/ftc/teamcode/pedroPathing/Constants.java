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
            .mass(5)
            .forwardZeroPowerAcceleration(-37.1841065)
            .lateralZeroPowerAcceleration(-74.017442)
            .centripetalScaling(0.0012)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.2, 0, 0, 0))
            .headingPIDFCoefficients(new PIDFCoefficients(1, 0, 0, 0.01))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(.015, 0, 0.00001, 0.6, 0)); //p// 0.01


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
            .xVelocity((63.69953 + 63.160463)/2)
            .yVelocity((52.869557 + 55.94934)/2)
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

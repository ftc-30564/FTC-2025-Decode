package org.firstinspires.ftc.teamcode.opmode.teleop.debug;

import com.pedropathing.ftc.localization.Encoder;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.RobotConstants;

@TeleOp
public class DrivetrainDebug extends LinearOpMode {

    @Override
    public void runOpMode() {
        DcMotorEx frontLeftMotor =  hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.FRONT_LEFT_MOTOR_NAME);
        DcMotorEx frontRightMotor = hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.FRONT_RIGHT_MOTOR_NAME);
        DcMotorEx backLeftMotor =   hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.BACK_LEFT_MOTOR_NAME);
        DcMotorEx backRightMotor =  hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.BACK_RIGHT_MOTOR_NAME);

        DcMotorEx deadWheelLeft =  hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.DEAD_WHEEL_LEFT_NAME);
        DcMotorEx deadWheelRight = hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.DEAD_WHEEL_RIGHT_NAME);
        DcMotorEx deadWheelPerp =  hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.DEAD_WHEEL_PERP_NAME);

        frontLeftMotor.setDirection(RobotConstants.Drivetrain.FRONT_LEFT_MOTOR_DIRECTION);
        frontRightMotor.setDirection(RobotConstants.Drivetrain.FRONT_RIGHT_MOTOR_DIRECTION);
        backLeftMotor.setDirection(RobotConstants.Drivetrain.BACK_LEFT_MOTOR_DIRECTION);
        backRightMotor.setDirection(RobotConstants.Drivetrain.BACK_RIGHT_MOTOR_DIRECTION);

        // convert Encoder direction to DcMotorSimple
        deadWheelLeft.setDirection(RobotConstants.Drivetrain.DEAD_WHEEL_LEFT_DIRECTION == 1.0 ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);
        deadWheelRight.setDirection(RobotConstants.Drivetrain.DEAD_WHEEL_RIGHT_DIRECTION == 1.0 ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);
        deadWheelPerp.setDirection(RobotConstants.Drivetrain.DEAD_WHEEL_PERP_DIRECTION == 1.0 ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("DPAD Up", "Front Left Motor");
            telemetry.addData("DPAD Left", "Back Left Motor");
            telemetry.addData("DPAD Right", "Front Right Motor");
            telemetry.addData("DPAD Down", "Back Right Motor");

            telemetry.addData("Odometry Left (+ when forward)", deadWheelLeft.getCurrentPosition());
            telemetry.addData("Odometry Right (+ when forward)", deadWheelRight.getCurrentPosition());
            telemetry.addData("Odometry Perp (+ when left)", deadWheelPerp.getCurrentPosition());

            // Front Left Motor
            if (gamepad1.dpad_up) {
                frontLeftMotor.setPower(0.25);
            } else {
                frontLeftMotor.setPower(0);
            }

            // Back Left Motor
            if (gamepad1.dpad_left) {
                backLeftMotor.setPower(0.25);
            } else {
                backLeftMotor.setPower(0);
            }

            // Front Right Motor
            if (gamepad1.dpad_right) {
                frontRightMotor.setPower(0.25);
            } else {
                frontRightMotor.setPower(0);
            }

            // Back Right Motor
            if (gamepad1.dpad_down) {
                backRightMotor.setPower(0.25);
            } else {
                backRightMotor.setPower(0);
            }

            telemetry.update();
        }
    }
}
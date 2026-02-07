package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.InterpolationPoints;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.IndicatorRGB;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Interpolator;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.VelocityPair;

@TeleOp(group = "Main")
public class BlueTeleop extends LinearOpMode {
    private Drivetrain drivetrain;
    private Intake intake;
    private Shooter shooter;
    private Limelight limelight;
    private Interpolator interpolator;

    private IndicatorRGB indicator;

    private final boolean IS_RED = false;
    private final boolean USING_INTERPOLATION = true;

    public enum ShootingPosition {
        CLOSE(RobotConstants.Shooter.CLOSE_VELOCITY),
        FAR(RobotConstants.Shooter.FAR_VELOCITY);

        VelocityPair vel;
        ShootingPosition(VelocityPair vel) {
            this.vel = vel;
        }
    }

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap, false);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        limelight = new Limelight(hardwareMap);
        interpolator = new Interpolator(InterpolationPoints.points_1_4);
        indicator = new IndicatorRGB(hardwareMap);

        ShootingPosition shootingPosition = ShootingPosition.CLOSE;

        if (IS_RED) {
            limelight.setRedGoalPipeline();
        }
        else {
            limelight.setBlueGoalPipeline();
        }


        if (IS_RED) {
            drivetrain.setStartingPose(RobotConstants.Auto.LAST_REMEMBERED_POSE);
        }
        else {
            if (RobotConstants.Auto.LAST_REMEMBERED_POSE.getHeading() == 0) {
                RobotConstants.Auto.LAST_REMEMBERED_POSE = RobotConstants.Auto.LAST_REMEMBERED_POSE.setHeading(Math.toRadians(180));
            }
            drivetrain.setStartingPose(RobotConstants.Auto.LAST_REMEMBERED_POSE);
        }


        boolean zeroButton;
        boolean intakeButton;
        boolean barfButton;
        boolean chargeButton;
        boolean shootButton;
        boolean isShootingWhileMoving = false;

        boolean isCharged = false;
        double lowestRpm = 500;

        double turnAmt = 0;

        waitForStart();

        limelight.start();
        drivetrain.startTeleopDrive();
        drivetrain.resetImu();

        while (opModeIsActive()) {
            intakeButton = (gamepad1.right_bumper || gamepad2.a);
            barfButton = gamepad1.b;
            chargeButton = gamepad2.left_bumper;
            shootButton = gamepad2.right_bumper;
            zeroButton = gamepad1.back;

            isShootingWhileMoving = false;//Math.sqrt(Math.pow(gamepad1.left_stick_x, 2)) + Math.pow(gamepad1.left_stick_y, 2) > 0.5;

            drivetrain.update();

            if (gamepad1.left_bumper) {
                if (isShootingWhileMoving) {
                    drivetrain.setGoalCentricDriveV2(
                            -gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                            -gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                            -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT,
                            telemetry);
                }
                else {
                    drivetrain.setTeleopDrive(
                            -gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                            -gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                            -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT,
                            limelight.getYawTarget()
                    );
                }
            }
            else {
                drivetrain.setTeleopDrive(
                        -gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                        -gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                        -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT,
                        false);

                // Try to update position of robot
//                Pose estPose = limelight.getPoseEstimate(Math.toDegrees(drivetrain.getImuAngleRadians()) + 90);
//                if (estPose != null) {
//                    drivetrain.setPose(estPose);
//
//                    telemetry.addData("LL POSE X", estPose.getX());
//                    telemetry.addData("LL POSE Y", estPose.getY());
//                }
            }


            if (zeroButton) {
                if (IS_RED) {
                    drivetrain.zeroHeading();
                }
                else {
                    drivetrain.oneEightyHeading();
                }
            }

            if (intakeButton && barfButton) {
                intake.spit();
            }
            else if (intakeButton || shootButton){
                intake.run();
            }
            else if (barfButton) {
                intake.barf();
            }
            else {
                intake.stop();
            }

            if (gamepad1.dpad_down)
                shootingPosition = ShootingPosition.FAR;
            if (gamepad1.dpad_up) {
                shootingPosition = ShootingPosition.CLOSE;
            }

            if (chargeButton) {
                if (USING_INTERPOLATION) {
                    double vel = interpolator.getVelocity(limelight.getDistanceTarget(IS_RED, telemetry));
                    shooter.setToVelocityPair(new VelocityPair(vel, vel));

                    if (shooter.isAtVelocityPair(new VelocityPair(vel-10, vel-10))) {
                        isCharged = true;
                    }
                }
                else {
                    shooter.setToVelocityPair(shootingPosition.vel);
                }
            }
            else {
                isCharged = false;
                lowestRpm = 500;
                shooter.setTopShooterToVelocity(0);
                shooter.setBottomShooterToVelocity(0);
            }

            if (intakeButton) {
                shooter.runBackPusher();
            }
            else if (shootButton) {
                shooter.runPusher();
            }
            else if (barfButton) {
                shooter.barfPusher();
            }
            else {
                shooter.stopPusher();
            }

            if (shootButton){
                drivetrain.setMaxPower(0.5);
            }
            else {
                drivetrain.setMaxPower(1);
            }

            if (isShootingWhileMoving)
                indicator.orange();
            else if (limelight.isAlignedWithGoal())
                indicator.green();
            else
                indicator.blue();

            telemetry.addLine("SHOOTER");
            telemetry.addData("LOWEST RPM", lowestRpm);
            telemetry.addData("Bottom shooter vel", shooter.getVelocityBottom());
            telemetry.addData("Top shooter vel", shooter.getVelocityTop());
            telemetry.addLine("DRIVETRAIN");
            telemetry.addData("X", drivetrain.getPose().getX());
            telemetry.addData("Y", drivetrain.getPose().getY());
            telemetry.addData("Heading", Math.toDegrees(drivetrain.getPose().getHeading()));
            telemetry.addData("Robot IMU Heading", drivetrain.getImuAngleRadians());
            telemetry.addLine("LIMELIGHT");
            telemetry.addData("Turn Amount", turnAmt);
            telemetry.addData("Is aligned with goal", limelight.isAlignedWithGoal());
            telemetry.addData("Distance to target", limelight.getDistanceTarget(IS_RED, telemetry));

            telemetry.update();

        }

        // update last remembered pose
        RobotConstants.Auto.LAST_REMEMBERED_POSE = drivetrain.getPose();
    }
}
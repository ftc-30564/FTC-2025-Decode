package org.firstinspires.ftc.teamcode.opmode.teleop.debug;

import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.CoordinateSystem;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.InterpolationPoints;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.opmode.teleop.BlueTeleop;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.IndicatorRGB;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Interpolator;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.VelocityPair;

@TeleOp(group = "Tests")
public class AlignToTargetV2 extends LinearOpMode {
    public Drivetrain drivetrain;
    public Limelight limelight;
    private Intake intake;
    private Shooter shooter;
    private Interpolator interpolator;

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        limelight = new Limelight(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        interpolator = new Interpolator(InterpolationPoints.points_11_30);

        limelight.setObeliskPipeline();
        drivetrain.setStartingPose(new Pose(0, 0, 0));

        boolean zeroButton;
        boolean intakeButton;
        boolean barfButton;
        boolean chargeButton;
        boolean shootButton;

        waitForStart();

        //drivetrain.resetImu();
        drivetrain.startTeleopDrive();
        limelight.start();

        while (opModeIsActive()) {
            intakeButton = (gamepad1.right_bumper || gamepad2.a);
            barfButton = gamepad1.b;
            chargeButton = gamepad2.left_bumper;
            shootButton = gamepad2.right_bumper;
            zeroButton = gamepad1.back;

            drivetrain.update();

            if (gamepad1.left_bumper) {
                if (Math.sqrt(Math.pow(gamepad1.left_stick_x, 2)) + Math.pow(gamepad1.left_stick_y, 2) > 0.5) {
                }
                else {
                    drivetrain.setTeleopDrive(
                            -gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                            -gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                            -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT,
                            false
                            //limelight.getYawTarget()
                    );

                    Pose estPose = limelight.getPoseEstimate(Math.toDegrees(drivetrain.getPose().getHeading()) + 90);
                    if (estPose != null) {
                        drivetrain.setPose(estPose);

                        telemetry.addData("LL POSE X", estPose.getX());
                        telemetry.addData("LL POSE Y", estPose.getY());
                    }
                }
            }
            else {
                drivetrain.setTeleopDrive(
                        -gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT,
                        -gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT,
                        -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT,
                        false);
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

            if (chargeButton) {
                if (true) {
                    double vel = interpolator.getVelocity(limelight.getDistanceTarget(true, telemetry));
                    shooter.setToVelocityPair(new VelocityPair(vel, vel));
                }
                else {

                }
            }
            else {
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

            telemetry.addData("EST POSE X", drivetrain.getPose().getX());
            telemetry.addData("EST POSE Y", drivetrain.getPose().getY());

            telemetry.addData("Limelight offset", limelight.getYawTarget());

            telemetry.update();
        }
    }
}

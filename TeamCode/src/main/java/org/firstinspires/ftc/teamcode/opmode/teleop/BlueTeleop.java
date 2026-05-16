package org.firstinspires.ftc.teamcode.opmode.teleop;

import static org.firstinspires.ftc.teamcode.util.Poses.pedroToAdvScope;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Prism.Color;
import org.firstinspires.ftc.teamcode.Prism.GoBildaPrismDriver;
import org.firstinspires.ftc.teamcode.Prism.PrismAnimations;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.AimCalculator;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.Logging;
import org.firstinspires.ftc.teamcode.util.VelocityPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@TeleOp(group = "Main")
public class BlueTeleop extends LinearOpMode {
    private Drivetrain drivetrain;
    private Intake intake;
    private Shooter shooter;
    private AimCalculator aimCalculator;
    private ElapsedTime loopTimer = new ElapsedTime();
    private MultipleTelemetry multipleTelemetry;
    private TelemetryPacket telemetryPacket;
    private LynxModule controlHub;
    private LynxModule expansionHub;

    private Logging logging;

    private boolean red = false;
    private AimCalculator.ShotData shotData;

    public void setAllianceColor(boolean red) {
        this.red = red;
    }

    GoBildaPrismDriver prism;
    PrismAnimations.Snakes snakes = new PrismAnimations.Snakes(Color.MAGENTA);
    PrismAnimations.Pulse pulse = new PrismAnimations.Pulse(Color.ORANGE);
    PrismAnimations.Solid solidAligned = new PrismAnimations.Solid(new Color(148, 170, 198));
    PrismAnimations.Solid solidNotAligned = new PrismAnimations.Solid(Color.RED);

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        aimCalculator = new AimCalculator(drivetrain, this.red);
        multipleTelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryPacket = new TelemetryPacket();

        prism = hardwareMap.get(GoBildaPrismDriver.class,"prism");
        snakes.setBrightness(50);
        snakes.setStartIndex(0);
        snakes.setStopIndex(12);

        pulse.setBrightness(50);
        pulse.setStartIndex(0);
        pulse.setStopIndex(12);

        solidAligned.setBrightness(50);
        solidAligned.setStartIndex(0);
        solidAligned.setStopIndex(12);

        solidNotAligned.setBrightness(50);
        solidNotAligned.setStartIndex(0);
        solidNotAligned.setStopIndex(12);

        boolean intakeButton = false;
        boolean barfButton = false;
        boolean aimButton = false;
        boolean chargeButton;
        boolean shootButton;
        boolean isAimed = false;
        boolean calculateSotm = false;

        logging = new Logging(drivetrain, shooter, hardwareMap);

        if (!RobotConstants.Drive.HAS_POSE) {
            drivetrain.setStartingPose(new Pose(17.5/2, 17.75/2, Math.toRadians(90)));
        }
        else {
            drivetrain.setStartingPose(RobotConstants.Drive.LAST_REMEMBERED_POSE);
        }

        waitForStart();

        drivetrain.startTeleopDrive();

        while (opModeIsActive()) {
            loopTimer.reset();

            drivetrain.update();
            shooter.update();

            intakeButton = (gamepad1.right_bumper || gamepad2.a);
            barfButton = gamepad1.b;
            chargeButton = gamepad2.left_bumper;
            shootButton = gamepad2.right_bumper;
            aimButton = gamepad1.left_bumper;

            aimCalculator.update();
            shotData = aimCalculator.getShotData(calculateSotm);

            // only run shoot on the move calculations if the joystick is far enough
            calculateSotm = Math.sqrt(Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2)) > 0.25;

            if (aimButton) {
                isAimed = drivetrain.setAimedTeleopDrive(
                        gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT * (red ? -1 : 1),
                        gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT * (red ? -1 : 1),
                        shotData.angle);
            }
            else {
                isAimed = false;
                drivetrain.setTeleopDrive(
                        gamepad1.left_stick_y * RobotConstants.Drive.FORWARD_SPEEDLIMIT * (red ? -1 : 1),
                        gamepad1.left_stick_x * RobotConstants.Drive.STRAFE_SPEEDLIMIT * (red ? -1 : 1),
                        -gamepad1.right_stick_x * RobotConstants.Drive.TURN_SPEEDLIMIT,
                        false);
            }

            if (gamepad1.back) {
                drivetrain.setPose(new Pose(10.5, 10.5, Math.toRadians(90)));
            }

            if (intakeButton || shootButton){
                intake.run();
            }
            else if (barfButton) {
                intake.barf();
            }
            else {
                intake.stop();
            }

            if (chargeButton)
                shooter.setToVelocityPair(new VelocityPair(shotData.rpm, shotData.rpm));
            else
                shooter.coast();


            if (intakeButton) {
                shooter.runBackPusher();
            }
            else if (shootButton && isAimed) {
                shooter.runPusher();
            }
            else if (barfButton) {
                shooter.barfPusher();
            }
            else {
                shooter.stopPusher();
            }

            if (shootButton){
                prism.insertAnimation(GoBildaPrismDriver.LayerHeight.LAYER_0, snakes);
            }
            else if (intakeButton) {
                prism.insertAnimation(GoBildaPrismDriver.LayerHeight.LAYER_0, pulse);
            }
            else if (isAimed){
                prism.insertAnimation(GoBildaPrismDriver.LayerHeight.LAYER_0, solidAligned);
            }
            else {
                prism.insertAnimation(GoBildaPrismDriver.LayerHeight.LAYER_0, solidNotAligned);
            }

            prism.updateAllAnimations();


//            telemetry.addLine("SHOOTER");
//            telemetry.addData("LOWEST RPM", lowestRpm);
//            telemetry.addData("Bottom shooter vel", shooter.getVelocityBottom());
//            telemetry.addData("Top shooter vel", shooter.getVelocityTop());
//            telemetry.addLine("DRIVETRAIN");
//            telemetry.addData("X", drivetrain.getPose().getX());
//            telemetry.addData("Y", drivetrain.getPose().getY());
//            telemetry.addData("Heading", Math.toDegrees(drivetrain.getPose().getHeading()));
//            telemetry.addLine("LIMELIGHT");
//            telemetry.addData("Turn Amount", turnAmt);
//            telemetry.addData("Is aligned with goal", limelight.isAlignedWithGoal());
//            telemetry.addData("Distance to target", limelight.getDistanceTarget(IS_RED, telemetry));

            if (RobotConstants.Drive.IS_DEBUGGING) {
                logging.updateTelemetryPacket(telemetryPacket);

                telemetryPacket.put("Aim x", pedroToAdvScope(shotData.pose).getX());
                telemetryPacket.put("Aim y", pedroToAdvScope(shotData.pose).getY());
                telemetryPacket.put("Aim heading", pedroToAdvScope(shotData.pose).getHeading());

                telemetryPacket.put("Blue auto x", pedroToAdvScope(RobotConstants.AutoPoses.BLUE_STARTING_CLOSE).getX());
                telemetryPacket.put("Blue auto y", pedroToAdvScope(RobotConstants.AutoPoses.BLUE_STARTING_CLOSE).getY());
                telemetryPacket.put("Blue auto heading", pedroToAdvScope(RobotConstants.AutoPoses.BLUE_STARTING_CLOSE).getHeading());

                telemetryPacket.put("Red auto x", pedroToAdvScope(RobotConstants.AutoPoses.RED_STARTING_CLOSE).getX());
                telemetryPacket.put("Red auto y", pedroToAdvScope(RobotConstants.AutoPoses.RED_STARTING_CLOSE).getY());
                telemetryPacket.put("Red auto heading", pedroToAdvScope(RobotConstants.AutoPoses.RED_STARTING_CLOSE).getHeading());

                telemetryPacket.put("Shooter/Target angle (degrees)", shotData.angle);
                telemetryPacket.put("Shooter/Target rpm", shotData.rpm);

                telemetryPacket.put("Shooter/Distance from target", shotData.distance);

                telemetryPacket.put("Timer Loop", loopTimer.milliseconds());

                FtcDashboard.getInstance().sendTelemetryPacket(telemetryPacket);
            }

            telemetry.addData("Loop time", loopTimer.milliseconds());
            telemetry.update();
        }

        // update last remembered pose
        RobotConstants.Drive.HAS_POSE = true;
        RobotConstants.Drive.LAST_REMEMBERED_POSE = drivetrain.getPose();
    }
}
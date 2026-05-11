package org.firstinspires.ftc.teamcode.opmode.teleop;

import static org.firstinspires.ftc.teamcode.RobotConstants.AutoPoses.BLUE_GATETAKE;
import static org.firstinspires.ftc.teamcode.RobotConstants.AutoPoses.RED_GATETAKE;
import static org.firstinspires.ftc.teamcode.util.Poses.pedroToAdvScope;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.AimCalculator;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.IndicatorRGB;
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

    private IndicatorRGB indicator;

    private boolean red = false;
    private AimCalculator.ShotData shotData;

    public void setAllianceColor(boolean red) {
        this.red = red;
    }

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        indicator = new IndicatorRGB(hardwareMap);
        aimCalculator = new AimCalculator(drivetrain, this.red);
        multipleTelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryPacket = new TelemetryPacket();

        boolean intakeButton = false;
        boolean barfButton = false;
        boolean aimButton = false;
        boolean chargeButton;
        boolean shootButton;
        boolean isAimed = false;
        boolean calculateSotm = false;
        boolean intakeGatePressed = false;
        boolean followingPath = false;

        PathChain currentPath;

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
            chargeButton = gamepad2.left_bumper || (gamepad1.right_trigger_pressed);
            shootButton = gamepad2.right_bumper || (gamepad1.right_trigger_pressed);
            aimButton = gamepad1.left_bumper;

            aimCalculator.update();
            shotData = aimCalculator.getShotData(calculateSotm);

            // only run shoot on the move calculations if the joystick is far enough
            calculateSotm = Math.sqrt(Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2)) > 0.25;

            if (gamepad1.leftTriggerWasPressed()) {
                followingPath = true;
                Pose pose = red ? RED_GATETAKE : BLUE_GATETAKE;
                currentPath = drivetrain.pathBuilder()
                        .addPath(new BezierLine(drivetrain.getPose(), pose))
                        .setLinearHeadingInterpolation(drivetrain.getPose().getHeading(), pose.getHeading())
                        .build();

                drivetrain.followPath(currentPath, true);

            }
            if (gamepad1.leftTriggerWasReleased()) {
                followingPath = false;
                drivetrain.startTeleopDrive();
            }

            if (!followingPath) {
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
            else if (shootButton) {
                shooter.runPusher();
            }
            else if (barfButton) {
                shooter.barfPusher();
            }
            else {
                shooter.stopPusher();
            }


            if (isAimed && (!Objects.equals(indicator.currentColor, "green"))) {
                indicator.green();
            }
            else if ((!Objects.equals(indicator.currentColor, "blue"))){
                indicator.blue();
            }

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

                telemetryPacket.put("Red gatetake x", pedroToAdvScope(RED_GATETAKE).getX());
                telemetryPacket.put("Red gatetake y", pedroToAdvScope(RED_GATETAKE).getY());
                telemetryPacket.put("Red gatetake heading", pedroToAdvScope(RED_GATETAKE).getHeading());

                telemetryPacket.put("Blue gatetake x", pedroToAdvScope(BLUE_GATETAKE).getX());
                telemetryPacket.put("Blue gatetake y", pedroToAdvScope(BLUE_GATETAKE).getY());
                telemetryPacket.put("Blue gatetake heading", pedroToAdvScope(BLUE_GATETAKE).getHeading());

                telemetryPacket.put("Shooter/Target angle (degrees)", shotData.angle);
                telemetryPacket.put("Shooter/Target rpm", shotData.rpm);

                telemetryPacket.put("Shooter/Distance from target", shotData.distance);

                telemetryPacket.put("Timer Loop", loopTimer.milliseconds());

                FtcDashboard.getInstance().sendTelemetryPacket(telemetryPacket);
            }

            telemetry.addData("Loop time", loopTimer.milliseconds());
            telemetry.update();

            // update last remembered pose
            RobotConstants.Drive.HAS_POSE = true;
            RobotConstants.Drive.LAST_REMEMBERED_POSE = drivetrain.getPose();
        }

    }
}
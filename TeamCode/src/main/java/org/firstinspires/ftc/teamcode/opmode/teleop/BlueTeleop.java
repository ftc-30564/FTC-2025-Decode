package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.CoordinateSystem;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.TempUnit;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.AimCalculator;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.IndicatorRGB;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.VelocityPair;

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

    private IndicatorRGB indicator;

    private boolean red = false;

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

        controlHub = hardwareMap.get(LynxModule.class, "Control Hub");
        expansionHub = hardwareMap.get(LynxModule.class, "Expansion Hub");

        boolean zeroButton;
        boolean intakeButton;
        boolean barfButton;
        boolean chargeButton;
        boolean shootButton;
        boolean isAimed = false;

        waitForStart();

        drivetrain.startTeleopDrive();

        if (RobotConstants.AutoPaths.HAS_POSE) {
            drivetrain.setStartingPose(RobotConstants.AutoPaths.LAST_REMEMBERED_POSE);
        }
        else {
            drivetrain.setStartingPose(new Pose(17.5/2, 17.75/2, Math.toRadians(90)));
        }

        while (opModeIsActive()) {
            loopTimer.reset();

            aimCalculator.update();
            drivetrain.update();

            intakeButton = (gamepad1.right_bumper || gamepad2.a);
            barfButton = gamepad1.b;
            chargeButton = gamepad2.left_bumper;
            shootButton = gamepad2.right_bumper;
            zeroButton = gamepad1.back;

            AimCalculator.ShotData shotData = aimCalculator.getShotData();

            if (gamepad1.left_bumper) {
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

            if (zeroButton) {
                if (red) {
                    drivetrain.zeroHeading();
                }
                else {
                    drivetrain.oneEightyHeading();
                }
            }

            if (gamepad1.back) {
                drivetrain.setPose(new Pose(17.5/2, 17.75/2, Math.toRadians(90)));
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


            if (isAimed) {
                indicator.green();
            }
            else {
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

            telemetryPacket.put("Shooter/Target angle (degrees)", shotData.angle);
            telemetryPacket.put("Shooter/Target rpm", shotData.rpm);
            telemetryPacket.put("Shooter/Actual Bottom rpm", shooter.getVelocityBottom());
            telemetryPacket.put("Shooter/Actual top rpm", shooter.getVelocityTop());
            telemetryPacket.put("Shooter/Distance from target", shotData.distance);
            telemetryPacket.put("Shooter/Bottom current", shooter.getBottomCurrent());
            telemetryPacket.put("Shooter/Top current", shooter.getTopCurrent());

            telemetryPacket.put("Robot x", pedroToAdvScope(drivetrain.getPose()).getX());
            telemetryPacket.put("Robot y", pedroToAdvScope(drivetrain.getPose()).getY());
            telemetryPacket.put("Robot heading", pedroToAdvScope(drivetrain.getPose()).getHeading());

            telemetryPacket.put("Aim x", pedroToAdvScope(shotData.pose).getX());
            telemetryPacket.put("Aim y", pedroToAdvScope(shotData.pose).getY());
            telemetryPacket.put("Aim heading", pedroToAdvScope(shotData.pose).getHeading());

            telemetryPacket.put("Button/Charge", chargeButton);
            telemetryPacket.put("Button/Shoot", shootButton);

            telemetryPacket.put("ControlHub/Temp", controlHub.getTemperature(TempUnit.FARENHEIT));
            telemetryPacket.put("ControlHub/Current", controlHub.getCurrent(CurrentUnit.MILLIAMPS));
            telemetryPacket.put("ExpansionHub/Temp", expansionHub.getTemperature(TempUnit.FARENHEIT));
            telemetryPacket.put("ExpansionHub/Current", expansionHub.getCurrent(CurrentUnit.MILLIAMPS));

            telemetryPacket.put("Timer Loop", loopTimer.milliseconds());

            FtcDashboard.getInstance().sendTelemetryPacket(telemetryPacket);

        }

        // update last remembered pose
        RobotConstants.AutoPaths.LAST_REMEMBERED_POSE = drivetrain.getPose();
        RobotConstants.AutoPaths.HAS_POSE = true;
    }

    public Pose pedroToAdvScope(Pose pose) {
        Pose ret = pose.unaryMinus().plus(new Pose(144, 144, 0));
        ret = ret.setHeading(ret.getHeading() * -1).getAsCoordinateSystem(FTCCoordinates.INSTANCE);
        return ret;
    }
}
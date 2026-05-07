package org.firstinspires.ftc.teamcode.util;

import static org.firstinspires.ftc.teamcode.util.Poses.pedroToAdvScope;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.TempUnit;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

public class Logging {
    private final Drivetrain drivetrain;
    private final Shooter shooter;
    private final LynxModule controlHub;
    private final LynxModule expansionHub;
    private HardwareMap hardwareMap;

    public Logging(Drivetrain drivetrain,
                   Shooter shooter,
                   HardwareMap hardwareMap) {

        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.controlHub = hardwareMap.get(LynxModule.class, "Control Hub");
        this.expansionHub = hardwareMap.get(LynxModule.class, "Expansion Hub 2");
    }

    public void updateTelemetryPacket(TelemetryPacket telemetryPacket) {
//        telemetryPacket.put("Shooter/Target angle (degrees)", shotData.angle);
//        telemetryPacket.put("Shooter/Target rpm", shotData.rpm);
        telemetryPacket.put("Shooter/Actual Bottom rpm", shooter.getVelocityBottom());
        telemetryPacket.put("Shooter/Actual top rpm", shooter.getVelocityTop());
//        telemetryPacket.put("Shooter/Distance from target", shotData.distance);
        telemetryPacket.put("Shooter/Bottom current", shooter.getBottomCurrent());
        telemetryPacket.put("Shooter/Top current", shooter.getTopCurrent());

        telemetryPacket.put("Robot x", pedroToAdvScope(drivetrain.getPose()).getX());
        telemetryPacket.put("Robot y", pedroToAdvScope(drivetrain.getPose()).getY());
        telemetryPacket.put("Robot heading", pedroToAdvScope(drivetrain.getPose()).getHeading());

        telemetryPacket.put("Robot p x", (drivetrain.getPose()).getX());
        telemetryPacket.put("Robot p y", (drivetrain.getPose()).getY());
        telemetryPacket.put("Robot p heading", (drivetrain.getPose()).getHeading());

//        telemetryPacket.put("Aim x", pedroToAdvScope(shotData.pose).getX());
//        telemetryPacket.put("Aim y", pedroToAdvScope(shotData.pose).getY());
//        telemetryPacket.put("Aim heading", pedroToAdvScope(shotData.pose).getHeading());

        telemetryPacket.put("ControlHub/Temp", controlHub.getTemperature(TempUnit.FARENHEIT));
        telemetryPacket.put("ControlHub/Current", controlHub.getCurrent(CurrentUnit.MILLIAMPS));
        telemetryPacket.put("ExpansionHub/Temp", expansionHub.getTemperature(TempUnit.FARENHEIT));
        telemetryPacket.put("ExpansionHub/Current", expansionHub.getCurrent(CurrentUnit.MILLIAMPS));
    }
}

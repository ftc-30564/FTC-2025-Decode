package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(group = "Main")
public class RedTeleop extends BlueTeleop {
    public RedTeleop() {
        setAllianceColor(true);
    }
}
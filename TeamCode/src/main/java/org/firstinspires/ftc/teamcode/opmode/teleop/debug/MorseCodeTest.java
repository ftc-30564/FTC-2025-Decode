package org.firstinspires.ftc.teamcode.opmode.teleop.debug;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.MorseCodePlayer;
import org.firstinspires.ftc.teamcode.util.MorseCodeReader;

@TeleOp(group = "Tests")
public class MorseCodeTest extends LinearOpMode {


    @Override
    public void runOpMode() {
        MorseCodeReader reader = new MorseCodeReader(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

        }
    }

}

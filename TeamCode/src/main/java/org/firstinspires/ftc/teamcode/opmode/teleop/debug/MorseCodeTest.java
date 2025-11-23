package org.firstinspires.ftc.teamcode.opmode.teleop.debug;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.IndicatorRGB;
import org.firstinspires.ftc.teamcode.subsystems.MorseCodePlayer;

@TeleOp(group = "Tests")
public class MorseCodeTest extends LinearOpMode {


    @Override
    public void runOpMode() {
        MorseCodePlayer player = new MorseCodePlayer(new IndicatorRGB(hardwareMap));

        player.addSequence(".-.. .. -.- ./.- -. -../... ..- -... ... -.-. .-. .. -... .");
        waitForStart();

        while (opModeIsActive()) {
            player.playSequence();
        }
    }

}

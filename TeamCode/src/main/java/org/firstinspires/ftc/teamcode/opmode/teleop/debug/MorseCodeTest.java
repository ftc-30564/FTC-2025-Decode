package org.firstinspires.ftc.teamcode.opmode.teleop.debug;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.IndicatorRGB;
import org.firstinspires.ftc.teamcode.subsystems.MorseCodePlayer;

@Autonomous
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

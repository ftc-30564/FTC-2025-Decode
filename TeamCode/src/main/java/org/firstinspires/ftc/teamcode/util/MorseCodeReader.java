package org.firstinspires.ftc.teamcode.util;

import android.content.res.AssetManager;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.MorseCodePlayer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

// reads the morse code message from assets
public class MorseCodeReader {
    public AssetManager manager;

    public MorseCodeReader(HardwareMap hardwareMap) {
        this.manager = hardwareMap.appContext.getApplicationContext().getAssets();
    }

    public String getMorseCode() {
        try {
            InputStream inputStream = this.manager.open("morse-code.txt");

            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "-";

            return result;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // lol
        return "--. --- --- -../.-.. ..- -.-. -.-/- ---/.- .-.. .-..";
    }
}

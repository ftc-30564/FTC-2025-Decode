package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IndicatorRGB {
    private Servo lightRGB;
    private Servo secondLightRGB;

    public IndicatorRGB(HardwareMap hardwareMap) {
        lightRGB = hardwareMap.get(Servo.class, "lightRGB");
        secondLightRGB = hardwareMap.get(Servo.class, "secondLightRGB");
    }
    public void red(){
        lightRGB.setPosition(0.277);
        secondLightRGB.setPosition(0.277);
    }
    public void green(){
        lightRGB.setPosition(0.500);
        secondLightRGB.setPosition(0.500);
    }
    public void blue(){
        lightRGB.setPosition(0.611);
        secondLightRGB.setPosition(0.611);
    }
    public void off(){
        lightRGB.setPosition(0.0);
        secondLightRGB.setPosition(0.0);
    }
    public void white() {
        lightRGB.setPosition(1);
        secondLightRGB.setPosition(1);
    }
}


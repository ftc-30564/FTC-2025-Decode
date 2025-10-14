package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.util.DistanceVelocity;

import java.util.ArrayList;
import java.util.Arrays;

public class InterpolationPoints {
    // add points here
    public static ArrayList<DistanceVelocity> points = new ArrayList<>(Arrays.asList(
            new DistanceVelocity(1, 2),
            new DistanceVelocity(2, 4),
            new DistanceVelocity(3, 7),
            new DistanceVelocity(4, 10),
            new DistanceVelocity(5, 14)
    ));
}

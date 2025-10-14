package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.InterpolationPoints;
import org.firstinspires.ftc.teamcode.util.DistanceVelocity;

import java.util.ArrayList;

public class Interpolator {

    private ArrayList<DistanceVelocity> points;

    public Interpolator(ArrayList<DistanceVelocity> points) {
        this.points = points;
    }

    // Returns the ideal wheel velocity based off of the distance from the goal
    public double getVelocity(double distance) {
        return 0;
    }
}

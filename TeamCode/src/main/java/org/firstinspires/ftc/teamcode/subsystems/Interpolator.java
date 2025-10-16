package org.firstinspires.ftc.teamcode.subsystems;

import org.opencv.core.Point;

import java.util.ArrayList;

public class Interpolator {

    private ArrayList<Point> points;

    public Interpolator(ArrayList<Point> points) {
        this.points = points;
    }

    // Returns the ideal wheel velocity based off of the distance from the goal
    public double getVelocity(double distance) {
        return 0;
    }
}

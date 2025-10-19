package org.firstinspires.ftc.teamcode.subsystems;

import org.opencv.core.Point;

import java.util.ArrayList;

public class Interpolator {

    private ArrayList<Point> points;

    public Interpolator(ArrayList<Point> points) {
        this.points = points;
    }
    public int getNearestPointIndex(double distance) {
        for (int x = 0; x < points.size(); x ++) {
            if (points.get(x).x > distance) {
               return x - 1;
            }
        }
        throw new IndexOutOfBoundsException("Could not find nearest point in interpolator");
    }

    // Returns the ideal wheel velocity based off of the distance from the goal
    public double getVelocity(double distance) {
        Point leftPoint = points.get(getNearestPointIndex(distance));
        Point rightPoint = points.get(getNearestPointIndex(distance) + 1);

        double slope = (rightPoint.y - leftPoint.y) / (rightPoint.x - leftPoint.x);
        double yIntercept = (leftPoint.y - (slope * leftPoint.x));
        return slope * distance + yIntercept;
    }
}

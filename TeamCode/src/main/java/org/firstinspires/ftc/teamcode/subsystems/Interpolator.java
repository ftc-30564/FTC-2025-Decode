package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.opencv.core.Point;

import java.util.ArrayList;

public class Interpolator {
    private ArrayList<Point> points;
    private double lastValid = RobotConstants.Shooter.CLOSE_VELOCITY.bottom;

    public Interpolator(ArrayList<Point> points) {
        this.points = points;
    }
    public int getNearestPointIndex(double distance) {
        if (distance < points.get(0).x) {
            return 0;
        }

        for (int x = 0; x < points.size(); x ++) {
            if (points.get(x).x > distance) {
               return x - 1;
            }
        }
        // return the second to last point instead
        return points.size() - 2;
    }

    // Returns the ideal wheel velocity based off of the distance from the goal
    public double getVelocity(double distance) {
        if (distance == 0)
            return lastValid;

        Point leftPoint = points.get(getNearestPointIndex(distance));
        Point rightPoint = points.get(getNearestPointIndex(distance) + 1);

        // calculate the line formula between the two points
        double slope = (rightPoint.y - leftPoint.y) / (rightPoint.x - leftPoint.x);
        double yIntercept = (leftPoint.y - (slope * leftPoint.x));

        lastValid = (slope * distance) + yIntercept;
        return lastValid;
    }
}

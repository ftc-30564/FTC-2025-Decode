package org.firstinspires.ftc.teamcode.util;

import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.Pose;

public class PoseConversion {
    public static Pose pedroToAdvScope(Pose pose) {
        Pose ret = pose.unaryMinus().plus(new Pose(144, 144, 0));
        ret = ret.setHeading(ret.getHeading() * -1).getAsCoordinateSystem(FTCCoordinates.INSTANCE);
        return ret;
    }
}

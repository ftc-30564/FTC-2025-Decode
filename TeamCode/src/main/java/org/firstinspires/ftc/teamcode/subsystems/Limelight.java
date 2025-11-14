package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

public class Limelight {
    private Limelight3A limelight;
    private HardwareMap hardwareMap;

    private final int RED_GOAL_PIPELINE = 0;
    private final int BLUE_GOAL_PIPELINE = 1;
    private final int OBELISK_PIPELINE = 2;

    public Limelight(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        limelight.setPollRateHz(100);
    }

    public void setRedGoalPipeline() {
        limelight.pipelineSwitch(RED_GOAL_PIPELINE);
    }

    public void setBlueGoalPipeline() {
        limelight.pipelineSwitch(BLUE_GOAL_PIPELINE);
    }

    public void setObeliskPipeline() {
        limelight.pipelineSwitch(OBELISK_PIPELINE);
    }

    public void start() {
        limelight.start();
    }

    public double getOffsetTarget() {
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid() && result.getStaleness() < 100) {
            return result.getTx();
        }

        return 0;

    }

    public boolean isAlignedWithGoal() {
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid() && result.getStaleness() < 100) {
            if (limelight.getLatestResult().getTx() <=1.0 && limelight.getLatestResult().getTx() >= -1.0) {
                return true;
            }
        }

        return false;
    }

    public Pose getPoseEstimate(double heading) {
        LLResult result = limelight.getLatestResult();
        limelight.updateRobotOrientation(heading);

        if (result != null && result.isValid()) {
            Pose3D botpose_mt2 = result.getBotpose_MT2();
            if (botpose_mt2 != null) {
                double x = botpose_mt2.getPosition().x;
                double y = botpose_mt2.getPosition().y;

                return new Pose(x, y, heading);
            }
        }

        return null;
    }
}

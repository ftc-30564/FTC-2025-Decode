package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

// RED:
// forward -0.4  right 0.17  up 0
// BLUE:
// forward -0.1  right 0.12  up 0

public class Limelight {
    private Limelight3A limelight;
    private HardwareMap hardwareMap;

    private final int RED_GOAL_PIPELINE = 0;
    private final int BLUE_GOAL_PIPELINE = 1;
    private final int OBELISK_PIPELINE = 2;

    private final double BLUE_OFFSET = -3;
    private final double RED_OFFSET = -6.425;

    private double offset = 0;

    public Limelight(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        limelight.setPollRateHz(100);
    }

    public void setRedGoalPipeline() {
        limelight.pipelineSwitch(RED_GOAL_PIPELINE);
        offset = RED_OFFSET;
    }

    public void setBlueGoalPipeline() {
        limelight.pipelineSwitch(BLUE_GOAL_PIPELINE);
        offset = BLUE_OFFSET;
    }

    public void setObeliskPipeline() {
        limelight.pipelineSwitch(OBELISK_PIPELINE);
    }

    public void start() {
        limelight.start();
    }

    public boolean isConnected(){
        return limelight.isConnected();
    }

    public double getYawTarget() {
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            return result.getTx() + offset;
        }

        return 0;

    }

    public boolean isAlignedWithGoal() {
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid() && result.getStaleness() < 100) {
            return (result.getTx() + offset) <= 3.0 && (result.getTx() + offset) >= -3.0;
        }

        return false;
    }

    public boolean seesTarget() {
        // checks to see if there are any AprilTags
        return limelight.getLatestResult().getFiducialResults() != null;
    }

    public double getDistanceTarget(boolean red, Telemetry telemetry) {
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid() && result.getStaleness() < 100) {
            List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();

            for (LLResultTypes.FiducialResult fiducial : fiducials) {
                if (fiducial.getFiducialId() != (red ? 24 : 20))
                    continue;
                Pose3D targetPose = fiducial.getCameraPoseTargetSpace();

//                telemetry.addData("Pose X", targetPose.getPosition().x);
//                telemetry.addData("Pose Y", targetPose.getPosition().y);
//                telemetry.addData("Pose Z", targetPose.getPosition().z);

                return Math.sqrt(Math.pow(targetPose.getPosition().x, 2) + Math.pow(targetPose.getPosition().z, 2));
            }
        }
        return 0;
    }

    public Pose getPoseEstimate(double heading) {
        limelight.updateRobotOrientation(heading);

        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid() && (result.getStaleness() < 30)) {
            Pose3D botpose_mt2 = result.getBotpose_MT2();
            if (botpose_mt2 != null) {
                // converting limelight's weird coordinate system to pedro
                double x = (botpose_mt2.getPosition().y) * 38.3701 + 72;
                double y = (-botpose_mt2.getPosition().x) * 39.3701 + 72;

                return new Pose(x, y, Math.toRadians(heading - 90));
            }
        }

        return null;
    }
}

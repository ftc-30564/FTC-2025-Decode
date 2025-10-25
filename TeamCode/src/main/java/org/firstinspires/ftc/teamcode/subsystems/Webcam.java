package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Webcam {
    private VisionPortal visionPortal;               // Used to manage the video source.
    private AprilTagProcessor aprilTag;              // Used for managing the AprilTag detection process.
    private AprilTagDetection desiredTag = null;     // Used to hold the data for a detected AprilTag
    private HardwareMap hardwareMap;

    private final int RESOLUTION_X = 640;

    public Webcam(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public void init() {
        aprilTag = new AprilTagProcessor.Builder().build();
        aprilTag.setDecimation(2);

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .enableLiveView(true)
                .addProcessor(aprilTag)
                .setAutoStopLiveView(false) // keeps stream running after start
                .build();
    }

    public void close() {
        visionPortal.close();
    }

    private Pose getOffsetApriltag(int tag) {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == tag) {
                Pose3D tagPose = detection.robotPose;
                return new Pose(tagPose.getPosition().x, tagPose.getPosition().y, tagPose.getOrientation().getYaw(AngleUnit.RADIANS));
            }
        }

        return new Pose();
    }

    public Pose getOffsetRedTarget() {
        return getOffsetApriltag(24);
    }

    public Pose getOffsetBlueTarget() {
        return getOffsetApriltag(20);
    }
}

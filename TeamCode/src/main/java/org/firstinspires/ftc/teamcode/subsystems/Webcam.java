package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
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
        aprilTag = new AprilTagProcessor.Builder()
                .setCameraPose(new Position(DistanceUnit.INCH,
                                0,
                                0,
                                0,
                                0),
                        new YawPitchRollAngles(AngleUnit.DEGREES,
                                0,
                                0,
                                0,
                                0)
                )
                .setDrawAxes(true)
                .build();
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

    public double getOffsetApriltagInches(int tag) {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == tag) {
                return detection.robotPose.getPosition().y;
            }
        }

        return 0;
    }

    public double getOffsetApriltagDegrees(int tag) {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == tag) {
                return Math.atan2(detection.robotPose.getPosition().y, detection.robotPose.getPosition().x);
            }
        }

        return 0;
    }

    public double getOffsetRedTarget() {
        return getOffsetApriltagInches(24);
    }

    public double getOffsetBlueTarget() {
        return getOffsetApriltagInches(20);
    }
}

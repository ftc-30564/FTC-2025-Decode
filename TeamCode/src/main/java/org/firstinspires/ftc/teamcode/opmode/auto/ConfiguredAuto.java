package org.firstinspires.ftc.teamcode.opmode.auto;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.opmode.auto.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.command_lib.AutoConfiguration;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;
import org.firstinspires.ftc.teamcode.util.command_lib.SequentialCommand;

import java.util.ArrayList;

// Placeholder auto
public class ConfiguredAuto extends LinearOpMode {
    private Drivetrain drivetrain;
    private Intake intake;
    private Shooter shooter;

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);

        boolean atStarting = true;
        Pose startingPose = null;
        Pose lastPose = null;

        Command command = new SequentialCommand();

        if (!AutoConfiguration.autonomousConfigured) {
            telemetry.addLine("NO CONFIGURATION DETECTED");
            telemetry.update();
        }
        else {
            ArrayList<String> names = AutoConfiguration.commandNames;
            ArrayList<String> details = AutoConfiguration.commandDetails;

            for (int x = 0; x < names.size(); x ++) {
                String currName = names.get(x);
                String currDetail = details.get(x);

                if (currName.equals("Start")) {
                    // TODO: add color selector to config opmode
                    startingPose = (details.get(x).equals("Far") ? RobotConstants.Auto.RED_STARTING_FAR : RobotConstants.Auto.RED_STARTING_CLOSE);
                    lastPose = startingPose;
                    atStarting = true;
                }
                else if (currName.equals("Score")) {
                    if (lastPose == null) throw new RuntimeException("Trying to score starting from unknown position");
                    if (atStarting) {
                        command.add(new FollowPathCommand(drivetrain, RobotConstants.Auto.startToShootPath(drivetrain, currDetail.equals("Near"), true), telemetry));
                    }
                }
            }
        }

        waitForStart();
        while (opModeIsActive()) {


            telemetry.update();
        }
    }
}

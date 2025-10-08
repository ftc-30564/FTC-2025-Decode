package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ArrayList;

/*
This is an experimental OpMode that should let the driver fully customize the autonomous period.
Here is an example on how the telemetry should look like:
---------------------------------------------------------
    Start: Near
    Score: Near
    Pick up: First Line
    Score: Far
    Pick up: Second Line
    Score: Near
    Delay: 3
    Pick up: Third Line
---------------------------------------------------------
The OpMode is controlled using the bumpers to cycle through commands,
and the dpad to cycle through the detail. This allows for an infinitely customizable
autonomous and allows us to work with other teams.
*/
@TeleOp
public class AutoConfiguration extends LinearOpMode {

    // Very simple wrapper that handles cycling between multiple options.
    public  class OptionSelector {
        private ArrayList<String> list;
        private int currentIndex = 0;

        public OptionSelector() {
            list = new ArrayList<>();
        }

        public String getCurrentOption() {
            return list.get(currentIndex);
        }

        public void add(String s) {
            list.add(s);
        }

        public void shiftUp() {
            currentIndex ++;
            if (currentIndex >= list.size()) {
                currentIndex = 0;
            }
        }

        public void shiftDown() {
            currentIndex --;
            if (currentIndex < 0) {
                currentIndex = list.size()-1;
            }
        }
    }

    @Override
    public void runOpMode() {
        ArrayList<String> commandNames = new ArrayList<>();
        ArrayList<String> commandDetails = new ArrayList<>();

        OptionSelector autoCommandSelector = new OptionSelector();

        OptionSelector sideSelector = new OptionSelector();
        OptionSelector pickupSelector = new OptionSelector();
        OptionSelector delaySelector = new OptionSelector();
        OptionSelector blankSelector = new OptionSelector();

        blankSelector.add("");

        autoCommandSelector.add("Start");
        autoCommandSelector.add("Pick up");
        autoCommandSelector.add("Score");
        autoCommandSelector.add("Delay");

        sideSelector.add("Far");
        sideSelector.add("Near");

        pickupSelector.add("First Line");
        pickupSelector.add("Second Line");
        pickupSelector.add("Third Line");

        for (double x = 0; x < 15; x += 0.5) {
            delaySelector.add(Double.toString(x));
        }

        if (AutoConfig.autonomousConfigured) {
            telemetry.addLine("Autonomous configuration detected.");
            telemetry.update();
        }

        waitForStart();


        OptionSelector currentSelector = sideSelector;
        boolean warningHandled = false;

        while (opModeIsActive()) {
            if (AutoConfig.autonomousConfigured && (!warningHandled)) {
                if (gamepad1.x) {
                    telemetry.clear();
                    for (int x = 0; x < AutoConfig.commandNames.size(); x ++) {
                        telemetry.addData(AutoConfig.commandNames.get(x), AutoConfig.commandDetails.get(x));
                    }
                }
                else {
                    telemetry.addLine("Autonomous configuration detected.");
                    telemetry.addLine("Press X to view current configuration.");
                    telemetry.addLine("Press A to continue and erase current configuration.");
                    telemetry.addLine("Press B to wipe configuration and exit.");
                }
                if (gamepad1.a) {
                    warningHandled = true;
                    AutoConfig.commandNames.clear();
                    AutoConfig.commandDetails.clear();
                    AutoConfig.autonomousConfigured = false;
                }
                if (gamepad1.b) {
                    AutoConfig.commandNames.clear();
                    AutoConfig.commandDetails.clear();
                    AutoConfig.autonomousConfigured = false;
                    return;
                }
            }
            else {
                for (int x = 0; x < commandNames.size(); x ++) {
                    telemetry.addData(commandNames.get(x), commandDetails.get(x));
                }
                telemetry.addData(autoCommandSelector.getCurrentOption(), currentSelector.getCurrentOption());

                boolean leftBumperPressed = gamepad1.leftBumperWasPressed();
                boolean rightBumperPressed = gamepad1.rightBumperWasPressed();

                if (leftBumperPressed) {
                    autoCommandSelector.shiftDown();
                }
                if (rightBumperPressed) {
                    autoCommandSelector.shiftUp();
                }

                if (leftBumperPressed || rightBumperPressed) {
                    if (autoCommandSelector.getCurrentOption().equals("Start")) {
                        currentSelector = sideSelector;
                    }
                    else if (autoCommandSelector.getCurrentOption().equals("Pick up")) {
                        currentSelector = pickupSelector;
                    }
                    else if (autoCommandSelector.getCurrentOption().equals("Score")) {
                        currentSelector = sideSelector;
                    }
                    else if (autoCommandSelector.getCurrentOption().equals("Delay")) {
                        currentSelector = delaySelector;
                    }
                }

                if (gamepad1.dpadRightWasPressed()) {
                    currentSelector.shiftUp();
                }
                else if (gamepad1.dpadLeftWasPressed()) {
                    currentSelector.shiftDown();
                }

                if (gamepad1.aWasPressed()) {
                    commandNames.add(autoCommandSelector.getCurrentOption());
                    commandDetails.add(currentSelector.getCurrentOption());

                    AutoConfig.commandNames = commandNames;
                    AutoConfig.commandDetails = commandDetails;
                    AutoConfig.autonomousConfigured = true;
                }
                if (gamepad1.bWasPressed()) {
                    commandNames.clear();
                    commandDetails.clear();

                    AutoConfig.commandNames.clear();
                    AutoConfig.commandDetails.clear();
                    AutoConfig.autonomousConfigured = false;
                }
            }


            telemetry.update();
        }
    }
}
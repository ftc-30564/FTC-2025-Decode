package org.firstinspires.ftc.teamcode.opmode.teleop.debug;
import static org.firstinspires.ftc.teamcode.Prism.GoBildaPrismDriver.LayerHeight;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Prism.Color;
import org.firstinspires.ftc.teamcode.Prism.Direction;
import org.firstinspires.ftc.teamcode.Prism.GoBildaPrismDriver;
import org.firstinspires.ftc.teamcode.Prism.PrismAnimations;

import java.util.concurrent.TimeUnit;

    @TeleOp(name="Prism Animations", group="Tests")
//@Disabled

public class GoBildaPrismExample extends LinearOpMode {

    GoBildaPrismDriver prism;

    PrismAnimations.Snakes snakeLeft  = new PrismAnimations.Snakes(new Color(0, 240, 255));
    PrismAnimations.Snakes snakeRight = new PrismAnimations.Snakes(new Color(0, 240, 255));

    PrismAnimations.Solid solidWhite = new PrismAnimations.Solid(Color.WHITE);

    PrismAnimations.DroidScan droidScan = new PrismAnimations.DroidScan(new Color(0, 240, 255));

    @Override
    public void runOpMode() {
        /*
         * Initialize the hardware variables. Note that the strings used here must correspond
         * to the names assigned during the robot configuration step on the driver's station.
         */
        prism = hardwareMap.get(GoBildaPrismDriver.class,"prism");

        /*
         * Here you can customize the specifics of different animations. Each animation has its
         * own set of parameters that you can customize to create something unique! Each animation
         * has carefully selected default parameters. So you do not need to set each parameter
         * for every animation!
         */
        solidWhite.setBrightness(80);
        solidWhite.setStartIndex(0);
        solidWhite.setStopIndex(23);

        droidScan.setIndexes(0, 23);

        snakeLeft.setIndexes(0, 11);
        snakeRight.setIndexes(12, 23);

        snakeLeft.setDirection(Direction.Backward);
        snakeRight.setDirection(Direction.Backward);

        snakeLeft.setSpeed(0.7f);
        snakeRight.setSpeed(0.7f);

        droidScan.setSpeed(0.7f);

        snakeLeft.setBrightness(80);
        snakeRight.setBrightness(80);

        telemetry.addData("Device ID: ", prism.getDeviceID());
        telemetry.addData("Firmware Version: ", prism.getFirmwareVersionString());
        telemetry.addData("Hardware Version: ", prism.getHardwareVersionString());
        telemetry.addData("Power Cycle Count: ", prism.getPowerCycleCount());
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();
        resetRuntime();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


            if(gamepad1.aWasPressed()){
                /*
                 * Here we insert and update the animation to the Prism, this by default does not
                 * save it to an Artboard, it just starts the Animation playing. If you have
                 * already inserted an animation at a layer height, you can instead call
                 * .updateAnimationFromIndex(LayerHeight.LAYER_0) to update an animation at a
                 * specific layer height without overwriting it completely.
                 */
                prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, droidScan);

            }

            if(gamepad1.xWasPressed()){
                /*
                 * Clearing the animation doesn't erase any saved Artboards, but it removes all the
                 * currently displayed animations.
                 */
                prism.clearAllAnimations();
            }

            if(gamepad1.dpadDownWasPressed()){
                /*
                 * Here we save the animation we are currently displaying to Artboard 0.
                 */
                prism.saveCurrentAnimationsToArtboard(GoBildaPrismDriver.Artboard.ARTBOARD_0);
            }

            telemetry.addLine("Press A to insert and update the created animations.");
            telemetry.addLine("Press X to clear current animations.");
            telemetry.addLine("Press D-Pad Down to save current animations to Artboard #0");
            telemetry.addLine();
            telemetry.addData("Run Time (Hours): ",prism.getRunTime(TimeUnit.HOURS));
            telemetry.addData("Run Time (Minutes): ",prism.getRunTime(TimeUnit.MINUTES));
            telemetry.addData("Number of LEDS: ", prism.getNumberOfLEDs());
            telemetry.addData("Current FPS: ", prism.getCurrentFPS());
            telemetry.update();
            sleep(50);
        }
    }

}

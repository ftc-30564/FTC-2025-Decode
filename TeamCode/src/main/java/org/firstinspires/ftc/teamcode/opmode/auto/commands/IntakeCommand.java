package org.firstinspires.ftc.teamcode.opmode.auto.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.command_lib.Command;

public class IntakeCommand extends Command {
    private Intake intake;
    private Intake.Mode mode;

    /**
     * A command that sets the intake
     * @param intake The intake object
     * @param mode The mode to set the intake to
     */
    public IntakeCommand(Intake intake, Intake.Mode mode) {
        this.intake = intake;
        this.mode = mode;
    }

    @Override
    public void loop() {
        if (mode == Intake.Mode.RUNNING) {
            intake.run();
        }
        else if (mode == Intake.Mode.BARFING) {
            intake.barf();
        }
        else {
            intake.stop();
        }
    }

    @Override
    public void end() {
        intake.stop();
    }
}

# Command Library
Here is a custom implementation of a command-based wrapper for autonomous.
This just allows us to run different "commands" (like running the intake, priming the shooter).
These commands can be run in sequence or in parallel, and you can nest these commands.
This basically is the same as the FTCLib command library, but a custom implementation of it and a more simplified version.
## Example

```java
// inside runOpMode()...
        CommandScheduler commandScheduler = new CommandScheduler(this::opModeIsActive);
        
        Command auto_1 = new SequentialCommand(
                new ParallelCommand(
                        new DrivetrainCommand(drivetrain, 0.5, 0, 0, 1000),
                        new IntakeCommand(intake, intake.RUN, 1000)   
                ),
                new ParallelCommand(
                        new TrajectoryCommand(drivetrain, drivetrain.SHOOTING_POS),
                        new ShooterCommand(shooter, shooter.CHARGE, 15)
                )
        );
        while (opModeIsActive()) {
            commandScheduler.run(auto_1);
        }
```

This is just an example with fake commands, but it shows how it is structured and how it makes autonomous easier.

## File Structure
`commands`: consist of the unique robot commands, like `IntakeCommand`, `ShooterCommand`, `TrajectoryCommand`, etc. Feel free to add commands to this bit.

`library`: all the behind the scenes stuff, like `Command`, `CommandScheduler`, `SequentialCommand`, etc. Don't touch this bit

## Use
This should only be used for autonomous for now.
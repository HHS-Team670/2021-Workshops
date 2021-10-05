import edu.wpi.first.wpilibj.command.Command;
import frc.team670.robot.Robot;
import frc.team670.roboy.subsystems.SampleIntake;
import frc.team670.robot.utils.Logger;

public class RunIntake extends RunCommand {
    boolean accelerated, reversed;
    SampleIntake intake;

    public RunIntake(boolean accelerated, boolean reversed, SampleIntake intake) {
        this.accelerated = accelerated;
        this.reversed = reversed;
        this.intake = intake;
        addRequirements(intake);

    }

    @Override
    public void initialize() {
        Logger.consoleLog("Perparing to roll intake");
        intake.setAccelerate(accelerated);
    }

    public void execute() {
        intake.roll(reversed); //calling the method roll and passing in the reverse
    }

}
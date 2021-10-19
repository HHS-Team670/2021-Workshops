import edu.wpi.first.wpilibj.command.Command;
import frc.team670.robot.Robot;
import frc.team670.robot.subsystems.SampleIntake;
import frc.team670.robot.utils.Logger;

public class RunIntakeTimed extends WaitCommand {

    boolean accelerated, reversed;
    SampleIntake intake;

    public RunIntakeTimed(boolean accelerated, boolean reversed, double seconds, SampleIntake intake) {
        super(seconds);
        this.accelerated = accelerated;
        this.reversed = reversed;
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        Logger.consoleLog("Preparing to roll intake");
        intake.setAccelerate(accelerated);
    }

    public void execute() {
        intake.roll(reversed);
    }

    public void end() {
        intake.stop();
        Logger.consoleLog("Done rolling intake");
    }

}

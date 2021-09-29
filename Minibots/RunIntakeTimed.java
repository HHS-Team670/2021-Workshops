package Minibots;
import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.*;
import Minibots.*;

public class RunIntakeTimed extends TimedCommand {

    double seconds;
    SampleIntake intake;

    public RunIntakeTime(double secs) {
        this.seconds = secs;
        this.intake = new SampleIntake();
        addRequirements(this.intake);
        super("yee", this.seconds, this.intake);
    }

    @Override
    protected void initialize() {
        this.start();
        intake.deploy(true);
        intake.roll(true);
        intake.setAccelerate(true);
    }

    @Override
    protected void execute() {
        intake.mustangPeriodic();
    }

    @Override
    public boolean isFinished() {
        if (intake.checkHealth() != HealthState.GREEN) {
            return true;
        }
        if (intake.isJammed()) {
            return true;
        }
        if (super.isFinished()) {
            return true;
        }
        return false;
    }

    @Override
    public void end() {
        intake.stop();
    }
    
}

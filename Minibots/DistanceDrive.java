import edu.wpi.first.wpilibj.command.Command;
import frc.team670.robot.Robot;
import frc.team670.robot.utils.Logger;

public class DistanceDrive extends CommandBase {
    double distance, power;
    DriveBase base;
    
    public DistanceDrive(double distance, double power, DriveBase base) {
        this.distance = distance;
        this.leftpower = power;
        this.rightpower = power;
        this.base = base;
        addRequirements(base);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        base.tankDrive(leftPower, rightPower);
        correct();
    }

    @Override
    public void isFinished() {

    }

    @Override
    public void end() {

    }

}

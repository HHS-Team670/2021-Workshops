import edu.wpi.first.wpilibj.command.Command;
import frc.team670.robot.Robot;
import frc.team670.robot.utils.Logger;

public class DistanceDrive extends CommandBase {

    private DriveBase base;
    private double leftPower;
    private double rightPower;
    private double inches;
    private int ticks;

    public DistanceDrive(double inches, double leftPower, double rightPower) {
        inches = inches;
        leftPower = leftPower;
        rightPower = rightPower;

        double diameter = 2.497;
        double pi = 3.14195;

        double circumference = (pi * diameter);
        ticks = (int) (inches / circumference * 800);
        addRequirements(base);

    }

    @Override
    public void execute() {
        base.tankDrive(leftPower, rightPower);
        correct();
    }

    @Override
    public void isFinished() {
        Encoder leftEncoder = base.getLeftEncoder().getTicks();
        Encoder rightEncoder = base.getRightEncoder().getTicks();
        if (rightEncoder >= ticks && leftEncoder >= ticks) {
            end();
        }
    }

    @Override
    public void end() {
        base.stop();
    }

    public void correct() {
        Encoder leftEncoder = base.getLeftEncoder();
        Encoder rightEncoder = base.getRightEncoder();

        if (Math.abs(leftEncoder.getTicks() - rightEncoder.getTicks()) < 5) 
            return;

        if (leftEncoder.getTicks() < rightEncoder.getTicks()){
            rightPower -= 0.01;
        } else if (leftEncoder.getTicks() > rightEncoder.getTicks()){
            leftPower -= 0.01;
        }

        Logger.consoleLog("TicksL: %s TicksR: %s, SpeedL: %s SpeedR: %s", leftEncoder.getTicks(), rightEncoder.getTicks(), leftPower, rightPower);
    }

}

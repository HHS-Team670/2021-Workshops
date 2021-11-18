import edu.wpi.first.wpilibj.command.Command;
import frc.team670.robot.Robot;
import frc.team670.robot.utils.Logger;

public class DistanceDrive extends CommandBase {

    private Drivebase drivebase;
    private double distance, leftPower, rightPower;

    public DistanceDrive() {
        //initialize fields
        
    }

    @Override
    public void execute() {
        drivebase.tankDrive(leftPower, rightPower);
        correct();
    }

    @Override
    public boolean isFinished() {
        int leftTicks = drivebase.getLeftEncoder().getTicks();

        double rotations = leftTicks/800;
        double circumference = 2.497*Math.PI;
        double distance_traveled = rotations*circumference;
    

        if (distance_traveled >= distance){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void end(boolean isFinished) {
        drivebase.stop();
    }


    public void correct() {
        Encoder leftEncoder = base.getLeftEncoder();
        Encoder rightEncoder = base.getRightEncoder();

        if (Math.abs(leftEncoder.getTicks() - rightEncoder.getTicks()) < 5) 
            return;

        if (leftEncoder.getTicks() < rightEncoder.getTicks()){
            rightPower -= 0.01;
        } 
        else if (leftEncoder.getTicks() > rightEncoder.getTicks()){
            leftPower -= 0.01;
        }

        Logger.consoleLog("TicksL: %s TicksR: %s, SpeedL: %s SpeedR: %s", leftEncoder.getTicks(), rightEncoder.getTicks(), leftPower, rightPower);
    }

}

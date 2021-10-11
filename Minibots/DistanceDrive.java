import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team670.robot.subsystems.DriveBase;
import frc.team670.robot.utils.Logger;

public class DistanceDrive extends CommandBase {
    DriveBase base;
    double leftPower;
    double rightPower;
    double distance;

    public DistanceDrive(DriveBase base, double power, double distance) {
        this.leftPower = power;
        this.rightPower = power;
        this.distance = distance;
        addRequirements(base);
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

    @Override
    public void execute() {
        base.tankDrive(leftPower, rightPower);
        correct();
    }

    @Override
    public boolean isFinished() {
        if (((base.getLeftEncoder().getTicks())/ 800) * (2.497*Math.PI) >= distance||((base.getRightEncoder().getTicks())/ 800) * (2.497*Math.PI) >= distance) {
            return true;
        }else{
            return false; 
        }

    }

    @Override
    public void end(boolean Finished) {
        if(Finished){
            base.stop();
        }
    }

}

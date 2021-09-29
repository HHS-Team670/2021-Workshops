/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team670.robot.subsystems.DriveBase;
import frc.team670.robot.utils.Logger;

/**
 * Add your docs here.
 */
public class BalancedDrive extends WaitCommand {

    private DriveBase base;
    private double leftPower;
    private double rightPower;

    public BalancedDrive(double seconds, double power, DriveBase base){
        super(seconds);

        this.leftPower = power;
        this.rightPower = power;
        addRequirements(base);
        this.base = base;

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

    public void execute() {
        base.tankDrive(leftPower, rightPower);
        correct();
    }

    public void end(boolean isInteruppted) {
        base.stop();
    }

}

package frc.team670.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc.team670.robot.Robot;
import frc.team670.robot.RobotContainer;
import frc.team670.robot.subsystems.DriveBase;
import frc.team670.robot.utils.Logger;
import frc.team670.robot.utils.MathUtils;

public class PIDDistanceDrive extends CommandBase {

	PIDController m_leftController, m_rightController;

	double m_targetInches;
	DriveBase driveBase;

	// TODO: Find values
	// proportional speed constant
	double kP = 0.1;

	// integral speed constant
	double kI = 0.0;

	// derivative speed constant
	double kD = 0.0;

	// margin of error
	double TOLERANCE_INCHES = 0.5;

	/**
	 * 
	 * @param target Target distance in inches
	 */
	public PIDDistanceDrive() {
		
		double toleranceTicks = MathUtils.convertInchesToEncoderTicks(TOLERANCE_INCHES);
		
        m_leftController.setTolerance(toleranceTicks);

        m_rightController.setTolerance(toleranceTicks);
		
	}

	public void initialize() {
	    
		
	}

	public void execute() {
	    
	}

	public boolean isFinished() {
        return (  ||  );
	}

	public void end(boolean isFinished) {
		
	}
}

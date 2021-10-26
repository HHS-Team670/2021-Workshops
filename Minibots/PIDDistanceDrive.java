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
	public PIDDistanceDrive(double targetInches, Drivebase drivebase) {

		m_targetInches = targetInches;		
		
		
		m_leftController = new PIDController(kP, kI, kD);
		m_rightController = new PIDController(kP, kI, kD);

		double toleranceTicks = MathUtils.convertInchesToEncoderTicks(TOLERANCE_INCHES);


        m_leftController.setTolerance(toleranceTicks);

        m_rightController.setTolerance(toleranceTicks);

		addRequirements(driveBase);
		
	}

	public void initialize() {
	    
		// set the setpoint on the pid controllers with target inches
		double m_targetTicks = MathUtils.convertInchesToEncoderTicks(m_targetInches);

		// look at documentation for a method to set setpoint on both PID controllers

		
	}

	public void execute() {
	    
		// find the error in ticks using drivebase encoders

		// look at documentation for a method to calculate the new speed with this error

		// call tankDrive with these new speeds

	}

	public boolean isFinished() {

		// documentation has method for checking if the PID controller has reached the setpoint

        return (  ||  );
	}

	public void end(boolean isInterrupted) {
		driveBase.stop();
	}
}

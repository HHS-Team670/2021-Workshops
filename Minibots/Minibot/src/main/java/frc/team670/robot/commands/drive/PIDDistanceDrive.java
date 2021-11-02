package frc.team670.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc.team670.robot.Robot;
import frc.team670.robot.RobotContainer;
import frc.team670.robot.subsystems.DriveBase;
import frc.team670.robot.utils.Logger;
import frc.team670.robot.utils.MathUtils;

public class PIDDistanceDrive extends CommandBase {

	PIDController m_rightController;

	double m_targetInches;
	DriveBase driveBase;

	// TODO: Play around with these constants
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
	public PIDDistanceDrive(double targetInches, DriveBase drivebase) {

		m_targetInches = targetInches;		
		
		
		m_rightController = new PIDController(kP, kI, kD);

		double toleranceTicks = MathUtils.convertInchesToEncoderTicks(TOLERANCE_INCHES);

        m_rightController.setTolerance(toleranceTicks);

		addRequirements(driveBase);
		
	}

	public void initialize() {
	    
		// set the setpoint on the pid controllers with target inches
		double m_targetTicks = MathUtils.convertInchesToEncoderTicks(m_targetInches);

		// look at documentation for a method to set setpoint on both PID controllers
		m_rightController.setSetpoint(m_targetTicks);
	}

	public void execute() {
	    
		// find the error in ticks using drivebase encoders
		double rTicks = driveBase.getRightEncoder().getTicks();

		// look at documentation for a method to calculate the new speed with this error
		double speed = edu.wpi.first.wpiutil.math.MathUtils.clamp(m_rightController.calculate(rTicks), -1, 1);

		// call tankDrive with these new speeds
		driveBase.tankDrive(-speed, -speed);

	}

	public boolean isFinished() {

		// documentation has method for checking if the PID controller has reached the setpoint

        return (m_rightController.atSetpoint());
	}

	public void end(boolean isInterrupted) {
		driveBase.stop();
	}
}

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

		driveBase

		m_targetInches = targetInches;
		
		double toleranceTicks = MathUtils.convertInchesToEncoderTicks(TOLERANCE_INCHES);
		
		m_leftController = new PIDController(kP, kI, kD);

		m_rightController = new PIDController(kP, kI, kD);

        m_leftController.setTolerance(toleranceTicks);

        m_rightController.setTolerance(toleranceTicks);

		addRequirements(driveBase);
		
	}
	public void initialize () {
		double toleranceTicks = MathUtils.convertInchesToEncoderTicks(m_targetInches);
		m_leftController.setSetpoint(m_targetInches);
		m_rightController.setSetpoint(m_targetInches);
	}

	public void execute() {
		public Encoder getLeftEncoder() {
			// TODO Auto-generated method stub
			return this.le;
		}
	
		public Encoder getRightEncoder() {
			return this.re;
		}
	    driveBase(m_leftController.calculate(m_rightController.getTicks, targetInches(m_rightController.calculate(m_leftController.getTicks, targetInches))

	}

	public boolean isFinished() {
        return (m_leftController.setPoint()|| m_rightController.setPoint());
	}

	public void end(boolean isFinished) {
		driveBase.stop();
	}
}
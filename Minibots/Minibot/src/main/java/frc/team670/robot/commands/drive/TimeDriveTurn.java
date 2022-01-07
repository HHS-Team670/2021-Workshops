package frc.team670.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team670.robot.Robot;
import frc.team670.robot.subsystems.DriveBase;
import frc.team670.robot.utils.Logger;

public class TimeDriveTurn extends SequentialCommandGroup {
	
	public TimeDriveTurn(DriveBase drivebase) {
		addRequirements(drivebase);
        addCommands(
			new TimeDrive(5, 1, drivebase),
			new TimeDrive(3, 1, 0.5, drivebase) // turn right
        );
	}


}

	



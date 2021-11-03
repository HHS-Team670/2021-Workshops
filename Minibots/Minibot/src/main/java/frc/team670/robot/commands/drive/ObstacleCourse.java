package frc.team670.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.team670.robot.subsystems.DriveBase;
import frc.team670.robot.commands.drive.ObstacleCourse;
import frc.team670.robot.commands.drive.TimeDrive;

public class ObstacleCourse extends SequentialCommandGroup{
    
    public ObstacleCourse(DriveBase driveBase) {
        addCommands(
        new TimeDrive(1.5, 1, 1, driveBase), 
        new WaitCommand(1),
        new TimeDrive(2.5, 1, 0, driveBase) 
        );
    }
    
}
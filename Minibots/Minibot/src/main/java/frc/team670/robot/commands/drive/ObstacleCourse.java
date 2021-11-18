package frc.team670.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team670.robot.subsystems.DriveBase;

public class ObstacleCourse extends SequentialCommandGroup {
    public ObstacleCourse(DriveBase driveBase) {
        //TimeDrive (seconds, lspeed, rspeed, driveBase)
        //DistanceDrive (inches, lspeed, rspeed, driveBase)
        addCommands( 
        new TimeDrive(10, 0, 1, driveBase), 
        new TimeDrive(12, 1, 0.8, driveBase), 
        new TimeDrive(6, 0.65, 1, driveBase));
    }
}
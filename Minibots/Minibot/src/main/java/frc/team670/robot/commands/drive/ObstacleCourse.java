package frc.team670.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team670.robot.subsystems.DriveBase;

public class ObstacleCourse extends SequentialCommandGroup {
    
    public ObstacleCourse(DriveBase driveBase){
        addCommands(
            new DistanceDrive(3, 1, 1, driveBase),
            //new TimeDrive(seconds, power, driveBase),
            new WaitCommand(1),
            new TimeDrive(2, 1, 0.5, driveBase),
            //new WaitCommand(1),
            new TimeDrive(5, 0.3, 1, driveBase),
            new WaitCommand(1),
            //new DistanceDrive(10, 1, 1, driveBase),
            new WaitCommand(1),
            new DistanceDrive(20, 0.2, 1, driveBase),
            new WaitCommand(1),
            new TimeDrive(5, 0.3, 1, driveBase),
            new WaitCommand(1),
            new DistanceDrive(24, 1, 1, driveBase)

        );
        
    }
}

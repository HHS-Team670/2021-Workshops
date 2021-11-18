package frc.team670.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team670.robot.subsystems.DriveBase;

public class ObstacleCourse extends SequentialCommandGroup {
    public ObstacleCourse(DriveBase driveBase) {
        addCommands( new TimeDrive(3, -0.8, 1, driveBase), new TimeDrive(23, 1, 1, driveBase), new TimeDrive(3, -0.8, 1, driveBase));
    }
}
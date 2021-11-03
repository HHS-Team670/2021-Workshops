package frc.team670.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team670.robot.subsystems.DriveBase;

public class ObstacleCourse extends SequentialCommandGroup {
    public ObstacleCourse(DriveBase driveBase) {
        addCommands(new PIDDistanceDrive(18, driveBase), new TimeDrive(5, 1, 0.5, driveBase), new PIDDistanceDrive(18, driveBase));
    }
}
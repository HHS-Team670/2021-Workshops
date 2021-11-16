package frc.team670.robot.subsystems;
public class ShootAllBalls extends CommandBase {

    int counter = 0;
    PathChoosingIndexer indexer = new PathChoosingIndexer();


    public void execute() {
        indexer.runUpdraw();
        indexer.updateChamberStates();
        counter++;
    }
    public boolean isFinished() {
        if (indexer.getBottomChamber() == indexer.topChamber - counter) {
            return true;
        }
        else {
            return false;
        }
    }

    public void end() {
        indexer.stopUpdraw();
    }
}

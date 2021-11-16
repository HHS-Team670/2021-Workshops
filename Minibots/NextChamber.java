
public class NextChamber extends CommandBase{
    PathChoosingIndexer indexer;
    boolean shooting;
    int originalTopChamber;
    public void initialize() {
        
        indexer = new PathChoosingIndexer();
        shooting = false;
        originalTopChamber = indexer.getTopChamber();
    }

    public void execute() {
        
        originalTopChamber = indexer.getTopChamber();
        indexer.runIndexer(shooting);
    }

    public boolean isFinished() {
        if (indexer.getTopChamber() > originalTopChamber)  {
            return true;
        }
        else {
            return false;
        }
    }

    public void end() {
        shooting = false;
        indexer.stop();
    }
    
}

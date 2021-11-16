public class NextChamber extends CommandBase{
    
    PathChoosingIndexer indexer;
    private int topChamber;

    public NextChamber(PathChoosingIndexer indexer) {
        this.indexer = indexer;
    }

    public void initialize() {
        topChamber = indexer.getTopChamber();
    }

    public void execute() {
        indexer.runIndexer(false);
    }

    public boolean isFinished() {
        return (topChamber == 3 || topChamber == -1 || indexer.getTopChamber() == topChamber + 1);
    }

    public void end() {
        indexer.stop();
    }
}

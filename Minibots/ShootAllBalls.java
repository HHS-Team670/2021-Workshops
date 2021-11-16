public class ShootAllBalls extends CommandBase {

    PathChoosingIndexer indexer;
    private int leftRatio, rightRatio;

    public ShootAllBalls(PathChoosingIndexer indexer, int leftRatio, int rightRatio) {
        this.indexer = indexer;
        this.leftRatio = leftRatio;
        this.rightRatio = rightRatio;
    }

    public void initialize() {
        indexer.setRatio(leftRatio, rightRatio);
        indexer.runUpdraw();
    }

    public void execute() {
        if (indexer.updrawIsUpToSpeed()) {
            indexer.runIndexer(true);
        }
    }

    public boolean isFinished() {
        return (indexer.getTopChamber() == -1);
    }

    public void end() {
        indexer.stop();
        indexer.stopUpdraw();
    }


}

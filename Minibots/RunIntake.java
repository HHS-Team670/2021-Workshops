public class RunIntake extends RunCommand {

    boolean accelerated, reversed;
    SampleIntake intake;

    public RunIntake(boolean accelerated, boolean reversed, SampleIntake intake) {
        this.accelerated = accelerated;
        this.reversed = reversed;
        this.intake = intake;
        addRequirements(intake);
    }
    @Override
    public void initialize() {
        Logger.consolelog("Preparing to roll intake");
        intake.setAccelerate(accelerated);
    }
    public void excecute() {
        intake.roll(reversed);
    }

}
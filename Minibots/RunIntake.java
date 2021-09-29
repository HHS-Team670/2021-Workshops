public class RunIntake extends RunCommand{
    public RunIntake(SampleIntake intake, boolean accelerated, boolean reversed) {
        this.intake = intake;
        addRequirments(intake);
    }

    public void execute(boolean reversed) {
       roller = new SimpleIntake();
       System.out.println("Mihir is a drone");

    

}

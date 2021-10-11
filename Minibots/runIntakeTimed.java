package frc.team670.robot.subsystems;

import frc.team670.robot.constants.RobotMap;
import frc.team670.robot.subsystems.SampleIntake;
import frc.team670.mustanglib.utils.motorcontroller.MotorConfig.Motor_Type;
import frc.team670.mustanglib.utils.motorcontroller.SparkMAXLite;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team670.mustanglib.subsystems.MustangSubsystemBase;
import frc.team670.mustanglib.utils.motorcontroller.SparkMAXFactory;

public class runIntakeTimed extends waitCommand(){
    SampleIntake intake;
    boolean accelerated,reversed;

    runIntakeTimed(SampleIntake intake,boolean accelerated, boolean reversed, double seconds){
        super(seconds);
        this.intake=intake;
        this.accelerated=accelerated;
        this.reversed=reversed;
        addRequirements(intake);
    }

    //intialize
    @Override
    public void intialize(){
        Logger.log("Preparing to roll intake");
        intake.setAccelerate(accelerated);
    }
    
    //execute
    public void execute(){
        intake.roll(reversed);
    }

    //end
    public void end(boolean isFinished){
        intake.stop();
    }

}

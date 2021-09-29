package frc.team670.robot.subsystems;

import frc.team670.robot.constants.RobotMap;
import frc.team670.mustanglib.utils.motorcontroller.MotorConfig.Motor_Type;
import frc.team670.mustanglib.utils.motorcontroller.SparkMAXLite;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team670.mustanglib.subsystems.MustangSubsystemBase;
import frc.team670.mustanglib.utils.motorcontroller.SparkMAXFactory;


/* This is a practice activity to test your knowledge. 
    Try to refrain from referring to the actual Intake class in 2020 Robot.
    Also note that the acceleration is not actually part of the Intake, 
    since it wouldn't be ideal. It is just extra coding practice.
*/   
public class SampleIntake extends MustangSubsystemBase {

    private SparkMAXLite roller;
    private Compressor compressor;
    private Solenoid deployer;
    private boolean isDeployed = false;

    private double INTAKE_PEAK_CURRENT = 35; // Testing
    private int exceededCurrentLimitCount = 0;
    
    private double speed = 0.5; 
    private double ACCELERATE_SPEED = 0.05;
    private boolean isAccelerating = false;
    private boolean isReversed = false;

    public SampleIntake() {
        roller = SparkMAXFactory.buildFactorySparkMAX(RobotMap.INTAKE_ROLLER, Motor_Type.NEO_550);
        roller.setInverted(true);
        roller.setOpenLoopRampRate(1.0);
        compressor = new Compressor(RobotMap.PCMODULE);
        compressor.setClosedLoopControl(true);
        deployer = new Solenoid(RobotMap.PCMODULE, RobotMap.INTAKE_DEPLOYER);
        isAccelerating = false;
    }
    
    public boolean isRolling() {
        return (roller.get() != 0);
}

    public void deploy(boolean isDeployed) {
        this.isDeployed = isDeployed;
        deployer.set(isDeployed);
    }

    public boolean isDeployed() {
        return isDeployed;
    }


    public void setAccelerate(boolean accel){
        isAccelerating = accel;
    }


    public void roll(boolean reversed) {
        if (!reversed) {
            roller.set(speed);
        } else {
            roller.set(speed * -1);
        }
        this.reversed = reversed;
    }

    

    public boolean isJammed(){
        double intakeCurrent = roller.getOutputCurrent();
        if (intakeCurrent > 0.2){
            if (intakeCurrent >= INTAKE_PEAK_CURRENT) {
                exceededCurrentLimitCount++;
            } else {
                exceededCurrentLimitCount = 0;
            }
            if (exceededCurrentLimitCount >= 1){ // 4 consecutive readings higher than peak
                return true;
            }
        }

        return false;

    }

    public void stop() {
        roller.stopMotor();
    }

    /**
     * @return RED if the roller has issues, or the intake isn't deployed but the
     *         pneumatics have issues
     */
    @Override
    public HealthState checkHealth() {
        if (roller == null || isSparkMaxErrored(roller)) {
            return HealthState.RED;
        }
        if (compressor == null || deployer == null) {
            if (isDeployed) {
                // If there's a problem with only pneumatics but the intake is already
                // deployed, we can still roll the intake
                return HealthState.YELLOW;
            }
            // If it isn't deployed and we have a pneumatics issue, we can't do anything
            return HealthState.RED;
        }
        return HealthState.GREEN;
    }

    @Override
    public void mustangPeriodic() { // <-- called repeatedly
        if (isAccelerating && isRolling()) {
            if (speed <= speed - ACCELERATE_SPEED) {
                speed += ACCELERATE_SPEED;  
                roll(reversed);
            } 
        }
    }

}

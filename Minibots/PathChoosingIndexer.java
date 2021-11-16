import java.util.ArrayList;

import frc.team670.mustanglib.dataCollection.sensors.TimeOfFlightSensor;
import frc.team670.robot.utils.Logger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class PathChoosingIndexer extends MustangSubsystemBase {

    private TimeOfFlightSensor[] sensors; // sensor 0 is bottom most, sensor 5 is top most

    private SparkMaxLite frontMotor, backMotor;

    private TalonSRX updraw, topWheel;

    private boolean[] chamberStates;

    private double UPDRAW_SPEED = -0.9;
    private double INDEXER_SPEED = 0.35;
    private static final int UPDRAW_NORMAL_CONTINUOUS_CURRENT_LIMIT = 9;
    private static final int UPDRAW_PEAK_CURRENT_LIMIT = 15;

    private int totalNumBalls;
    private int rightNumBalls, leftNumBalls;
    private int ballsFiredOnLeft, ballsFiredOnRight;
    public int topChamber;

    public HealthState healthState;
    public PathChoosingIndexer() {
        sensors = new TimeOfFlightSensor[6] {new TimeOfFlightSensor(0),
        new TimeOfFlightSensor(1), new TimeOfFlightSensor(2), new TimeOfFlightSensor(3),
        new TimeOfFlightSensor(4), new TimeOfFlightSensor(5)};

        frontMotor = SparkMAXFactory.buildFactorySparkMAX(RobotMap.FRONT_MOTOR, Motor_Type.NEO_550);
        backMotor = SparkMAXFactory.buildFactorySparkMAX(RobotMap.BACK_MOTOR, Motor_Type.NEO_550);
        updraw = TalonSRXFactory.buildFactoryTalonSRX(RobotMap.UPDRAW_SPINNER, false);
        topWheel = TalonSRXFactory.buildFactoryTalonSRX(RobotMap.TOP_WHEEL, false);
        
        for (TalonSRX wheel : new TalonSRX[] {updraw, topWheel})
            wheel.setNeutralMode(NeutralMode.Coast); // free, nothing holding instead of brake

            wheel.configContinuousCurrentLimit(UPDRAW_NORMAL_CONTINUOUS_CURRENT_LIMIT);
            wheel.configPeakCurrentLimit(UPDRAW_PEAK_CURRENT_LIMIT);
            wheel.enableCurrentLimit(true);

            wheel.configVoltageCompSaturation(12); // "full output" will now scale to 12 Volts
            wheel.enableVoltageCompensation(true);
        }

        chamberStates = new boolean[5]; 
    }

    private void pushGameDataToDashboard() {
        NetworkTableInstance instance = NetworkTableInstance.getDefault();
        NetworkTable table = instance.getTable("/SmartDashboard");
        NetworkTableEntry gameData = table.getEntry("Balls");
        gameData.setNumber(totalNumBalls);
    }

    /**
     * Loop through sensors list and update the chamber states.
     * Remember, true means that a ball is detected, and false means that it isn't.
     * Remember to change rightNumBalls, leftNumBalls, and totalNumBalls accordingly.
     * Refer to the ToF documentation!
     */
    public void updateChamberStates() {
        for(int i = 0; i <= sensors.length; i++) {
            if (sensors[i].getDistance()  <=  5) {
                chamberStates[i] = true;
            }
            else {
                chamberStates[i] = false;
            }
        }
    }

    /**
     * Requires a loop.
     * @return The topmost chamber that holds a ball.
     */
    public int getTopChamber() {
        for(int i = sensors.length-3; i >= 0; i--) {
            if (chamberStates[i] = true) {
                topChamber = i;
                return i;
            }
        }
    }

    public int getBottomChamber() {
        for(int i = 0; i >= sensors.length - 3; i++) {
            if (chamberStates[i] = true) {
                return i;
            }
        }
    }

    /**
     * Run the frontMotor and backMotor, as well as updraw depending on the 'shooting' parameter.
     * Refer to SparkMaxLite documentation.
     */
    public void runIndexer(boolean shooting) {
        frontMotor.set(INDEXER_SPEED);
        backMotor.set(-1 * INDEXER_SPEED);
        if (shooting) {
            runUpdraw();

        }
    }

    /**
     * Run updraw and topWheel here. The direction of rotation of topWheel should be determined by the set ratio.
     * Refer to TalonSRX documentation.
     */
    public void runUpdraw() {
        if (ballsFiredOnLeft <= leftNumBalls) {
            updraw.set(UPDRAW_SPEED*-1);
            topWheel.set(UPDRAW_SPEED);
            ballsFiredOnLeft++;
        }
        else if (ballsFiredOnRight <= rightNumBalls) {
            updraw.set(UPDRAW_SPEED);
            topWheel.set(UPDRAW_SPEED*-1);
            ballsFiredOnRight++;
        }

    }

    /**
     * Called before shooting balls. Sets the ratio for shooting. For example, if the ratio was 1, 1
     * and total number of balls was 4, then 2 would be shot from left and right side.
     * Remember to *reassign* leftNumBalls and rightNumBalls.
     * @param left 
     * @param right
     */
    public void setRatio(int left, int right) {
        leftNumBalls = left;
        rightNumBalls = right;
    }

    /**
     * Stop the frontMotor and backMotor.
     * Refer to SparkMaxLite docs.
     */
    public void stop() {
        frontMotor.stop();
        backMotor.stop();
    }

    /**
     * Stops updraw and topWheel. 
     * Refer to TalonSRX docs.
     */
    public void stopUpdraw() {
        updraw.stop();
        topWheel.stop();
    }

    /**
     * Check motor output against UPDRAW_SPEED to determine whether it is running fast enough.
     */
    public boolean updrawIsUpToSpeed() {
        if (updraw.output() == UPDRAW_SPEED) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * optional.
     */
    public void logSensorVals() {
        Logger.consoleLog("LeftNumBalls: %s RightNumBalls: %s, ballsFiredOnLeft: %s ballsFiredOnRight: %s", leftNumBalls, rightNumBalls, ballsFiredOnLeft, ballsFiredOnRight);
        Logger.consolelog("Chamber 1: %s Chamber 2: %s Chamber 3: %s Chamber 4: %s Chamber 5: %s Chamber 6: %s", chamberStates[0], chamberStates[1], chamberStates[2], chamberStates[3], chamberStates[4], chamberStates[5], chamberStates[6]);
        Logger.consolelog("Top Chamber: %d", topChamber);
    }



    @Override
    /**
     * Think about what you would return when motors aren't working, sensors malfunctinoing, etc.
     * @return A health state.
     */
    public MustangSubsystemBase.HealthState checkHealth() {
        if (!updrawIsUpToSpeed()) {
            return MustangSubsystemBase.HealthState.YELLOW;
        }
    }

    @Override
    /**
     * Add methods here that should be called repeatedly.
     */
    public void mustangPeriodic() {
        updateChamberStates();
        topChamber = getTopChamber();
        logSensorVals();
        Logger.consoleLog("Running periodic");
        SmartDashboard.putNumber("Ratio for ball firing: ", leftNumBalls + ":" + rightNumBalls);
        SmartDashboard.putBooleanArray("Chamber States", chamberStates);
    }

    


    
}
import java.util.ArrayList;

import frc.team670.mustanglib.dataCollection.sensors.TimeOfFlightSensor;
import frc.team670.pi.ControlMode;
import frc.team670.robot.utils.Logger;

public class PathChoosingIndexer extends MustangSubsystemBase {

    private TimeOfFlightSensor[] sensors; // sensor 0 is bottom most, sensor 4 is top most

    private SparkMaxLite frontMotor, backMotor;

    private TalonSRX updraw, topWheel;

    private boolean[] chamberStates;
    private boolean shooting; 

    //wheel speeds
    private double UPDRAW_SPEED = -0.9;
    private double INDEXER_SPEED = 0.35;

    private static final int UPDRAW_NORMAL_CONTINUOUS_CURRENT_LIMIT = 9;
    private static final int UPDRAW_PEAK_CURRENT_LIMIT = 15;

    private int totalNumBalls;
    private int rightNumBalls, leftNumBalls;

    public PathChoosingIndexer() {
        sensors = new TimeOfFlightSensor[5] {new TimeOfFlightSensor(0),
        new TimeOfFlightSensor(1), new TimeOfFlightSensor(2), new TimeOfFlightSensor(3),
        new TimeOfFlightSensor(4), new TimeOfFlightSensor(4)};
        //takes in port and motor type: buildFactorySparkMAX
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

        chamberStates = new boolean[4];
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
     * Remember to change rightNumBalls, leftNumBalls, and totalNumBalls accor  `dingly.
     * Refer to the ToF documentation!
     */
    public void updateChamberStates() {
        totalNumBalls = 0;
        for (int i = 0; i < sensorVals.length; i++) {
            chamberStates[i] = sensorVals[i] < RobotConstants.MIN_BALL_DETECTED_WIDTH_INDEXER;
            if (chamberStates[i])
                totalNumBalls++;
        }
    }

    private void updateSensorVals() {
        if (serialport == null) {
            try {
                serialport = new SerialPort(9600, SerialPort.Port.kUSB1);
            } catch (Exception e) {
                Logger.consoleError(e.getMessage());
            }
        }
        if (serialport != null) {
            try {
                String reading = serialport.readString();
                if (reading.length() > 0) {
                    serialParser(reading);
                    for (int i = 0; i < sensorVals.length; i++) {
                        if (sensorVals[i] >= 300) {
                            serialport.reset();
                        }
                    }
                    // logSensorVals();
                }
            } catch (Exception e) {
                serialport = null;
            }
        }
    }

    /**
     * Requires a loop.
     * @return The topmost chamber that holds a ball.
     */
    public int getTopChamber() {
        for (int i = 3 ; i > -1 ; i--) {
            if (chamberStates[i]) {
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
            updraw.set(ControlMode.PercentOutput, UPDRAW_SPEED);
        }
    }

    /**
     * Run updraw and topWheel here. The direction of rotation of topWheel should be determined by the set ratio.
     * Refer to TalonSRX documentation.
     */
    private void runUpdraw() {
        updraw.set(ControlMode.PercentOutput, UPDRAW_SPEED);
        topWheel.set(ControlMode.PercentOutput, UPDRAW_SPEED);
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
        frontMotor.stopMotor();
        backMotor.stopMotor();
    }

    /**
     * Stops updraw and topWheel. 
     * Refer to TalonSRX docs.
     */
    public void stopUpdraw() {
        updraw.set(ControlMode.PercentOutput, 0);
        Object updrawStartTime = null;
    }

    /**
     * Check motor output against UPDRAW_SPEED to determine whether it is running fast enough.
     */
    public boolean updrawIsUpToSpeed() {
        if (updraw.getMotorOutputPercent() == UPDRAW_SPEED){
            return true;
        }
    }

    /**
     * optional.
     */
    public void logSensorVals() {
        Logger.consoleLog("0: %s, 1: %s, 2: %s, 3: %s", sensorVals[0], sensorVals[1], sensorVals[2], sensorVals[3]);
        Logger.consoleLog("Chamber to check: %s", chamberToCheck);
        Logger.consoleLog("Total balls: %s", getTotalNumBalls());
    }



    @Override
    /**
     * Think about what you would return when motors aren't working, sensors malfunctinoing, etc.
     * @return A health state.
     */
    public MustangSubsystemBase.HealthState checkHealth() {
        // TODO Auto-generated method stub
        //return MustangSubsystemBase.HealthState.RED;
        CANError frontError = frontMotor.getLastError();
        CANError backError = backMotor.getLastError();

        boolean isFrontError = isSparkMaxErrored((SparkMAXLite) frontMotor);
        boolean isBackError = isSparkMaxErrored((SparkMAXLite) backMotor);
        // boolean isRotatorError = isSparkMaxErrored(rotator);
        boolean isUpdrawError = isPhoenixControllerErrored(updraw);
        if (isUpdrawError || isFrontError || isBackError) {
            MustangNotifications.reportError("RED Errors: front: %s, back: %s", frontError, backError);
            return HealthState.RED;
        }
    }

    @Override
    /**
     * Add methods here that should be called repeatedly.
     */
    public void mustangPeriodic() {
        updateChamberStates();
        checkHealth();
        updateSensorVals();
        Logger.consoleLog("Running periodic");
        
    }

    


    
}
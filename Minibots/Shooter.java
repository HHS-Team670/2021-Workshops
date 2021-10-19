/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team670.robot.subsystems;

import java.util.List;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team670.robot.constants.RobotMap;
import frc.team670.robot.utils.functions.MathUtils;
import frc.team670.robot.utils.math.interpolable.InterpolatingDouble;
import frc.team670.robot.utils.math.interpolable.InterpolatingTreeMap;
import frc.team670.robot.utils.math.interpolable.LinearRegression;
import frc.team670.robot.utils.motorcontroller.SparkMAXFactory;
import frc.team670.robot.utils.motorcontroller.SparkMAXLite;
import frc.team670.robot.utils.motorcontroller.MotorConfig.Motor_Type;

/**
 * Represents a 2-stage shooter, with 1st stage using a VictorSPX and 2-NEO 2nd
 * stage with SparkMax controllers.
 * 
 * @author ctychen, Pallavi Das
 */
public class Shooter extends MustangSubsystemBase {

  private SparkMAXLite mainController, followerController;
  private List<SparkMAXLite> controllers;

  private CANEncoder stage2_mainEncoder;
  private CANPIDController stage2_mainPIDController;

  private double targetRPM = 2500; // Will change later if we adjust by distance
  private static double DEFAULT_SPEED = 2500;

  private static double MIN_RPM = 2125;
  private static double MAX_RPM = 2725;

  private double speedAdjust = 0; // By default, we don't adjust, but this may get set later

  private static double MAX_SHOT_DISTANCE_METERS = 8.6868; // = 28-29ish feet

  private static final double PULLEY_RATIO = 2; // Need to check this



  // Stage 2 values, as of 2/17 testing
  private static final double V_P = 0.000100;
  private static final double V_I = 0.0;
  private static final double V_D = 0.0;
  private static final double V_FF = 0.000183;
  private static final double RAMP_RATE = 1.0;


  private static final int VELOCITY_SLOT = 0;

  /**
   * constructor 
   */
  public Shooter() {

    // build spark max using buildFactorySparkMaxPair, assign controllers to their fields, get encoder and pidcontrolle
    // from main and assign to fields, set P, I, D, & FF for PIDController
    controllers = SparkMAXFactory.buildFactorySparkMAXPair(RobotMap.SHOOTER_MAIN, RobotMap.SHOOTER_FOLLOWER, false, Motor_Type.NEO);
    mainController = controllers.get(0);
    followerController = controllers.get(1);
    followerController.follow(mainController);
 
    //TODO initialize stage2_mainEncoder and stage2_mainPIDController with mainController
    stage2_mainEncoder = mainController.getEncoder();
    stage2_mainPIDController = mainController.getPIDController();

    
    // TODO set P, I, D, and FF constants on controller

    stage2_mainPIDController.setFF(V_FF);
    stage2_mainPIDController.setI(RAMP_RATE);
    stage2_mainPIDController.setD(VELOCITY_SLOT);

  }

  /**
   * @return stage 2 velocity
   */
  public double getStage2Velocity() {
      // use stage2_mainEncoder
}

  /**
   * runs the shooter
   */
  public void run() {
    // TODO set setpoint using setReference and give it the targetSpeed + adjust and Control type Velocity
    stage2_mainPIDController.setReference(targetRPM);
  }

  /**
   * @param targetRPM sets velocity target for shooter
   */
  public void setVelocityTarget(double targetRPM) {
    
  }

  /**
   * 
   * @return default RPM speed
   */
  public double getDefaultRPM(){
    return this.DEFAULT_SPEED;
  }

  

  /**
   * stops running shooter
   */
  public void stop() {
      // TODO setReference 0 and set Control type dutyCycle
    stage2_mainPIDController.setReference(ControlType.dutyCycle);
  }

  

  public void mustangPeriodic() {
    SmartDashboard.putNumber("Stage 2 speed", getStage2Velocity());
  }

  @Override
  public HealthState checkHealth() {
      // TODO Auto-generated method stub
      return null;
  }

}
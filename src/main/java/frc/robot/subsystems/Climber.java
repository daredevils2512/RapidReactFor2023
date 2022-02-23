package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.utils.Constants;

public class Climber extends NTSubsystem {
  // Motor stuff
  private final TalonSRX m_rightMotor;
  private final TalonSRX m_leftMotor;

  public Climber (){
    super("ClimberSubsystem");

    // Make Motors
    m_rightMotor = new TalonSRX(Constants.climber1ID);
    m_leftMotor = new TalonSRX(Constants.climber2ID);
    m_leftMotor.follow(m_rightMotor);
  }

  /** Sets the speed of the climbing motors
   * @param speed The speed of the motors
   */
  public void setClimbSpeed(double speed){
    m_rightMotor.set(ControlMode.PercentOutput, speed);
    m_logger.fine("setclimbspeed: " + speed);
  }
}

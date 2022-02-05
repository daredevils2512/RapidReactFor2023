package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class IntakeSub extends NTSubsystem {
  // IDs TODO: change to correct values!
  private final int m_intake1ID = 01;
  private final int m_intake2ID = 02;

  // Motor stuff
  private final WPI_TalonSRX m_intake1;
  private final WPI_TalonSRX m_intake2;

  public IntakeSub() {
    super("IntakeSub");

    // Sets IDs for motors
    m_intake1 = new WPI_TalonSRX(m_intake1ID);
      m_intake2 = new WPI_TalonSRX(m_intake2ID);
        
    // Sets up invertions, etc.
    m_intake1.setInverted(false);
    m_intake2.setInverted(true);
    m_intake2.follow(m_intake1);
  }
    
  /** Runs intake motors
  * @param double Speed for intake motors
  */
  public void setIntake(double speed) {
    m_intake1.set(speed);
  }
}
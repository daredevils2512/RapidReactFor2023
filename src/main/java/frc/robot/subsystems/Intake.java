package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.utils.Constants;

public class Intake extends NTSubsystem {

  // Motor stuff
  private final WPI_TalonSRX m_intake1;
  private final WPI_TalonSRX m_intake2;
  // private final WPI_TalonSRX m_intake3;

  // Shifters
  private final DoubleSolenoid m_leftShifter;
  // private final DoubleSolenoid m_rightShifter;

  public Intake() {
    super("IntakeSub");

    // Sets IDs for motors
    m_intake1 = new WPI_TalonSRX(Constants.intake1ID);
    m_intake2 = new WPI_TalonSRX(Constants.intake2ID);
        
    // Sets up inversions, etc.
    m_intake1.setInverted(false);
    m_intake2.setInverted(false);
    m_intake2.follow(m_intake1);

    // Shifters
    m_leftShifter = new DoubleSolenoid(Constants.pneumaticsModuleType, Constants.intakeShifter1ForwardID, Constants.intakeShifter1BackwardID);
    //m_rightShifter = new DoubleSolenoid(Constants.pneumaticsModuleType, Constants.intakeShifter2ForwardID, Constants.intakeShifter2BackwardID);
  }

  /** Sets gears to proper value */
  public void setExtended(boolean wantsExtended) {
    m_leftShifter.set(wantsExtended ? Constants.intakeExtendedValue : Constants.intakeRetractedValue);
    //m_rightShifter.set(wantsExtended ? Constants.intakeExtendedValue : Constants.intakeRetractedValue);
    m_logger.info("set extended: " + wantsExtended);
  }

  /** @return true if shifters are in low gear */
  public boolean getExtended() {
    m_logger.fine("get extended: " + (m_leftShifter.get() == Constants.intakeExtendedValue));
    return m_leftShifter.get() == Constants.intakeExtendedValue;
  }

  /** Toggles the shifters on/off */
  public void toggleExtended() {
    setExtended(!getExtended());
  }
    
  /** Runs intake motors
   * @param speed Speed for intake motors
   */
  public void setIntake(double speed) {
    m_intake1.set(speed);
    m_logger.fine("set intake speed: " + speed);
  }
}
package frc.robot.subsystems;

import java.util.Properties;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class DriveTrainSub extends NTSubsystem {

  // IDs TODO: change to correct values!
  private final int k_frontLeftID = 00;
  private final int k_backLeftID = 01;
  private final int k_frontRightID = 12;
  private final int k_backRightID = 13;
  private final int k_leftEncoderID1 = 0;
  private final int k_leftEncoderID2 = 1;
  private final int k_rightEncoderID1 = 0;
  private final int k_rightEncoderID2 = 1;
  private final double k_rateLimNUM = 0.5;
  private final double k_maxSpeed = 0.5;
  private final double k_maxTurn = 0.5;
  private final int k_leftForwardChannel = 0;
  private final int k_leftBackwardChannel = 0;
  private final int k_rightForwardChannel = 0;
  private final int k_rightBackwardChannel = 0;
  private final PneumaticsModuleType k_pneumaticsModuleType = PneumaticsModuleType.CTREPCM;

  // Motor stuff
  private final WPI_TalonFX m_frontLeft; 
  private final WPI_TalonFX m_backLeft; 
  private final WPI_TalonFX m_frontRight; 
  private final WPI_TalonFX m_backRight;
  private final MotorControllerGroup m_left; 
  private final MotorControllerGroup m_right;
  private final DifferentialDrive m_drive;

  // Network table stuff
  private final NetworkTable m_table; 
  private final NetworkTableEntry m_leftDistanceEntry;
  private final NetworkTableEntry m_rightDistanceEntry;
  private final NetworkTableEntry m_leftEncoderEntry;
  private final NetworkTableEntry m_rightEncoderEntry;
  private final Properties m_properties;
  private final Encoder m_leftEncoder; 
  private final Encoder m_rightEncoder;
  private final double m_gearRatio;
  private final double m_wheelCircumference;
  private final double m_wheelDiameter;
  private final double m_distancePerPulse;
  private final NetworkTableEntry m_getLowGearEntry;
  private final int m_encoderResolution;

  // Shifting
  private final DoubleSolenoid m_leftShifter;
  private final DoubleSolenoid m_rightShifter;

  // Rate limiter
  private final SlewRateLimiter m_rateLim;

  public DriveTrainSub() {
    super("DriveTrainSub");
    m_table = NetworkTableInstance.getDefault().getTable("Drive Train");

    // Motor stuff
    m_frontLeft = new WPI_TalonFX(k_frontLeftID); 
    m_backLeft = new WPI_TalonFX(k_backLeftID);
    m_left = new MotorControllerGroup(m_frontLeft, m_backLeft);
    m_left.setInverted(true);
    m_frontRight = new WPI_TalonFX(k_frontRightID);
    m_backRight = new WPI_TalonFX(k_backRightID);
    m_right = new MotorControllerGroup(m_frontRight, m_backRight);
    m_drive = new DifferentialDrive(m_left, m_right); 

    // Network table stuff
    m_properties = new Properties();
    m_leftEncoder = new Encoder(k_leftEncoderID1, k_leftEncoderID2);
    m_rightEncoder = new Encoder(k_rightEncoderID1, k_rightEncoderID2);
    m_leftEncoderEntry = m_table.getEntry("Left encoder distance"); 
    m_rightEncoderEntry = m_table.getEntry("Right encoder distance");
    m_encoderResolution = Integer.parseInt(m_properties.getProperty("encoderResolution"));
    m_wheelDiameter = Units.inchesToMeters(Double.parseDouble(m_properties.getProperty("wheelDiameter")));
    m_wheelCircumference = Units.inchesToMeters(m_wheelDiameter) * Math.PI;
    m_gearRatio = Double.parseDouble(m_properties.getProperty("gearRatio"));
    m_distancePerPulse = m_wheelCircumference / m_gearRatio / m_encoderResolution;
    m_leftEncoder.setDistancePerPulse(m_distancePerPulse);
    m_leftEncoder.setReverseDirection(true);
    m_rightEncoder.setDistancePerPulse(m_distancePerPulse);
    m_leftDistanceEntry = m_table.getEntry("Left distance entry"); 
    m_rightDistanceEntry = m_table.getEntry("Right distance entry"); 
    m_getLowGearEntry = m_table.getEntry("Low gear entry");

    // Shifting
    m_leftShifter = new DoubleSolenoid(k_pneumaticsModuleType, k_leftForwardChannel, k_leftBackwardChannel);
    m_rightShifter = new DoubleSolenoid(k_pneumaticsModuleType, k_rightForwardChannel, k_rightBackwardChannel);

    // Rate limiter
    m_rateLim = new SlewRateLimiter(k_rateLimNUM);
  }

  /** Runs the arcade drive 
   * @param move Speed for forward/backward movement
   * @param turn Speed for left/right movement
  */
  public void arcadeDrive(double move, double turn) { 
    move = m_rateLim.calculate(move);
    turn = m_rateLim.calculate(turn);
    m_drive.arcadeDrive((move)*k_maxSpeed, (turn)*k_maxTurn);
  }

  /** 
   * @return Left encoder
  */
  public int getLeftEncoder() { 
    return m_leftEncoder.get();
  }

  /** 
   * @return Right encoder
  */
  public int getRightEncoder() { 
    return m_rightEncoder.get();
  }

  /** 
   * @return Left distance
   */
  public double getLeftDistance() { 
    return m_leftEncoder.getDistance();
  }

  /** 
   * @return Left distance
   */
  public double getRightDistance() { 
    return m_rightEncoder.getDistance();
  }

  /** Sets low gear only if it wants to
   * @param wantsLowGear if it wants to set low gear
   */
  public void setLowGear(boolean wantsLowGear) {
    m_leftShifter.set(wantsLowGear ? Value.kForward : Value.kReverse);
    m_rightShifter.set(wantsLowGear ? Value.kForward : Value.kReverse);
  }

  /** @return true if shifter are in low gear */
  public boolean getLowGear() {
    return m_leftShifter.get() == Value.kForward ? true : false;
  }

  public void toggleShifters() {
    setLowGear(!getLowGear());
  }
  
  /** Periodically runs code */
  @Override
  public void periodic() { 
    m_leftEncoderEntry.setNumber(getLeftEncoder());
    m_rightEncoderEntry.setNumber(getRightEncoder());

    m_leftDistanceEntry.setNumber(getLeftDistance());
    m_rightDistanceEntry.setNumber(getRightDistance());

    m_getLowGearEntry.setBoolean(getLowGear());
  }
}
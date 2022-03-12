package frc.robot.subsystems.physical;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NTSubsystem;
import frc.robot.utils.Constants;

public class PhysicalSparkDrivetrain extends NTSubsystem implements Drivetrain {
  // Motor stuff
  private final PWMSparkMax m_frontLeft; 
  private final PWMSparkMax m_backLeft; 
  private final PWMSparkMax m_frontRight; 
  private final PWMSparkMax m_backRight;
  private final MotorControllerGroup m_left;
  private final MotorControllerGroup m_right;
  private final DifferentialDrive m_drive;

  // Network table stuff
  private final NetworkTable m_table; 
  private final NetworkTableEntry m_leftDistanceEntry;
  private final NetworkTableEntry m_rightDistanceEntry;
  private final NetworkTableEntry m_leftEncoderEntry;
  private final NetworkTableEntry m_rightEncoderEntry;
  private final NetworkTableEntry m_getLowGearEntry;
  private final Encoder m_leftEncoder; 
  private final Encoder m_rightEncoder;

  // Shifting
  private final DoubleSolenoid m_leftShifter;
  private final DoubleSolenoid m_rightShifter;

  // Rate limiter
  private final SlewRateLimiter m_rateLim;
  private final SlewRateLimiter m_rateLimTurn;


  public PhysicalSparkDrivetrain() {
    super("Drivetrain");
    m_table = NetworkTableInstance.getDefault().getTable("Drive Train");

    // Motor stuff
    m_frontLeft = new PWMSparkMax(Constants.drivetrainLeftID1); 
    m_backLeft = new PWMSparkMax(Constants.drivetrainLeftID2);
    m_left = new MotorControllerGroup(m_frontLeft, m_backLeft);
    m_frontLeft.setInverted(true);
    m_frontRight = new PWMSparkMax(Constants.drivetrainRightID1);
    m_backRight = new PWMSparkMax(Constants.drivetrainRightID2);
    m_right = new MotorControllerGroup(m_frontRight, m_backRight);
    m_drive = new DifferentialDrive(m_left, m_right); 

    // Network table stuff
    m_leftEncoder = new Encoder(Constants.drivetrainLeftEncoderChannelA, Constants.drivetrainLeftEncoderChannelB);
    m_rightEncoder = new Encoder(Constants.drivetrainRightEncoderChannelA, Constants.drivetrainRightEncoderChannelB);
    m_leftEncoderEntry = m_table.getEntry("Left encoder distance"); 
    m_rightEncoderEntry = m_table.getEntry("Right encoder distance");
    m_leftEncoder.setDistancePerPulse(Constants.drivetrainDistancePerPulse);
    m_leftEncoder.setReverseDirection(true);
    m_rightEncoder.setDistancePerPulse(Constants.drivetrainDistancePerPulse);
    m_leftDistanceEntry = m_table.getEntry("Left distance entry"); 
    m_rightDistanceEntry = m_table.getEntry("Right distance entry"); 
    m_getLowGearEntry = m_table.getEntry("Low gear entry");

    // Shifting
    m_leftShifter = new DoubleSolenoid(Constants.pneumaticsModuleType, Constants.drivetrainLeftForwardChannel, Constants.drivetrainLeftBackwardChannel);
    m_rightShifter = new DoubleSolenoid(Constants.pneumaticsModuleType, Constants.drivetrainRightForwardChannel, Constants.drivetrainRightBackwardChannel);

    // Rate limiter
    m_rateLim = new SlewRateLimiter(Constants.drivetrainRateLimNUM);
    m_rateLimTurn = new SlewRateLimiter(Constants.drivetrainRateLimNUM);
  }

  @Override
  public void arcadeDrive(double move, double turn) {
    move = m_rateLim.calculate(move);
    turn = m_rateLimTurn.calculate(turn);
    m_drive.arcadeDrive((move)*Constants.drivetrainMaxSpeed, (turn)*Constants.drivetrainMaxTurn);
    
  }

  @Override
  public void setLowGear(boolean wantsLowGear) {
    m_leftShifter.set(wantsLowGear ? Constants.drivetrainLowGearValue : Constants.drivetrainHighGearValue);
    m_rightShifter.set(wantsLowGear ? Constants.drivetrainLowGearValue : Constants.drivetrainHighGearValue);
    m_logger.fine("set low gear: " + wantsLowGear);
    
  }

  @Override
  public void toggleShifters() {
    setLowGear(!getLowGear());
    
  }

  @Override
  public double getRightDistance() {
    return m_rightEncoder.getDistance();
  }

  @Override
  public double getLeftDistance() {
    return m_leftEncoder.getDistance();
  }

  @Override
  public boolean getLowGear() {
    m_logger.fine("get low gear: " + (m_leftShifter.get() == Constants.drivetrainLowGearValue));
    return m_leftShifter.get() == Constants.drivetrainLowGearValue;
  }
  
  @Override
  public void periodic() { 
    m_leftEncoderEntry.setNumber(getLeftEncoder());
    m_rightEncoderEntry.setNumber(getRightEncoder());

    m_leftDistanceEntry.setNumber(getLeftDistance());
    m_rightDistanceEntry.setNumber(getRightDistance());

    m_getLowGearEntry.setBoolean(getLowGear());
  }

  @Override
  public int getLeftEncoder() {
    return m_leftEncoder.get();
  }

  @Override
  public int getRightEncoder() {
    return m_rightEncoder.get();
  }

  @Override
  public double getDistance() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void aimLeft(double speed) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void aimRight(double speed) {
    // TODO Auto-generated method stub
    
  }
  
}

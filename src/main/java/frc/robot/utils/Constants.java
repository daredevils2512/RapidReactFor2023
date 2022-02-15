package frc.robot.utils;

import java.util.Properties;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  private static Properties m_properties;
  
  // TODO set auto speed
  public static final double DRIVE_AUTO_SPEED = 0.5;
  // TODO change distance
  public static final double AUTO_DRIVE_BACK_DISTANCE = 150;

  // Drivetrain
  public static double drivetrainRateLimNUM = 0.5;
  public static double drivetrainMaxSpeed = 0.5;
  public static double drivetrainMaxTurn = 0.5;
  public static int drivetrainLeftForwardChannel = 0;
  public static int drivetrainLeftBackwardChannel = 0;
  public static int drivetrainRightForwardChannel = 0;
  public static int drivetrainRightBackwardChannel = 0;
  public static double drivetrainGearRatio = Double.parseDouble(m_properties.getProperty("gearRatio"));
  public static int drivetrainEncoderResolution = Integer.parseInt(m_properties.getProperty("encoderResolution"));
  public static double drivetrainWheelDiameter = Units.inchesToMeters(Double.parseDouble(m_properties.getProperty("wheelDiameter")));
  public static double drivetrainWheelCircumference =  Units.inchesToMeters(drivetrainWheelDiameter) * Math.PI;
  public static double drivetrainDistancePerPulse = drivetrainWheelCircumference / drivetrainGearRatio / drivetrainEncoderResolution;

  // Intake
  public static int intakeShifter1ForwardID = 01;
  public static int intakeShifter1BackwardID = 01;
  public static int intakeShifter2ForwardID = 02;
  public static int intakeShifter2BackwardID = 02;

  // Shooter
  public static int shooterEncoderID1 = 1;
  public static int shooterEncoderID2 = 2;
  public static double shooterRateLimNUM = 0.5;
  public static int shooterForwardChannel = 0;
  public static int shooterBackwardChannel = 1;

  // Motor IDs
  public static int drivetrainLeft1ID = 0;
  public static int drivetrainLeft2ID = 1;
  public static int drivetrainRight1ID = 2;
  public static int drivetrainRight2ID = 3;
  public static int intake1ID = 4;
  public static int intake2ID = 5;
  public static int magID = 6;
  public static int climber1ID = 7;
  public static int climber2ID = 8;
  public static int shooterID = 9;

  // Encoder IDs
  public static int drivetrainLeftEncoderID1 = 0;
  public static int drivetrainLeftEncoderID2 = 1;
  public static int drivetrainRightEncoderID1 = 0;
  public static int drivetrainRightEncoderID2 = 1;

  // Other stuffs
  public static PneumaticsModuleType CTREPCM_PneumaticsModuleType = PneumaticsModuleType.CTREPCM;
  public static Value value_kforward = Value.kForward;
  public static Value value_kreverse = Value.kReverse;

  private Constants(){

  }
}

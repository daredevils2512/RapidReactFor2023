// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Properties;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrainSub extends SubsystemBase {
  private final double m_maxSpeed = 0.5;
  private final double m_maxTurn = 0.5;

  private final SlewRateLimiter m_rateLim;

  // IDs
  private final int frontLeftID = 13;
  private final int backLeftID = 12;
  private final int frontRightID = 10;
  private final int backRightID = 11;
  private final int leftEncoderID1 = 0;
  private final int leftEncoderID2 = 1;
  private final int rightEncoderID1 = 0;
  private final int rightEncoderID2 = 1;

  // Motor stuff
  private final WPI_TalonSRX m_frontLeft; 
  private final WPI_TalonSRX m_backLeft; 
  private final WPI_TalonSRX m_frontRight; 
  private final WPI_TalonSRX m_backRight;
  private final MotorControllerGroup m_left; 
  private final MotorControllerGroup m_right;
  private final DifferentialDrive m_drive;

  // Network table stuff
  private final NetworkTable m_table; 
  private final Encoder m_leftEncoder; 
  private final Encoder m_rightEncoder;
  private final NetworkTableEntry m_leftEncoderEntry;
  private final NetworkTableEntry m_rightEncoderEntry;
  private final double m_gearRatio;
  private final double m_wheelCircumference;
  private final double m_wheelDiameter;
  private final int m_encoderResolution;
  private final double m_distancePerPulse;
  private final NetworkTableEntry m_leftDistanceEntry;
  private final NetworkTableEntry m_rightDistanceEntry;
  private final NetworkTableEntry m_speed;
  private final Properties m_properties;

  public DriveTrainSub() {
    m_table = NetworkTableInstance.getDefault().getTable("Drive Train");
    m_properties = new Properties();

    // Motor stuff
    m_frontLeft = new WPI_TalonSRX(frontLeftID); 
    m_backLeft = new WPI_TalonSRX(backLeftID);
    m_left = new MotorControllerGroup(m_frontLeft, m_backLeft);
    m_left.setInverted(true);
    m_frontRight = new WPI_TalonSRX(frontRightID);
    m_backRight = new WPI_TalonSRX(backRightID);
    m_right = new MotorControllerGroup(m_frontRight, m_backRight);
    m_drive = new DifferentialDrive(m_left, m_right); 

    // Network table stuff
    m_leftEncoder = new Encoder(leftEncoderID1, leftEncoderID2);
    m_rightEncoder = new Encoder(rightEncoderID1, rightEncoderID2);
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

    m_rateLim = new SlewRateLimiter(0.4);
    m_speed = NetworkTableInstance.getDefault().getTable("Test").getEntry("Speed");
  }

  // arcade drive for driving
  public void arcadeDrive(double move, double turn) { 
    move = m_rateLim.calculate(m_speed.getDouble(0));
    turn = m_rateLim.calculate(m_speed.getDouble(0));
    m_drive.arcadeDrive((move)*m_maxSpeed, (turn)*m_maxTurn);
  }

  /** left encoder retrive value 
   * @return returns left encoder value
  */
  public int getLeftEncoder() { 
    return m_leftEncoder.get();
  }

  /** right encoder retrive value 
   * @return rernts right encoder value
  */
  public int getRightEncoder() { 
    return m_rightEncoder.get();
  }

  /** left distance retrive value
   * @return left distance value
   */
  public double getLeftDistance() { 
    return m_leftEncoder.getDistance();
  }

  /** right distance retrive value
   * @return right distance value
   */
  public double getRightDistance() { 
    return m_rightEncoder.getDistance();
  }
  
  // set encoders periodically
  @Override
  public void periodic() { 
    m_leftEncoderEntry.setNumber(getLeftEncoder());
    m_rightEncoderEntry.setNumber(getRightEncoder());

    m_leftDistanceEntry.setNumber(getLeftDistance());
    m_rightDistanceEntry.setNumber(getRightDistance());
  }
}
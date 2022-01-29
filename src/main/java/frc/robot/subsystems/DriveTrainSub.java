// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class DriveTrainSub extends PropertiesSubsystem {
  private final double m_maxSpeed = 0.5;
  private final double m_maxTurn = 0.5;

  private final int frontLeftID = 13;
  private final int backLeftID = 12;
  private final int frontRightID = 10;
  private final int backRightID = 11;

  private final WPI_TalonSRX m_frontLeft; // Motor stuff
  private final WPI_TalonSRX m_backLeft; 
  private final WPI_TalonSRX m_frontRight; 
  private final WPI_TalonSRX m_backRight;
  private final MotorControllerGroup m_left; 
  private final MotorControllerGroup m_right;
  private final DifferentialDrive m_drive;

  private final Encoder m_leftEncoder; // Network table stuff
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

  public DriveTrainSub() {
    super("DriveTrainSub");

    m_frontLeft = new WPI_TalonSRX(frontLeftID); // Motor stuff
    m_backLeft = new WPI_TalonSRX(backLeftID);
    m_left = new MotorControllerGroup(m_frontLeft, m_backLeft);
    m_left.setInverted(true);
    m_frontRight = new WPI_TalonSRX(frontRightID);
    m_backRight = new WPI_TalonSRX(backRightID);
    m_right = new MotorControllerGroup(m_frontRight, m_backRight);
    m_drive = new DifferentialDrive(m_left, m_right); 

    m_leftEncoder = new Encoder(0, 1);
    m_rightEncoder = new Encoder(0, 1);
    m_leftEncoderEntry = m_table.getEntry("Left encoder distance"); // Network table stuff
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
  }

  public void arcadeDrive(double move, double turn) { // arcade drive for driving
    m_drive.arcadeDrive((move)*m_maxSpeed, (turn)*m_maxTurn);
  }

  public int getLeftEncoder() { // left encoder retrive value
    return m_leftEncoder.get();
  }

  public int getRightEncoder() { // right encoder retrive value
    return m_rightEncoder.get();
  }

  public double getLeftDistance() { // left distance retrive value
    return m_leftEncoder.getDistance();
  }

  public double getRightDistance() { // left distance retrive value
    return m_rightEncoder.getDistance();
  }
  
  @Override
  public void periodic() { // set encoders periodically
    m_leftEncoderEntry.setNumber(getLeftEncoder());
    m_rightEncoderEntry.setNumber(getRightEncoder());

    m_leftDistanceEntry.setNumber(getLeftDistance());
    m_rightDistanceEntry.setNumber(getRightDistance());
  }
}
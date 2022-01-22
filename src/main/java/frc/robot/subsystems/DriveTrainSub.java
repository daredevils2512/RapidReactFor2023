// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.RobotContainer;

// import com.ctre.phoenix.motorcontrol.InvertType;

public class DriveTrainSub extends SubsystemBase {
  // private WPI_TalonSRX fr, br, fl, bl;
  private DifferentialDrive m_drive;

  double maxSpeed = 1;
  double maxTurn = 1;


  /** Creates a new ExampleSubsystem. */
  public DriveTrainSub() {
    MotorController m_frontLeft = new WPI_TalonSRX(13);
    MotorController m_backLeft = new WPI_TalonSRX(12);
    MotorControllerGroup m_left = new MotorControllerGroup(m_frontLeft, m_backLeft);

    MotorController m_frontRight = new WPI_TalonSRX(10);
    MotorController m_backRight = new WPI_TalonSRX(11);
    MotorControllerGroup m_right = new MotorControllerGroup(m_frontRight, m_backRight);
    
    m_left.setInverted(true);

    m_drive = new DifferentialDrive(m_left, m_right);
  }

  public void arcadeDrive(double move, double turn) {
    m_drive.arcadeDrive((move)*maxSpeed, (turn)*maxTurn);
  }
}

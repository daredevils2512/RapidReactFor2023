// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSub;

/** An example command that uses an example subsystem. */
public class DriveTrainCommand extends CommandBase {
  
  private DriveTrainSub m_drivetrain;
  private DoubleSupplier m_forward, m_reverse;

  public DriveTrainCommand(DriveTrainSub drivetrain, DoubleSupplier forward, DoubleSupplier reverse) {
    m_drivetrain = drivetrain;
    m_forward = forward;
    m_reverse = reverse;

    addRequirements(m_drivetrain);
  }

  @Override
  public void execute() {
    // m_drivetrain.arcadeDrive(RobotContainer.getReverse(), RobotContainer.getForward(), RobotContainer.getTurn());
    m_drivetrain.arcadeDrive(m_forward.getAsDouble(), m_reverse.getAsDouble());
  }

  @Override
  public boolean isFinished() {
    return false;
  }

}

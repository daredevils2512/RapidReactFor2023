// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.logging.Level;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NTSubsystem extends LoggingSubsystem {

  protected NetworkTable m_table;
  /** Creates a new network table.
   * @param name sets name of table
   * @param logLevel set level for subsystem to display logs at (default: WARNING)
  */
  public NTSubsystem(String tableName, Level logLevel) {
    super(tableName, logLevel);
    m_table = NetworkTableInstance.getDefault().getTable(tableName);
  }
  
  /** Creates a new network table.
   * @param name sets name of table
   * @param logLevel set level for subsystem to display logs at (default: WARNING)
  */
  public NTSubsystem(String tableName) {
    super(tableName);
    m_table = NetworkTableInstance.getDefault().getTable(tableName);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

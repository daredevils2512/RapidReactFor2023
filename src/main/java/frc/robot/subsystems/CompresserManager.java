package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.utils.Constants;
import edu.wpi.first.wpilibj.Compressor;

public interface CompresserManager {
  public void setClosedLoopControl(boolean wantsClosedLoopControl);
  public boolean getClosedLoopControl();
  public void toggleCompressor();
}
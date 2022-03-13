package frc.robot.subsystems.physical;

import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.subsystems.CompresserManager;
import frc.robot.subsystems.NTSubsystem;
import frc.robot.utils.Constants;
import edu.wpi.first.wpilibj.Compressor;

public class PhysicalCompressor extends NTSubsystem implements CompresserManager {
  private final NetworkTableEntry m_isRunningEntry;
  private final NetworkTableEntry m_closedLoopControlEntry;

  private Compressor m_compressor = new Compressor(Constants.PNEUMATICS_MODULE_TYPE);
 
  public PhysicalCompressor() {
    super("Compressor");
    m_isRunningEntry = m_table.getEntry("Is running");
    m_closedLoopControlEntry = m_table.getEntry("closed loop control");
  }
    
  public void periodic() {}
      
  public void setClosedLoopControl(boolean wantsClosedLoopControl) {
    // m_compressor.setClosedLoopControl(wantsClosedLoopControl);
    if (wantsClosedLoopControl)  m_compressor.enableDigital(); else m_compressor.disable();
    m_logger.fine("Compressor closed loop control: " + getClosedLoopControl());
  }
    
  public boolean getClosedLoopControl() {
    return m_compressor.enabled();
  }

  public void toggleCompressor() {
    if (getClosedLoopControl())  m_compressor.disable() ; else m_compressor.enableDigital();
  }
}
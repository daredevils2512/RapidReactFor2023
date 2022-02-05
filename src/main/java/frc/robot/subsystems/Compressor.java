package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
// import edu.wpi.first.wpilibj.Compressor;

public class Compressor  extends NTSubsystem {
    private final NetworkTable m_networkTable;
    private final NetworkTableEntry m_isRunningEntry;
    private final NetworkTableEntry m_closedLoopControlEntry;

    private Compressor m_compressor = new Compressor();
 
    public Compressor() {
        super("Compressor");
        m_networkTable = NetworkTableInstance.getDefault().getTable(getName());
        m_isRunningEntry = m_networkTable.getEntry("Is running");
        m_closedLoopControlEntry = m_networkTable.getEntry("closed loop contorl");

      
    
    }
    
    public void periodic() {
      }
    
      
    public void setClosedLoopControl(boolean wantsClosedLoopControl) {
        m_compressor.setClosedLoopControl(wantsClosedLoopControl);
        m_logger.fine("Compressor closed loop control: " + getClosedLoopControl());
      }
    
      
    public boolean getClosedLoopControl() {
        return m_compressor.getClosedLoopControl();
      }
      
      
    public void toggleCompressor() {
        m_compressor.setClosedLoopControl(!getClosedLoopControl());
      }
    
}

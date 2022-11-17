package frc.robot.subsystems.dummy;

import frc.robot.subsystems.interfaces.CompressorManager;

public class DummyCompressor implements CompressorManager {

  @Override
  public void setClosedLoopControl(boolean wantsClosedLoopControl) { }

  @Override
  public boolean getClosedLoopControl() { return false; }

  @Override
  public void toggleCompressor() { }
  
}

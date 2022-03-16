package frc.robot.subsystems.interfaces;

public interface CompresserManager {
  public void setClosedLoopControl(boolean wantsClosedLoopControl);
  public boolean getClosedLoopControl();
  public void toggleCompressor();
}
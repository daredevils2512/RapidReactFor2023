package frc.robot.subsystems;

public interface CompresserManager {
  public void setClosedLoopControl(boolean wantsClosedLoopControl);
  public boolean getClosedLoopControl();
  public void toggleCompressor();
}
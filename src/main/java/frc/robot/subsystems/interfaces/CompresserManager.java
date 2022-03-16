package frc.robot.subsystems.interfaces;

public interface CompresserManager {
  /** Enables closed loop control
   * @param wantsClosedLoopControl Whether to enable closed loop control
   */
  public void setClosedLoopControl(boolean wantsClosedLoopControl);

  /** @return If closed loop control is enabled */
  public boolean getClosedLoopControl();

  /** Toggles the compressor */
  public void toggleCompressor();
}
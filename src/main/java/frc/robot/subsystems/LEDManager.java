package frc.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;

public interface LEDManager {
  /** @return The color value of the LEDs */
  public Color getColor();

  /** Sets the color values of the LEDs */
  public void setColor(double saturation);

  /** Toggles the LEDs */
  public void toggleLEDs();

  /** Enables / Disables the LEDs
   * @param wantsEnabled whether to enable the LEDs
   */
  public void setEnabled(boolean wantsEnabled);
}

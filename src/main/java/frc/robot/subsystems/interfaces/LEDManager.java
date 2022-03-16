package frc.robot.subsystems.interfaces;

import edu.wpi.first.wpilibj.util.Color;

public interface LEDManager {
  /** @return The color value of the LEDs */
  public Color getColor();

  /** Sets the color values of the LEDs
   * @param saturation The saturation for the 
   */
  public void setColor(double saturation, int hue, int value);

  /** Toggles the LEDs */
  public void toggleLEDs();

  /** Enables / Disables the LEDs
   * @param wantsEnabled whether to enable the LEDs
   */
  public void setEnabled(boolean wantsEnabled);

  /** Sets the new hue value for HSV
   * @param newHue The new hue value
   */
  public void setHue(int newHue);

  /** Sets the new value for HSV
   * @param newValue The new value
   */
  public void setValue(int newValue);
}

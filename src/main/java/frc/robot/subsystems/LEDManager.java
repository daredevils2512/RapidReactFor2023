package frc.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;

public interface LEDManager {
  public Color getColor();
  public void setColor(double saturation);
  public void toggleLEDs();
}

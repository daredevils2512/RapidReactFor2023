package frc.robot.subsystems.dummy;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.subsystems.LEDManager;

public class DummyLEDManager implements LEDManager {

  @Override
  public Color getColor() { return null; }

  @Override
  public void setColor(double saturation) { }

  @Override
  public void toggleLEDs() { }
  
}

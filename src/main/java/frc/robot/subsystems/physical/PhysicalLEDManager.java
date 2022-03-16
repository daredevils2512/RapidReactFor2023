package frc.robot.subsystems.physical;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.subsystems.LEDManager;
import frc.robot.subsystems.interfaces.NTSubsystem;
import frc.robot.utils.Constants;

public class PhysicalLEDManager extends NTSubsystem implements LEDManager {
  // LED stuffs
  private final AddressableLED m_LED;
  private final AddressableLEDBuffer m_LEDBuffer;

  // Network table
  private final NetworkTable m_table;
  private final NetworkTableEntry m_LEDColor;

  // Time
  private double startTime;

  // Enabled
  private boolean enabled;

  public PhysicalLEDManager() {
    super("LEDManager");
    
    // Network table
    m_table = NetworkTableInstance.getDefault().getTable("LEDs");
    m_LEDColor = m_table.getEntry("LED color");

    // LED
    m_LED = new AddressableLED(Constants.LED_PORT);
    m_LED.setLength(Constants.LED_LENGTH);

    // LED Buffer
    m_LEDBuffer = new AddressableLEDBuffer(Constants.LED_LENGTH);

    // Time
    startTime = Timer.getFPGATimestamp();

    // Enabled
    enabled = false;
  }
  
  /** Periodically runs code */
  @Override
  public void periodic() {
    if (enabled) {
      setColor(Math.cos(Timer.getFPGATimestamp() - startTime) / 2 + 0.5);
      m_LEDColor.setValue(getColor());
      m_LED.setData(m_LEDBuffer);
    }
  }

  @Override
  /** @return The color value of the LEDs */
  public Color getColor() {
    return m_LEDBuffer.getLED(1);
  }

  @Override
  /** Sets the color values of the LEDs */
  public void setColor(double saturation) {
    for (int i = 0; i <= Constants.LED_LENGTH; i++) { 
      m_LEDBuffer.setRGB(i, 255, 0, 0);

      Constants.LED_S = Constants.LED_MIN_S + (int)((Constants.LED_MAX_S - Constants.LED_MIN_S) * saturation);
      
      m_LEDBuffer.setHSV(i, 180, Constants.LED_S, 255);
    }
  }

  @Override
  /** Toggles the LEDs */
  public void toggleLEDs() {
    setEnabled(!enabled);
  }

  @Override
  /** Enables / Disables the LEDs
   * @param wantsEnabled whether to enable the LEDs
   */
  public void setEnabled(boolean wantsEnabled) {
    if (wantsEnabled) {
      enabled = true;
      m_LED.start();
    } else {
      enabled = false;
      m_LED.stop();
    }
  }
  
}

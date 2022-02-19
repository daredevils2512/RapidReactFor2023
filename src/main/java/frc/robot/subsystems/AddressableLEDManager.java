package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.utils.Constants;

public class AddressableLEDManager extends NTSubsystem {
  // LED stuffs
  private final AddressableLED m_LED;
  private final AddressableLEDBuffer m_LEDBuffer;

  // Network table
  private final NetworkTable m_table;
  private final NetworkTableEntry m_LEDColor;

  // Math
  private int sMin = 0;
  private int sMax = 255;

  public AddressableLEDManager() {
    super("AddressableLEDManager");

    // Network table
    m_table = NetworkTableInstance.getDefault().getTable("LEDs");
    m_LEDColor = m_table.getEntry("LED color");

    // LED
    m_LED = new AddressableLED(Constants.LEDPort);
    m_LED.setLength(Constants.LEDLength);

    // LED Buffer
    m_LEDBuffer = new AddressableLEDBuffer(Constants.LEDLength);

    // Starts the LEDs
    m_LED.start();
  }
  
  /** Periodically runs code */
  @Override
  public void periodic() {
    setColor(Math.sin(Timer.getFPGATimestamp()) / 2 + 0.5);
    m_LEDColor.setValue(getColor());
    m_LED.setData(m_LEDBuffer);
  }

  /** @return The color value of the LEDs */
  public Color getColor() {
    return m_LEDBuffer.getLED(1);
  }

  /** Sets the color values of the LEDs */
  public void setColor(double saturation) {
    for (int i = 0; i <= Constants.LEDLength; i++) { 
      m_LEDBuffer.setRGB(i, 255, 0, 0);

      Constants.LED_S = sMin + (int)((sMax - sMin) * saturation);
      
      m_LEDBuffer.setHSV(i, 180, Constants.LED_S, 255);
    }
  }
}
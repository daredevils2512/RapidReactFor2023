package frc.robot.io;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class XboxController {
  // Joystick
  private final Joystick m_controller;

  // Axis IDs
  private final int k_xAxisLeftID = 0;
  private final int k_yAxisLeftID = 1;
  private final int k_xAxisRightID = 4;
  private final int k_yAxisRightID = 5;
  private final int k_leftTrigger = 2;
  private final int k_rightTrigger = 3;

  // Buttons 
  public final Button aButton;
  public final Button bButton;
  public final Button xButton;
  public final Button yButton;
  public final Button leftBumper;
  public final Button rightBumper;
  public final Button backArrow;
  public final Button startArrow;
  public final Button leftStickButton;
  public final Button rightStickButton;
  
  public XboxController(int port) {
    // Joystick 
    m_controller = new Joystick(port);

    // Buttons
    aButton = new JoystickButton(m_controller, 1);
    bButton = new JoystickButton(m_controller, 2);
    xButton = new JoystickButton(m_controller, 3);
    yButton = new JoystickButton(m_controller, 4);
    leftBumper = new JoystickButton(m_controller, 5);
    rightBumper = new JoystickButton(m_controller, 6);
    backArrow = new JoystickButton(m_controller, 7);
    startArrow = new JoystickButton(m_controller, 8);
    leftStickButton = new JoystickButton(m_controller, 9);
    rightStickButton = new JoystickButton(m_controller, 10);
  
  }

  /** @return XAxisLeft */
  public double getXAxisLeft() {
    return m_controller.getRawAxis(k_xAxisLeftID);
  }

  /** @return YAxisLeft */
  public double getYAxisLeft() {
    return m_controller.getRawAxis(k_yAxisLeftID);
  }

  /** @return XAxisRight */
  public double getXAxisRight() {
    return m_controller.getRawAxis(k_xAxisRightID);
  }

  /** @return YAxisRight */
  public double getYAxisRight() {
    return m_controller.getRawAxis(k_yAxisRightID);
  }

  /** @return Left Trigger */
  public double getLeftTrigger() {
    return m_controller.getRawAxis(k_leftTrigger);
  }

  /** @return Right Trigger */
  public double getRightTrigger() {
    return m_controller.getRawAxis(k_rightTrigger);
  }
}
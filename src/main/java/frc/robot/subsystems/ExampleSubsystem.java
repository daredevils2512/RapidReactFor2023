// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.logging.Level;

import frc.robot.io.NTButton;

public class ExampleSubsystem extends PropertiesSubsystem {

  public int examplePort;
  public double exampleValue;
  public boolean exampleToggle;

  public NTButton toggleButton;

  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem() {
    super("ExampleSubsystem"); //[deploy-directory]/ExampleSubsystem.properties
    examplePort = getIntProperty("examplePort", -1);
    exampleToggle = getBooleanProperty("exampleToggle");

    //creates error
    exampleValue = getDoubleProperty("exampleValue");

    toggleButton = new NTButton(this::toggle, name + "." + "exampleToggle", m_table);

    toggleButton.runConcurrently();

  }

  public void toggle() {
    exampleToggle = !exampleToggle;
    m_table.getEntry("exampleToggle").setBoolean(exampleToggle);
    setProperty("exampleToggle", Boolean.toString(exampleToggle));
    m_logger.log(Level.INFO, name + ".toggle() ran sucessfully!");
  }

  @Override
  public void periodic() {

    //toggleButton.update();

    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

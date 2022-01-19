// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import frc.robot.utils.PropertyUtils;

public abstract class PropertiesSubsystem extends NTSubsystem {

  private Properties m_properties;

  public PropertiesSubsystem(String name) {
    super(name);
    m_properties = new Properties();
    try {
      m_properties = PropertyUtils.loadProperties(name);
    } catch (IOException e) {
      m_logger.log(Level.SEVERE, "Could not load properties for " + name, e);
      m_logger.log(Level.INFO, "Creating Properties file for " + name);
    }
  }

  /**
   * Save all Properties to disc
   */
  public void saveProperties() {
    try {
      PropertyUtils.saveProperties(m_properties, name, "Properties for " + name);
    } catch (IOException e) {
      m_logger.log(Level.SEVERE, "Could not save properties for " + name, e);
    }
  }

  public String getProperty(String key, String defaultValue) {
    String value;
    value = m_properties.getProperty(key);
    if (value == null) {
      m_logger.log(Level.SEVERE, name + "." + key + " not found");
      value = defaultValue;
    } else {
      m_logger.log(Level.INFO, name + "." + key + " loaded as: " + value);
    }
    return value;
  }

  public String getStringProperty(String key, String defaultValue) {
    String out = getProperty(key, defaultValue);
    m_table.getEntry(key).setString(out);
    return out;
  }

  public void setProperty(String key, String value) {
    m_properties.setProperty(key, value);
    m_logger.log(Level.FINE, name + "." + key + " set to " + value);
  }

  public double getDoubleProperty(String key, double defaultValue) {
    double out = defaultValue;
    try {
      out = Double.parseDouble(getProperty(key, "" + defaultValue));
    } catch (NumberFormatException e) {
      m_logger.log(Level.WARNING, name + "." + key + " cannot be read", e);
    }
    m_logger.log(Level.INFO, name + "." + key + " loaded as: " + out);
    m_table.getEntry(key).setDouble(out);
    return out;
  }

  public double getDoubleProperty(String key) {
    return getDoubleProperty(key, 0);
  }

  public int getIntProperty(String key, int defaultValue) {
    int out = defaultValue;
    try {
      out = Integer.parseInt(getProperty(key, "" + defaultValue));
    } catch (NumberFormatException e) {
      m_logger.log(Level.WARNING, name + "." + key + " cannot be read", e);
    }
    m_table.getEntry(key).setNumber(out);
    return out;  
  }

  public int getIntProperty(String key) {
    return getIntProperty(key, 0);
  }

  public boolean getBooleanProperty(String key, boolean defaultValue) {
    boolean out = defaultValue;
    out = Boolean.parseBoolean(getProperty(key, "" + defaultValue));
    m_table.getEntry(key).setBoolean(out);
    return out;
  }

  public boolean getBooleanProperty(String key) {
    return getBooleanProperty(key, false);
  }
}

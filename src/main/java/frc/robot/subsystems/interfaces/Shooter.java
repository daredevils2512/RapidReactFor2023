package frc.robot.subsystems.interfaces;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface Shooter extends Subsystem {
  void spitBalls(double speed);
  void setVoltage(double voltage);
  void setRPM(double RPM);
  void setRPMPID(double setpoint);
  public double velocityToRPM(double velocity);
  public double get();
  public double getRPM();
  // double encoderRate();

}

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface Shooter extends Subsystem {
  void spitBalls(double speed);
  void setVoltage(double voltage);
  void setRPM(double RPM);
  // double encoderRate();

}

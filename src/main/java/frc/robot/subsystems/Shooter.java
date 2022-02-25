package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface Shooter extends Subsystem {
  void spitBalls();
  void setVoltage();
  void setRPM();
  double encoderRate();

}

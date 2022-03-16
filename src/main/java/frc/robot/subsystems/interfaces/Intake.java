package frc.robot.subsystems.interfaces;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface Intake extends Subsystem {
  void setExtended(boolean wantsExtended);
  void toggleExtended();
  void setIntake(double speed);
  boolean getExtended();
}

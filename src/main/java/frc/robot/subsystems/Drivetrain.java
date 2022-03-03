package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface Drivetrain extends Subsystem {
  void arcadeDrive(double move, double turn);
  void setLowGear(boolean wantsLowGear);
  void toggleShifters();
  int getLeftEncoder();
  int getRightEncoder();
  double getRightDistance();
  double getLeftDistance();
  boolean getLowGear();
}

package frc.robot.subsystems;

public interface Intake {
  void setExtended(boolean wantsExtended);
  void toggleExtended();
  void setIntake(double speed);
  boolean getExtended();
}

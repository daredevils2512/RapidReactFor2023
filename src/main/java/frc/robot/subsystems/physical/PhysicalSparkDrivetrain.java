package frc.robot.subsystems.physical;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NTSubsystem;

public class PhysicalSparkDrivetrain extends NTSubsystem implements Drivetrain {

  public PhysicalSparkDrivetrain() {
    super("Drivetrain");
  }

  @Override
  public void arcadeDrive(double move, double turn) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setLowGear(boolean wantsLowGear) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void toggleShifters() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public double getRightDistance() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double getLeftDistance() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean getLowGear() {
    // TODO Auto-generated method stub
    return false;
  }
  
}

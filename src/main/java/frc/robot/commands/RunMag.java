package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Magazine;

public class RunMag extends CommandBase  {
  // Construct the variables
  private final double m_speed;
  private final Magazine m_magazine;

  /** Assigns variables
   * @param Magazine The file to use
   * @param double Control for the speed of the magazine
   */
  public RunMag(Magazine magazine, double speed){
    m_magazine = magazine;
    m_speed = speed;
    addRequirements(magazine);
  }

  /** Executes the code and runs Magazine.moveBalls */
  @Override
  public void execute() {
    m_magazine.moveBalls(m_speed);
  }

  @Override
  public void end(boolean interrupted) {
    m_magazine.moveBalls(0.0);
  }
}


package frc.robot.commands.teleop;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Magazine;

public class RunMagCommand extends CommandBase  {
  // Variables
  private final DoubleSupplier m_speed;
  private final Magazine m_magazine;

  /** Assigns variables
   * @param Magazine The file to use
   * @param DoubleSupplier Control for the speed of the magazine
   */
  public RunMagCommand(Magazine magazine, DoubleSupplier speed){
    m_magazine = magazine;
    m_speed = speed;
    addRequirements(magazine);
  }

  /** Executes the code and runs moveBalls */
  @Override
  public void execute() {
    m_magazine.moveBalls(m_speed.getAsDouble());
  }

  /** Called once the command ends or is interrupted. */
  @Override
  public void end(boolean interrupted) {
    m_magazine.moveBalls(0.0);
  }
}

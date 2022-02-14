package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSub;

public class ActuateShiftCommand extends CommandBase {
  // Variables
  private final IntakeSub m_intakesub;

  /** Assigns values
   * @param intakesub the subsystem to use
   */
  public ActuateShiftCommand(IntakeSub intakesub) {
    m_intakesub = intakesub;
    addRequirements(m_intakesub);
  }

  /** Toggles low gear */
  @Override
  public void execute() {
    m_intakesub.toggleExtended();
  }
}

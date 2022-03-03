package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class ActuateShiftCommand extends InstantCommand {
  // Variables
  private final Intake m_intake;

  /** Assigns values
   * @param intakesub the subsystem to use
   */
  public ActuateShiftCommand(Intake intake) {
    m_intake = intake;
    // addRequirements(m_intake);
  }

  /** Toggles low gear */
  @Override
  public void execute() {
    m_intake.toggleExtended();
  }
}

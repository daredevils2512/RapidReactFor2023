package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.physical.PhysicalIntake;

public class IntakeCommand extends CommandBase {
  // Variables
  private final PhysicalIntake m_intake;
  private final DoubleSupplier m_speed;

  /** Assigns the values of the arcadeDrive stuff 
   * @param IntakeSub The subsystem to use
   * @param DoubleSupplier Control for speed of intake
  */
  public IntakeCommand(PhysicalIntake intake, DoubleSupplier speed) {
    m_intake = intake;
    m_speed = speed;
    addRequirements(m_intake);
  }  

  /** Executes intake code */
  public void execute() {
    m_intake.setIntake(m_speed.getAsDouble());
  }
}

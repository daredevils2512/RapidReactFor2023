package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;

public class DriveShiftCommand extends InstantCommand {
  // Variables
  private final Drivetrain m_drivetrain;

  /** Assigns the values of the arcadeDrive stuff 
   * @param drivetrain The subsystem to use
   * @param shift The control for shifting
  */
  public DriveShiftCommand(Drivetrain drivetrain) {
    m_drivetrain = drivetrain;
    addRequirements(m_drivetrain);
  }

  /** Executes the drive train code */
  @Override
  public void execute() {
    m_drivetrain.toggleShifters();
  }
}
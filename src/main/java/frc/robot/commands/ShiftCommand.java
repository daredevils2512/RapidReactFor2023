package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSub;

public class ShiftCommand extends CommandBase {
  // Variables
  private final DriveTrainSub m_drivetrain;

  /** Assigns the values of the arcadeDrive stuff 
   * @param drivetrain The subsystem to use
   * @param shift The control for shifting
  */
  public ShiftCommand(DriveTrainSub drivetrain) {
    m_drivetrain = drivetrain;
    addRequirements(m_drivetrain);
  }

  /** Executes the drive train code */
  @Override
  public void execute() {
    m_drivetrain.toggleShifters();
  }
}
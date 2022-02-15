package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DrivetrainCommand extends CommandBase {
  // Variables
  private final Drivetrain m_drivetrain;
  private final DoubleSupplier m_move;
  private final DoubleSupplier m_turn;

  /** Assigns the values of the arcadeDrive stuff 
   * @param drivetrain The subsystem to use
   * @param move Control for forward and backward movement
   * @param turn Control for left and right movement
  */
  public DrivetrainCommand(Drivetrain drivetrain, DoubleSupplier move, DoubleSupplier turn) {
    m_drivetrain = drivetrain;
    m_move = move;
    m_turn = turn;
    addRequirements(m_drivetrain);
  }

  /** Executes the drive train code */
  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(m_move.getAsDouble(), m_turn.getAsDouble());
  }
}
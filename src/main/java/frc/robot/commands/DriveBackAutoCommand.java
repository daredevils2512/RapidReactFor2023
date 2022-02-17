package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveBackAutoCommand extends CommandBase{
  private final Drivetrain m_drivetrain;
  private final double m_move;
  private final double m_distance;
  private double m_initialDistance;

  public DriveBackAutoCommand(Drivetrain drivetrain, double move, double distance){
    m_drivetrain = drivetrain;
    m_move = move;
    m_distance = distance;
  }

  @Override
  public void initialize() {
    m_initialDistance = m_drivetrain.getAverageDistance();
  }

  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(m_move, 0);
  }

  @Override
  public boolean isFinished() {
    return m_drivetrain.getAverageDistance() - m_initialDistance >= m_distance;
  }

  @Override
  public void end(boolean interrupted) {
    if (!interrupted){
      m_drivetrain.arcadeDrive (0,0);
    }
  }
}
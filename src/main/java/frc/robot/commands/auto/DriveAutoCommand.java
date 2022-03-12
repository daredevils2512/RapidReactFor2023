package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveAutoCommand extends CommandBase{
  private final Drivetrain m_drivetrain;
  private final double m_move;
  private final double m_distance;
  private double m_initialDistance;

  public DriveAutoCommand(Drivetrain drivetrain, double move, double distance) {
    m_drivetrain = drivetrain;
    m_move = move;
    m_distance = distance;
  }

  @Override
  public void initialize() {
    m_initialDistance = m_drivetrain.getDistance();
  }

  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(m_move, 0);
  }

  @Override
  public boolean isFinished() {
    return m_drivetrain.getDistance() - m_initialDistance >= m_distance;
  }

  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0,0);
  }
}
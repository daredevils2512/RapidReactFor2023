package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.physical.PhysicalDrivetrain;

public class DriveBackAuto extends CommandBase{
  private final PhysicalDrivetrain m_drivetrain;
  private final double m_move;
  private final double m_distance;
  private double m_initialDistance;

  public DriveBackAuto(PhysicalDrivetrain drivetrain, double move, double distance){
    m_drivetrain = drivetrain;
    m_move = move;
    m_distance = distance;
  }

  @Override
  public void initialize() {
    m_initialDistance = getDistance();
  }

  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(m_move, 0);
  }

  @Override
  public boolean isFinished() {
    return getDistance() - m_initialDistance >= m_distance;
  }

  @Override
  public void end(boolean interrupted) {
    if (!interrupted){
      m_drivetrain.arcadeDrive (0,0);
    }
  }

  private double getDistance() {
    return (m_drivetrain.getLeftDistance() + m_drivetrain.getRightDistance()) / 2;
  }
}
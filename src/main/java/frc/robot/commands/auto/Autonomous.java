package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Autonomous extends CommandBase {
  // Variables
  private final DriveAutoCommand m_drive;
  private final ShootAutoCommand m_shoot;

  public Autonomous(DriveAutoCommand drive, ShootAutoCommand shoot) {
    m_drive = drive;
    m_shoot = shoot;
  }

  @Override
  public void initialize() { }

  @Override
  public void execute() { 
    m_shoot.andThen(m_drive);
  }

  @Override
  public boolean isFinished() { 
    return m_drive.isFinished();
  }

  @Override
  public void end(boolean interrupted) { }
}
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class RevShooterAutoCommand extends CommandBase {
  // Variables
  private double speed;
  private Shooter m_shooter;

  public RevShooterAutoCommand(Shooter shooter, double speed) {
    m_shooter = shooter;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_shooter.spitBalls(speed);
  }

  @Override
  public boolean isFinished() {
    if (speed == 0) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void end(boolean interrupted) {
    if (!interrupted) m_shooter.spitBalls(0.0);
  }
}
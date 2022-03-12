package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class RevShooterCommand extends CommandBase {
  private double m_speed;
  private Shooter m_shooter;

  public RevShooterCommand(Shooter shooter, double speed) {
    m_speed = speed;
    m_shooter = shooter;
  }

  @Override
  public void initialize() { }

  @Override
  public void execute() {
    m_shooter.spitBalls(m_speed);
  }

  @Override
  public void end(boolean interrupted) {
    m_shooter.spitBalls(0);
  }

  @Override
  public boolean isFinished(){
    return false;
  }
  
}
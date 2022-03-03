package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class RevShooter extends CommandBase {
  private double speed;
  private Shooter m_shooter;

  public RevShooter(Shooter shooter, double speed) {
    m_shooter = shooter;
    this.speed = speed;

  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    m_shooter.spitBalls(speed);
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

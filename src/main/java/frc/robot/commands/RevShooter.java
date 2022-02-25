package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.physical.PhysicalShooter;

public class RevShooter extends CommandBase {
  private double speed;
  private PhysicalShooter m_shooter;

  public RevShooter(PhysicalShooter shooter, double speed) {
    m_shooter = shooter;

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
  }

}

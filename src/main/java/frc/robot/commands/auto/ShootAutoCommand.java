package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.teleop.IntakeCommand;
import frc.robot.commands.teleop.RevShooterCommand;
import frc.robot.commands.teleop.RunMagCommand;

public class ShootAutoCommand extends CommandBase {
  // Variables
  private final RevShooterCommand m_shooter;
  private final RunMagCommand m_mag;
  private final IntakeCommand m_intake;

  public ShootAutoCommand(RevShooterCommand shooter, RunMagCommand mag, IntakeCommand intake) {
    m_shooter = shooter;
    m_mag = mag;
    m_intake = intake;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_shooter.withTimeout(6).andThen(m_mag.withTimeout(5).alongWith(m_intake.withTimeout(5)));
  }

  @Override
  public void end(boolean interrupted) {
    // TODO: idk what exactly to add here but its probably really important
  }
}
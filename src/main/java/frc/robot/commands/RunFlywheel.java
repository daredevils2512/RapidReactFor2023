package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.physical.PhysicalShooter;

public class RunFlywheel extends CommandBase {
  private final Shooter m_flywheel;

  public RunFlywheel(Shooter flywheel) {
    m_flywheel = flywheel;
    addRequirements(flywheel);
  }

  /** Called when the command is initially scheduled. */
  @Override
  public void initialize() {}

  /** Called every time the scheduler runs while the command is scheduled. */
  @Override
  public void execute() {
    m_flywheel.setRPM(1);
  }

  /** Called once the command ends or is interrupted. */
  @Override
  public void end(boolean interrupted) {}

  /** Returns true when the command should end. */
  @Override
  public boolean isFinished() {
    return false;
  }
}

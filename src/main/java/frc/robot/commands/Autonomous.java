package frc.robot.commands;

import com.ctre.phoenix.led.Animation;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class Autonomous extends CommandBase {
  private final Drivetrain m_drivetrain;
  private final RunMagCommand m_mag;
  private final RunFlywheelCommand m_flywheel;
  private final double m_move;
  private final double m_distance;
  private final double m_shoot;
  private double m_initialDistance;

  public Autonomous(Drivetrain drivetrain, double move, double distance, RunFlywheelCommand flywheel, RunMagCommand mag, double shooter){
    m_drivetrain = drivetrain;
    m_move = move;
    m_distance = distance;
    m_flywheel = flywheel;
    m_mag = mag;
    m_shoot = shooter;
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

  public Command runShooter() {
    return m_flywheel.withTimeout(2).andThen(m_mag).withTimeout(1); //TODO find mag time for shooting
  }

  // public Command aim
}
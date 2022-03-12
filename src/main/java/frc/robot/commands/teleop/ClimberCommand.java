package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClimberCommand extends CommandBase {
  private final Climber m_climber;
  private final double m_speed;

  

  public ClimberCommand(Climber climber, double speed) {
    m_climber = climber;
    m_speed = speed;
    addRequirements(m_climber);
    
  }

  @Override
  public void execute() {
  
    m_climber.setClimbSpeed(m_speed);
  }
  @Override
  public void end(boolean interrupted){
    m_climber.setClimbSpeed(0);
  }
}
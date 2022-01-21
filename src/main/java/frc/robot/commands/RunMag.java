package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Magazine;

public class RunMag extends CommandBase  {
    private double speed;
    private Magazine m_magazine;
    public RunMag( Magazine magazine, double speed){
    m_magazine = magazine;
        
    }
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
    m_magazine.moveBalls(speed);
    }

    @Override
    public void end(boolean interrupted) {
        m_magazine.moveBalls(0.0);
  
    }
}


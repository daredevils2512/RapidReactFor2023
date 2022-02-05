package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSub;

public class DriveBackAuto extends CommandBase{
  private final DriveTrainSub m_drivetrain;
  private final double m_move;

  public DriveBackAuto(DriveTrainSub drivetrain, double move){
    m_drivetrain = drivetrain;
    m_move = move;
  }
  @Override
  public void initialize() {
    // TODO Auto-generated method stub
  }
  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(m_move, 0);
    // TODO Auto-generated method stub
  }
  @Override
  public boolean isFinished() {
    
    // TODO Auto-generated method stub
    return false;
  }
  @Override
  public void end(boolean interrupted) {
    // TODO Auto-generated method stub
  }
}
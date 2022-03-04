package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;

public class ShootLowGoalCommand extends ParallelCommandGroup {
  public ShootLowGoalCommand(Shooter shooter, Magazine magazine){
    addRequirements(shooter, magazine);
  }
}

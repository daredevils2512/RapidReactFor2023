package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.physical.PhysicalMagazine;
import frc.robot.subsystems.physical.PhysicalShooter;

public class ShootLowGoalCommand extends ParallelCommandGroup {
  public ShootLowGoalCommand(Shooter shooter, Magazine magazine){
    addRequirements(shooter, magazine);
  }
}

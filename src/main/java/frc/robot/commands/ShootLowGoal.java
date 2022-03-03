package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.physical.PhysicalMagazine;
import frc.robot.subsystems.physical.PhysicalShooter;

public class ShootLowGoal extends ParallelCommandGroup {
  public ShootLowGoal(PhysicalShooter shooter, PhysicalMagazine magazine){
    addRequirements(shooter, magazine);
  }
}

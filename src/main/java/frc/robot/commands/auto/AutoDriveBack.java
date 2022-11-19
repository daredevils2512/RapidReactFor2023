package frc.robot.commands.auto;


import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.Commands;
import frc.robot.subsystems.interfaces.Drivetrain;

public class AutoDriveBack extends CommandBase {
  private final Drivetrain drivetrain;
  private double initialDistance = 0;
  private final double distance;
  private final DoubleSupplier speed;

  public AutoDriveBack(Drivetrain drivetrain, DoubleSupplier speed, double  distance) {
    this.drivetrain = drivetrain;
    this.speed = speed;
    this.distance = distance;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    initialDistance = drivetrain.getDistance();
  }

  @Override
  public void execute() {
    Commands.drive(drivetrain, speed, () -> 0.0);
  }

  @Override
  public void end(boolean interrupted) {
    Commands.drive(drivetrain, ()-> 0.0, () -> 0.0);
  }

  @Override
  public boolean isFinished() {
    if (distance < 0) {
      return drivetrain.getDistance() - initialDistance < distance;
    } else {
      return drivetrain.getDistance() - initialDistance > distance;
    }
  }
}

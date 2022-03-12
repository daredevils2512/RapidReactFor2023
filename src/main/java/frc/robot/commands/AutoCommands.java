package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;

public final class AutoCommands extends CommandBase {
  private AutoCommands() {}

  public static Command autoDriveBack(Drivetrain drivetrain, double speed) {
    return new RunCommand(() -> drivetrain.arcadeDrive(speed, 0));
  }

  public static Command autoShoot(Shooter shooter, Magazine mag, Intake intake, double speed) {
    return new RunCommand(() -> Commands.revShooter(shooter, speed).withTimeout(6).andThen(Commands.runMag(mag, () -> speed).withTimeout(5).alongWith(Commands.runIntake(intake, () -> speed).withTimeout(5))).andThen(Commands.revShooter(shooter, 0)));
  }

  public static Command fullAuto(Drivetrain drivetrain, double driveSpeed, Shooter shooter, Magazine mag, Intake intake, double shootSpeed) {
    return autoDriveBack(drivetrain, driveSpeed).andThen(autoShoot(shooter, mag, intake, shootSpeed));
  }
}
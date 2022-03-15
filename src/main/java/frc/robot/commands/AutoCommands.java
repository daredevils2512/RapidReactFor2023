package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.interfaces.Drivetrain;
import frc.robot.subsystems.interfaces.Intake;
import frc.robot.subsystems.interfaces.Magazine;
import frc.robot.subsystems.interfaces.Shooter;

public final class AutoCommands {
  private AutoCommands() {}

  public static Command autoDriveBack(Drivetrain drivetrain, double speed, double driveTime) {
    return new RunCommand(() -> Commands.drive(drivetrain, () -> speed, () -> 0.0).withTimeout(driveTime).andThen(Commands.drive(drivetrain, () -> 0, () -> 0)));
  }

  public static Command autoShoot(Shooter shooter, Magazine mag, Intake intake, double speed) {
    return new RunCommand(() -> Commands.revShooter(shooter, speed).withTimeout(6).andThen(Commands.runMag(mag, () -> speed).withTimeout(5).alongWith(Commands.runIntake(intake, () -> speed).withTimeout(5))).andThen(Commands.revShooter(shooter, 0)));
  }

  public static Command fullAuto(Drivetrain drivetrain, double driveSpeed, double driveTime, Shooter shooter, Magazine mag, Intake intake, double shootSpeed) {
    return autoDriveBack(drivetrain, driveSpeed, driveTime).andThen(autoShoot(shooter, mag, intake, shootSpeed));
  }
}
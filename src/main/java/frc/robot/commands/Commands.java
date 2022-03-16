package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.LEDManager;
import frc.robot.subsystems.interfaces.Climber;
import frc.robot.subsystems.interfaces.Drivetrain;
import frc.robot.subsystems.interfaces.Intake;
import frc.robot.subsystems.interfaces.Magazine;
import frc.robot.subsystems.interfaces.Shooter;

public final class Commands {
  private Commands() {}
  
  public static Command intakeShifters(Intake intake) {
    return new InstantCommand(() -> intake.toggleExtended());
  }

  public static Command runIntake(Intake intake, DoubleSupplier speed) {
    return new RunCommand(() -> intake.setIntake(speed.getAsDouble()));
  }

  public static Command runClimber(Climber climber, double speed) {
    return new RunCommand(() -> climber.setClimbSpeed(speed));
  }

  public static Command driveShifters(Drivetrain drivetrain) {
    return new InstantCommand(() -> drivetrain.toggleShifters());
  }

  public static Command drive(Drivetrain drivetrain, DoubleSupplier move, DoubleSupplier turn) {
    return new RunCommand(() -> drivetrain.arcadeDrive(move.getAsDouble(), turn.getAsDouble()));
  }

  public static Command revShooter(Shooter shooter, double speed) {
    return new RunCommand(() -> shooter.setRPMPID(speed));
  }

  public static Command runMag(Magazine mag, DoubleSupplier speed) {
    return new RunCommand(() -> mag.moveBalls(speed.getAsDouble()));
  }

  public static Command toggleLEDs(LEDManager LED) {
    return new RunCommand(() -> LED.toggleLEDs());
  }
}

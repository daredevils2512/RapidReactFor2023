package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.interfaces.Drivetrain;
import frc.robot.subsystems.interfaces.Magazine;
import frc.robot.subsystems.interfaces.Shooter;

public final class AutoCommands {
  private AutoCommands() { }
  
  /** Auto command that only drives back.
  * @param drivetrain The drivetrain subsystem to use
  * @param speed The speed to go during autonomous (should be negative to go backwards)
  * @return The command to be used when called.
  */
 public static Command autoDriveBack(Drivetrain drivetrain, DoubleSupplier speed) {
  return Commands.drive(drivetrain, speed, () -> 0.0);
 }

 /** Auto command that only shoots.
  * @param shooter Shooter subsystem to use.
  * @param mag Magazine subsystem to use.
  * @param shootSpeed The speed for everything.
  * @return The command to be used when called.
  */
  public static Command autoShoot(Shooter shooter, Magazine mag, DoubleSupplier shootSpeed) {
     return Commands.revShooter(shooter, shootSpeed)
     .withTimeout(3)
      .andThen(Commands.revShooter(shooter, shootSpeed)
      .alongWith(Commands.runMag(mag, shootSpeed))
      .withTimeout(3));
  }

  /** Auto comamnd that drives back and then shoots.
   * @param mag Magazine subsystem to use.
   * @param shootSpeed The speed to shoot the balls
   * @param drivetrain Drivetrain subsystem to use.
   * @param driveSpeed The speed to drive during autonomous (should be negative to go backwards)
   * @param shooter Shooter subsystem to use.
   * @return The command to be used when called.
   */
  public static Command fullAuto(Magazine mag, DoubleSupplier shootSpeed, Drivetrain drivetrain, DoubleSupplier driveSpeed, Shooter shooter) {
    return autoShoot(shooter, mag, shootSpeed)
    .andThen(autoDriveBack(drivetrain, driveSpeed)
    .withTimeout(6));
  }
}
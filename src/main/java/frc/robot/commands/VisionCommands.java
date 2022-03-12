package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Vision.Limelight;
import frc.robot.Vision.LimelightLEDMode;
import frc.robot.subsystems.Drivetrain;
import frc.robot.utils.Constants;

public final class VisionCommands {
  static NetworkTable m_limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

  public static Command Aim(Drivetrain drivetrain, Limelight limelight) {
    limelight.setLEDMode(LimelightLEDMode.ON);

    double tx = m_limelightTable.getEntry("tx").getDouble(0);

    return new RunCommand(() -> drivetrain.arcadeDrive(0, Constants.Kp * tx));
  }

  public static Command findRange(Drivetrain drivetrain, Limelight limelight) {
    limelight.setLEDMode(LimelightLEDMode.ON);

    double ty = m_limelightTable.getEntry("ty").getDouble(0);
    double angleToGoalDegrees = Constants.limelightMountAngleDegrees + ty;
    double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
    double currentDistance = (Constants.goalHeightInches - Constants.limelightLensHeightInches) / Math.tan(angleToGoalRadians);
    double distanceVariation = Constants.desiredDistance - currentDistance;
    double driveAjust = Constants.Kp * distanceVariation;

    return new RunCommand(() -> drivetrain.arcadeDrive(driveAjust, 0));
  }
}

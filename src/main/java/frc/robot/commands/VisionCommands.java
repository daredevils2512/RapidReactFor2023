package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.interfaces.Drivetrain;
import frc.robot.utils.Constants;
import frc.robot.vision.Limelight;
import frc.robot.vision.LimelightLEDMode;

public final class VisionCommands {
  private VisionCommands() {}

  static NetworkTable m_limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

  public static Command Aim(Drivetrain drivetrain) {
    double tx = m_limelightTable.getEntry("tx").getDouble(0);
    double turnAdjust = Constants.K_P * tx;

    return new RunCommand(() -> drivetrain.arcadeDrive(0, turnAdjust));
  }

  public static Command findRange(Drivetrain drivetrain) {
    double ty = m_limelightTable.getEntry("ty").getDouble(0);
    double angleToGoalDegrees = Constants.LIMELIGHT_MOUNT_ANGLE_DEGREES + ty;
    double angleToGoalRadians = angleToGoalDegrees * (Math.PI / 180.0);
    double currentDistance = (Constants.GOAL_HEIGHT - Constants.LIMELIGHT_LENS_HEIGHT) / Math.tan(angleToGoalRadians);
    double distanceVariation = Constants.DESIRED_DISTANCE - currentDistance;
    double moveAjust = Constants.K_P * distanceVariation;

    return new RunCommand(() -> drivetrain.arcadeDrive(moveAjust, 0));
  }

  public static Command turnOnLimelight(Limelight limelight) {
    return new RunCommand(() -> limelight.setLEDMode(LimelightLEDMode.ON));
  }

  public static Command turnOffLimelight(Limelight limelight) {
    return new RunCommand(() -> limelight.setLEDMode(LimelightLEDMode.OFF));
  }
}

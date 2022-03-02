package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.utils.Constants;

public class FindRange extends CommandBase {
  private final Drivetrain m_drivetrain;
  private final NetworkTable m_limelight;
  private final NetworkTableEntry m_ty;
  private final double m_distanceThreshold;
  //private final Double angleToGoalDegrees;
  
  private final Double Kpd;

  public FindRange(Drivetrain drivetrain, double distanceThreshold) {
    m_drivetrain = drivetrain;
    m_distanceThreshold = distanceThreshold;
    Kpd= 0.2;
    m_limelight = NetworkTableInstance.getDefault().getTable("limelight");
    m_ty = m_limelight.getEntry("ty");
  }

  /** Called every time the scheduler runs while the command is scheduled. */
  @Override
  public void execute() {    
    //calculate distance
    double driveAjust= Kpd*getDistanceVariation();

    m_drivetrain.arcadeDrive(driveAjust, 0);
  }

  /** Called once the command ends or is interrupted. */
  @Override
  public void end(boolean interrupted) { }

  /** Returns true when the command should end. */
  @Override
  public boolean isFinished() {
    return getDistanceVariation() < m_distanceThreshold;
  }

  private double getDistanceVariation() {
    double angleToGoalDegrees = Constants.limelightMountAngleDegrees + m_ty.getDouble(0.0);
    double angleToGoalRadians = Math.toRadians(angleToGoalDegrees);

    double currentDistance = (Constants.goalHeightInches - Constants.limelightLensHeightInches)/Math.tan(angleToGoalRadians);
    double distanceVariation = Constants.desiredDistance - currentDistance;

    return distanceVariation;
  }
}

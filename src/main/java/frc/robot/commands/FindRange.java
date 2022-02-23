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
  // private final Double angleToGoalDegrees;
  

  
  private final Double Kpd;

    public FindRange(Drivetrain drivetrain){
      m_drivetrain = drivetrain; 
      Kpd= 0.2;
      m_limelight = NetworkTableInstance.getDefault().getTable("limelight");
      m_ty = m_limelight.getEntry("ty");
      double angleToGoalDegrees = Constants.limelightMountAngleDegrees + m_ty.getDouble(0.0);
      double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
      //calculate distance
      double distanceFromLimelightToGoalInches = (Constants.goalHeightInches - Constants.limelightLensHeightInches)/Math.tan(angleToGoalRadians);
      
    }

}

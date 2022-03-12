package frc.robot.commands.vision;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Aim extends CommandBase{
  private final Drivetrain m_drivetrain;
  private final NetworkTable m_limelight;
  private final NetworkTableEntry m_tx; 
  private final Double m_aimAjust; 
  private final Double Kp; 


  public Aim(Drivetrain drivetrain){
    m_drivetrain = drivetrain; 
    Kp = 0.2;
    m_limelight = NetworkTableInstance.getDefault().getTable("limelight");
    m_tx = m_limelight.getEntry("tx");
    m_aimAjust = Kp * m_tx.getDouble(0);  

  }

  /** Called when the command is initially scheduled. */
  @Override
  public void initialize() {}

  /** Called every time the scheduler runs while the command is scheduled. */
  @Override
  public void execute() {
   m_drivetrain.arcadeDrive(0, m_aimAjust);
  }

  /** Called once the command ends or is interrupted. */
  @Override
  public void end(boolean interrupted) {}

  /** Returns true when the command should end. */
  @Override
  public boolean isFinished() {
    return false;
  }
}

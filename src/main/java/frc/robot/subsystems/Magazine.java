package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Magazine extends NTSubsystem {
  private final Victor m_magMotor;

  // the channel below is not an actual channel, and therefore just a placeholder
  private final int channel = -1;

  public Magazine() {
    super("Magazine");
    m_magMotor = new Victor(channel);

  }

  public void moveBalls(double magSpeed) {
    m_magMotor.set(magSpeed);
    m_logger.finest("magazine speed: " + magSpeed);
  }

}

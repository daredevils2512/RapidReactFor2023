package frc.robot.subsystems;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {  
    private final NetworkTableEntry m_speed = NetworkTableInstance.getDefault().getTable("Test").getEntry("Speed");

    private final SlewRateLimiter m_limiter;
    
    private final int channel = 0;

    private final PWMSparkMax m_spark;

    public Shooter() {
        m_spark = new PWMSparkMax(0);
        
        m_limiter = new SlewRateLimiter(0.4);
    
        m_speed.setDouble(0);

    }

  public void set(double speed) {
    speed = m_limiter.calculate(speed);
    m_spark.set(speed);
  }  
}

package frc.robot.subsystems;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.Logger;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class Shooter extends NTSubsystem { 
  private final int channel = 0;
  private double ks = 0;
  private double kv = 0;

  private final NetworkTableEntry m_speed = NetworkTableInstance.getDefault().getTable("Test").getEntry("Speed");

  private final PWMSparkMax m_spark;
  private final SlewRateLimiter m_limiter;
  private final SimpleMotorFeedforward feedforward;

  public Shooter() {
    super("Shooter");
    m_speed.setDouble(0);

    m_spark = new PWMSparkMax(channel);
      
    m_limiter = new SlewRateLimiter(0.4);
    feedforward = new SimpleMotorFeedforward(ks, kv);
  }

  public void set(double speed) {
    speed = m_limiter.calculate(speed);
    m_spark.set(speed);
  }  
  public void setvoltage(double voltage){
    m_spark.setVoltage(voltage);
  }
  
  public void setRPM (double RPM){
    double voltage = feedforward.calculate(RPM);
    m_spark.setVoltage(voltage);
  }
}

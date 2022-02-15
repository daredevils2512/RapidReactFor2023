package frc.robot.subsystems;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Encoder;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Shooter extends NTSubsystem { 
  // IDs TODO: Change these values!
  private final int k_channel = 0;
  private final int k_encoderID1 = 1;
  private final int k_encoderID2 = 2;
  private final double k_rateLim = 0.5;
  private final double k_ks = 0;
  private final double k_kv = 0;

  // Motor stuff
  private final Encoder m_encoder;
  private final WPI_TalonFX m_motor;
  private final SlewRateLimiter m_limiter;
  private final SimpleMotorFeedforward feedforward;

  // Network table stuff
  private final NetworkTableEntry m_speed;

  public Shooter() {
    super("Shooter");

    m_speed = NetworkTableInstance.getDefault().getTable("Test").getEntry("Speed");
    m_speed.setDouble(0);

    m_encoder = new Encoder(k_encoderID1, k_encoderID2);
    m_encoder.setDistancePerPulse(1./4096);

    m_motor = new WPI_TalonFX(k_channel);
      
    m_limiter = new SlewRateLimiter(k_rateLim);
    feedforward = new SimpleMotorFeedforward(k_ks, k_kv);
  }

  public void spitBalls(double speed) {
    speed = m_limiter.calculate(speed);
    m_motor.set(speed);
    m_logger.fine("set: " + get());
  }  
  
  public void setvoltage(double voltage){
    m_motor.setVoltage(voltage);
    m_logger.fine("set: " + get());
  }
  
  public void setRPM (double RPM){
    double voltage = feedforward.calculate(RPM);
    m_motor.setVoltage(voltage);
    m_logger.fine("set: " + get());
  }
 
  public double encoderRate(int encoder){
    return m_encoder.getRate();
  }
  
  public double get(){
    return m_motor.get(); 
  }
}

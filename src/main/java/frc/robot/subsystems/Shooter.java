package frc.robot.subsystems;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.Logger;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class Shooter extends SubsystemBase { 
  private final int channel = 0;
  private double ks = 0;
  private double kv = 0;

  private final NetworkTableEntry m_speed = NetworkTableInstance.getDefault().getTable("Test").getEntry("Speed");

  private final WPI_TalonFX m_motor;
  private final SlewRateLimiter m_limiter;
  private final SimpleMotorFeedforward feedforward;
  private final Encoder m_encoder = new Encoder(1, 2);

  public Shooter() {
    m_speed.setDouble(0);

    m_encoder.setDistancePerPulse(1./4096);

    m_motor = new WPI_TalonFX(channel);
      
    m_limiter = new SlewRateLimiter(0.4);
    feedforward = new SimpleMotorFeedforward(ks, kv);
  }

  public void set(double speed) {
    speed = m_limiter.calculate(speed);
    m_motor.set(speed);
  }  
  
  public void setvoltage(double voltage){
    m_motor.setVoltage(voltage);
  }
  
  public void setRPM (double RPM){
    double voltage = feedforward.calculate(RPM);
    m_motor.setVoltage(voltage);
  }
 
  public double encoderRate(int encoder){
    return m_encoder.getRate();
  }
  
  public double get(){
    return m_motor.get(); 
  }
}

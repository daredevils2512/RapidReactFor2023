package frc.robot.subsystems;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.utils.Constants;

import java.util.logging.Level;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Shooter extends NTSubsystem {
  // Motor stuff
  // private final Encoder m_encoder;
  private final WPI_TalonFX m_motor;
  private final SlewRateLimiter m_limiter;
  private final SimpleMotorFeedforward feedforward;

  // Network table stuff
  private final NetworkTableEntry m_speed;

  public Shooter() {
    super("Shooter", Level.FINE);

    m_speed = NetworkTableInstance.getDefault().getTable("Test").getEntry("Speed");
    m_speed.setDouble(0);

    // m_motor.getSelectedSensorVelocity() 
    // m_encoder.setDistancePerPulse(1./4096);

    m_motor = new WPI_TalonFX(Constants.shooterID);
      
    m_limiter = new SlewRateLimiter(Constants.shooterRateLimNUM);
    feedforward = new SimpleMotorFeedforward(Constants.shooterForwardChannel, Constants.shooterBackwardChannel);
  }

  public void spitBalls(double speed) {
    // speed = m_limiter.calculate(speed);
    m_motor.set(speed);
    m_logger.fine("set: " + get());
  }  
  
  public void setVoltage(double voltage) {
    m_motor.setVoltage(voltage);
    m_logger.fine("set: " + get());
  }
  
  public void setRPM (double RPM) {
    double voltage = feedforward.calculate(RPM);
    m_motor.setVoltage(voltage);
    m_logger.fine("set: " + get());
  }
 
  // public double encoderRate(int encoder){
  //   return m_encoder.getRate();
  // }
  
  public double get() {
    return m_motor.get(); 
  }
}

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.utils.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Shooter extends NTSubsystem {
  // PID
  private final PIDController PID;

  // Motor stuff
  private final Encoder m_encoder;
  private final WPI_TalonFX m_motor;
  private final SlewRateLimiter m_limiter;
  private final SimpleMotorFeedforward feedforward;

  // Network table stuff
  private final NetworkTableEntry m_speed;

  private double m_setpoint = 0.0;

  private double m_voltage;

  public Shooter() {
    super("Shooter");

    PID = new PIDController(Constants.shooterP, Constants.shooterI, Constants.shooterD);

    m_speed = NetworkTableInstance.getDefault().getTable("Test").getEntry("Speed");
    m_speed.setDouble(0);

    m_encoder = new Encoder(Constants.shooterEncoderChannelA, Constants.shooterEncoderChannelB);
    m_encoder.setDistancePerPulse(1./4096);

    m_motor = new WPI_TalonFX(Constants.shooterID);
      
    m_limiter = new SlewRateLimiter(Constants.shooterRateLimNUM);
    feedforward = new SimpleMotorFeedforward(Constants.shooterForwardChannel, Constants.shooterBackwardChannel);
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
    m_voltage = feedforward.calculate(RPM);
    m_motor.setVoltage(m_voltage);
    m_logger.fine("set: " + get());
  }
 
  public double encoderRate(int encoder){
    return m_encoder.getRate();
  }
  
  public double get(){
    return m_motor.get(); 
  }

  @Override
  public void periodic() {
  }

  public void setRPMPID(double setpoint) {
    m_motor.set(PID.calculate(m_encoder.getDistance(), setpoint));
  }
}

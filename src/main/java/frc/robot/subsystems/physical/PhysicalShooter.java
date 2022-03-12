package frc.robot.subsystems.physical;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.subsystems.NTSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.utils.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class PhysicalShooter extends NTSubsystem implements Shooter {
  // Motor stuff
  private final WPI_TalonFX m_motor;
  private final SlewRateLimiter m_limiter;
  private final SimpleMotorFeedforward feedforward;

  // Network table stuff
  private final NetworkTableEntry m_speed;

  public PhysicalShooter() {
    super("Shooter");

    m_speed = NetworkTableInstance.getDefault().getTable("Test").getEntry("Speed");
    m_speed.setDouble(0);

    m_motor = new WPI_TalonFX(Constants.shooterID);
      
    m_limiter = new SlewRateLimiter(Constants.shooterRateLimNUM);
    feedforward = new SimpleMotorFeedforward(Constants.shooterForwardChannel, Constants.shooterBackwardChannel);
  }

  /** spits balls
   * @param speed the speed at which to spit said balls
   */
  public void spitBalls(double speed) {
    speed = m_limiter.calculate(speed);
    m_motor.set(speed);
    m_logger.fine("set: " + get());
  }  
  
  /** sets the voltage of the shooter
   * @param voltage the voltage to set the shooter
   */
  public void setVoltage(double voltage) {
    m_motor.setVoltage(voltage);
    m_logger.fine("set: " + get());
  }
  
  /** sets the RPM of the shooter
   * @param RPM the rpm to set the shooter to
   */
  public void setRPM(double RPM) {
    double voltage = feedforward.calculate(RPM);
    m_motor.setVoltage(voltage);
    m_logger.fine("set: " + get());
  }
  
  /** @return returns the shooter motor */
  public double get() {
    return m_motor.get(); 
  }
}

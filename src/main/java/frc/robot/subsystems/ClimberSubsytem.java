package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class ClimberSubsytem extends NTSubsystem {
    // IDs TODO: Change to correct values!
    private final int m_rightTalonID = 69;
    private final int m_leftTalonID = 420;

    // Motor stuff
    private final TalonSRX m_rightMotor;
    private final TalonSRX m_leftMotor;

    public ClimberSubsytem (){
        super("CLimberSubsystem");

        m_rightMotor = new TalonSRX(m_rightTalonID);
        m_leftMotor = new TalonSRX(m_leftTalonID);
        m_leftMotor.follow (m_rightMotor);
    }

    /** Sets the speed of the climbing motors
     * @param double The speed of the motors
     */
    public void setclimbspeed(double speed){
        m_rightMotor.set(ControlMode.PercentOutput, speed);
        m_logger.fine("setclimbspeed: " + speed);
    }
}

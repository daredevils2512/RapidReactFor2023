package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class ClimberSubsytem extends NTSubsystem {
    int rightTalonid = 69;
    int leftTalonid = 420;
    TalonSRX right;
    TalonSRX left;

    public ClimberSubsytem (){
        super("CLimberSubsystem");
        right = new TalonSRX(-1);
        left = new TalonSRX(-1);
        left.follow (right);
    }
    public void setclimbspeed(double speed){
        left.set(ControlMode.PercentOutput, speed);
        right.set(ControlMode.PercentOutput, speed);
        m_logger.fine("setclimbspeed: " + speed);
    }
}

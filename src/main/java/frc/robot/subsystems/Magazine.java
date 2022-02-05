package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Victor;

public class Magazine extends NTSubsystem {
    private final Victor m_magMotor;
    private final int m_magMotorID = 69;

    public Magazine() {
        super("Magazine");
        // the channel below is not an actual channel, and therefore just a placeholder. 
         m_magMotor = new Victor(m_magMotorID);

    }

    public void moveBalls (double magSpeed) {
        m_magMotor.set(magSpeed);
        m_logger.fine("move balls: " + m_magMotor.get());
    }

}
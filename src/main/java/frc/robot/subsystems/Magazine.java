package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Victor;

public class Magazine extends NTSubsystem {
    private final Victor m_magMotor;

    public Magazine() {
        super("Magazine");
         m_magMotor = new Victor(-1);

    }

    public void moveBalls (double magSpeed) {
        m_magMotor.set(magSpeed);
        m_logger.fine("move balls: " + m_magMotor.get());
    }
    


    
}

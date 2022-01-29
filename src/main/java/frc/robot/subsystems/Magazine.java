package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Magazine extends NTSubsystem {
    private final Victor m_magMotor;

    public Magazine() {
        super("Magazine");
        //the channel below is not an actual channel, and therefore just a placeholder. 
         m_magMotor = new Victor(-1);

    }

    public void moveBalls (double magSpeed) {
        m_magMotor.set(magSpeed); 
        

    }
    


    
}

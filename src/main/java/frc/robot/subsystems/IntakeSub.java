package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class IntakeSub extends NTSubsystem {
    WPI_TalonSRX intake1 = new WPI_TalonSRX(01);
    WPI_TalonSRX intake2 = new WPI_TalonSRX(02);

    public IntakeSub() {
        super("IntakeSub");
        intake1.setInverted(false);
        intake2.setInverted(true);
        intake2.follow(intake1);
    }

    public void setIntake(double speed) {
        intake1.set(speed);
    }
}
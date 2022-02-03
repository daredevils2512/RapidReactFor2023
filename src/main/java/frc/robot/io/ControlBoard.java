package frc.robot.io;

import edu.wpi.first.wpilibj.XboxController;

public class ControlBoard {
    public XboxController xboxController = new XboxController(0);
    public Extreme extreme = new Extreme(1);
    public ButtonBox buttonBox = new ButtonBox(2);
}

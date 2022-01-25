// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Optional;
import java.util.function.DoubleSupplier;
import java.util.logging.Level;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.RunMag;
import frc.robot.io.NTButton;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;
import frc.robot.utils.LoggingManager;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final NetworkTableEntry m_useNTShooterControlEntry;
  private final NetworkTableEntry m_shooterSpeedEntry;
  
  private final LoggingManager logManager = new LoggingManager(); 

  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // private final Shooter m_shooter = new Shooter();
  private final Optional <Shooter> m_shooter; 
  private final Optional <Magazine> m_magazine;

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private final Joystick m_joyshtick = new Joystick(1);
  private final XboxController m_xBocshController = new XboxController(0);

  private final Button trigger = new JoystickButton(m_joyshtick, 1);
  private final Button sideButton = new JoystickButton(m_joyshtick, 2);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    NTButton.startConcurrentHandling();
    if (RobotBase.isSimulation()) logManager.robotLogger.setLevel(Level.FINER);

    configureButtonBindings();
    m_magazine = Optional.empty();
    m_shooter = Optional.of(new Shooter());

    m_useNTShooterControlEntry = NetworkTableInstance.getDefault().getEntry("Use network tables for shooter control");
    m_shooterSpeedEntry = NetworkTableInstance.getDefault().getEntry("Shooter set speed");

    m_useNTShooterControlEntry.setBoolean(true);
    m_shooterSpeedEntry.setDouble(0);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    m_magazine.ifPresent((magazine) -> {
      trigger.whileHeld(new RunMag( magazine, 1));  
    });

    m_shooter.ifPresent((shooter) -> {
    shooter.setDefaultCommand(new RunCommand(() -> {
      DoubleSupplier speedSupplier = () -> {
        if (m_useNTShooterControlEntry.getBoolean(true)) {
          return m_shooterSpeedEntry.getDouble(0);
        }
        else {
          return m_joyshtick.getRawButtonPressed(2) ? 1 : 0;
        }
      };
      shooter.spitBalls(speedSupplier.getAsDouble());
    }, shooter));
  });
};

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}

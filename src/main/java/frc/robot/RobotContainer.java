// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.DoubleSupplier;
import java.util.logging.Level;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DriveTrainCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.io.NTButton;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.IntakeSub;
import frc.robot.commands.RunFlywheel;
import frc.robot.commands.RunMag;
import frc.robot.io.ControlBoard;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;
import frc.robot.utils.LoggingManager;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private static XboxController driver = new XboxController(0);

  private final NetworkTableEntry m_useNTShooterControlEntry;
  private final NetworkTableEntry m_shooterSpeedEntry;
  
  private final LoggingManager logManager = new LoggingManager(); 

  private final DriveTrainSub m_DriveTrainSub = new DriveTrainSub();
  private final IntakeSub m_IntakeSub = new IntakeSub();

  public enum Axis {
    kLeftX(0),
    kRightX(4),
    kLeftY(1),
    kRightY(5),
    kLeftTrigger(2),
    kRightTrigger(3);

    @SuppressWarnings("MemberName")
    public final int value;

    Axis(int value) {
      this.value = value;
    }
  }

  public static double getMove() {
    return driver.getLeftY();

  }

  public static double getTurn() {
    return driver.getRightX();
  }

  public static double getIntake() {
    // TODO: Change to correct controls!
    return driver.getRightTriggerAxis();
  }

  private final Shooter m_shooter = new Shooter();
  private final Magazine m_magazine = new Magazine();
  
  private final RunFlywheel m_runFlywheel = new RunFlywheel(m_shooter);
  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private final ControlBoard controlBoard = new ControlBoard();

  private final Trigger shooterReady = new Trigger(()->{
    return m_shooter.get()==0.2;
    //no value yet
  });

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    NTButton.startConcurrentHandling();
    if (RobotBase.isSimulation()) logManager.robotLogger.setLevel(Level.FINER);
    // m_autoCommand = new DriveTrainCommand(m_DriveTrainSub, () -> { return getMove(); }, () -> { return getTurn(); });
    configureButtonBindings();

    m_useNTShooterControlEntry = NetworkTableInstance.getDefault().getEntry("Use network tables for shooter control");
    m_shooterSpeedEntry = NetworkTableInstance.getDefault().getEntry("Shooter set speed");

    m_useNTShooterControlEntry.setBoolean(false);
    m_shooterSpeedEntry.setDouble(0);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    m_DriveTrainSub.setDefaultCommand(new DriveTrainCommand(m_DriveTrainSub, () -> {
      return getMove();
    }, () -> {
      return getTurn();
    }));
    
    m_IntakeSub.setDefaultCommand(new IntakeCommand(m_IntakeSub, () -> getIntake()));

    controlBoard.extreme.trigger.whileHeld(new RunMag( m_magazine, 1));

    m_shooter.setDefaultCommand(new RunCommand(() -> {

    
      DoubleSupplier speedSupplier = () -> {
        if (m_useNTShooterControlEntry.getBoolean(true)) {
          return m_shooterSpeedEntry.getDouble(0);
        }
        else {
          return controlBoard.extreme.sideButton.get() ? 1 : 0;
        }
      };
      m_shooter.set(speedSupplier.getAsDouble());
    }, m_shooter));

     controlBoard.buttonBox.topWhite.and(shooterReady).whileActiveContinuous(new RunCommand(()->{
      m_magazine.moveBalls(2);
     },m_magazine));

     controlBoard.buttonBox.topWhite.and(shooterReady).whenInactive(new RunCommand(()->{
      m_magazine.moveBalls(-1);
     },m_magazine).withTimeout(1));
    
     controlBoard.buttonBox.topWhite.whileHeld(m_runFlywheel);
  }
  

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}

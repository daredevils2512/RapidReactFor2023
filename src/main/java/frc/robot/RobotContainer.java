package frc.robot;

import java.util.Optional;
import java.util.logging.Level;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DriveBackAuto;
import frc.robot.commands.ActuateShiftCommand;
import frc.robot.commands.ClimberCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.io.NTButton;
import frc.robot.subsystems.physical.PhysicalClimber;
import frc.robot.subsystems.physical.PhysicalDrivetrain;
import frc.robot.subsystems.physical.PhysicalIntake;
import frc.robot.subsystems.physical.PhysicalMagazine;
import frc.robot.subsystems.physical.PhysicalShooter;
import frc.robot.commands.RunFlywheel;
import frc.robot.commands.RunMag;
import frc.robot.commands.ShootLowGoal;
import frc.robot.commands.DriveShiftCommand;
import frc.robot.commands.DrivetrainCommand;
import frc.robot.commands.RevShooter;
import frc.robot.io.ControlBoard;
import frc.robot.utils.Constants;
import frc.robot.utils.LoggingManager;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Network table stuff
  private final NetworkTableEntry m_useNTShooterControlEntry;
  private final NetworkTableEntry m_shooterSpeedEntry;

  // Logging
  private final LoggingManager m_logManager;

  // Subsystems
  private final Optional<PhysicalDrivetrain> m_drivetrainSub;
  private final Optional<PhysicalClimber> m_climber;
  private final Optional<PhysicalIntake> m_intakeSub;
  private final Optional<PhysicalMagazine> m_magazine;
  private final Optional<PhysicalShooter> m_shooter;

  // Commands
  private final ActuateShiftCommand m_intakeShift;
  private final ClimberCommand m_climberComamnd;
  private final DriveBackAuto m_auto;
  private final DriveShiftCommand m_driveShift;
  private final DrivetrainCommand m_drivetrainCommand;
  private final IntakeCommand m_intakeCommand;
  private final RevShooter m_revShooter;
  private final RunFlywheel m_runFlywheel;
  private final RunMag m_runMag;
  private final ShootLowGoal m_shootLowGoal;

  // Controls
  private final ControlBoard m_controlBoard;

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

  /** @return Left Stick y-Axis */
  public double getMove() {
    return m_controlBoard.xboxController.getYAxisLeft();
  }

  /** @return Right Stick x-Axis */
  public double getTurn() {
    return m_controlBoard.xboxController.getXAxisRight();
  }

  /** @return Right Trigger (this is temporary) */
  public double getIntake() {
    // TODO: Change to correct controls!
    return m_controlBoard.xboxController.getRightTrigger();
  }

  /** @return XAxisLeft (this is temporary) */
  public double getMagz() {
    // TODO: Change to correct controls!
    return m_controlBoard.xboxController.getXAxisLeft();
  }

  /** @return YAxisRight (this is temporary) */
  public double getClimber() {
    return m_controlBoard.xboxController.getYAxisRight();
  }

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Define optionals
    m_climber = Optional.of(new PhysicalClimber());
    m_drivetrainSub = Optional.of(new PhysicalDrivetrain());
    m_intakeSub = Optional.of(new PhysicalIntake());
    m_magazine = Optional.of(new PhysicalMagazine());
    m_shooter = Optional.of(new PhysicalShooter());

    // Define commands
    m_intakeShift = m_intakeSub.isPresent() ? new ActuateShiftCommand(m_intakeSub.get()) : null;
    m_climberComamnd = m_climber.isPresent() ? new ClimberCommand(m_climber.get(), getClimber()) : null;
    m_auto = m_drivetrainSub.isPresent() ? new DriveBackAuto(m_drivetrainSub.get(), Constants.DRIVE_AUTO_SPEED, Constants.AUTO_DRIVE_BACK_DISTANCE) : null;
    m_driveShift = m_drivetrainSub.isPresent() ? new DriveShiftCommand(m_drivetrainSub.get()) : null;
    m_drivetrainCommand = m_drivetrainSub.isPresent() ? new DrivetrainCommand(m_drivetrainSub.get(), () -> { return getMove(); }, () -> { return getTurn(); }) : null;
    m_intakeCommand = m_intakeSub.isPresent() ? new IntakeCommand(m_intakeSub.get(), () -> getIntake()) : null;
    m_revShooter = m_shooter.isPresent() ? new RevShooter(m_shooter.get(), 0) : null;
    m_runFlywheel = m_shooter.isPresent() ? new RunFlywheel(m_shooter.get()) : null;
    m_runMag = m_magazine.isPresent() ? new RunMag(m_magazine.get(), () -> 0) : null;
    m_shootLowGoal = null; // TODO: idk what this is

    // Define
    m_logManager = new LoggingManager();
    m_controlBoard = new ControlBoard();

    // Configure the button bindings
    if (RobotBase.isSimulation())
      m_logManager.robotLogger.setLevel(Level.FINER);
    configureButtonBindings();

    // Network Table stuff
    NTButton.startConcurrentHandling();
    m_useNTShooterControlEntry = NetworkTableInstance.getDefault().getEntry("Use network tables for shooter control");
    m_shooterSpeedEntry = NetworkTableInstance.getDefault().getEntry("Shooter set speed");
    m_useNTShooterControlEntry.setBoolean(false);
    m_shooterSpeedEntry.setDouble(0);

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // TODO Make correct controls
    if (m_intakeSub.isPresent()) m_controlBoard.extreme.baseBackLeft.whenPressed(m_intakeShift);
    if (m_climber.isPresent()) m_climber.get().setDefaultCommand(m_climberComamnd);
    // m_auto command here
    if (m_drivetrainSub.isPresent()) m_controlBoard.extreme.baseBackRight.whenPressed(m_driveShift);
    if (m_drivetrainSub.isPresent()) m_drivetrainSub.get().setDefaultCommand(m_drivetrainCommand);
    if (m_intakeSub.isPresent()) m_intakeSub.get().setDefaultCommand(m_intakeCommand);
    if (m_shooter.isPresent()) m_controlBoard.extreme.sideButton.whileHeld(m_revShooter);
    if (m_shooter.isPresent()) m_controlBoard.buttonBox.topWhite.whileHeld(m_runFlywheel);
    if (m_magazine.isPresent()) m_magazine.get().setDefaultCommand(m_runMag);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   * 
   * @return The command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
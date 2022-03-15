package frc.robot;

import java.util.logging.Level;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.io.NTButton;
import frc.robot.commands.Commands;
import frc.robot.commands.VisionCommands;
import frc.robot.commands.AutoCommands;
import frc.robot.subsystems.dummy.DummyDrivetrain;
import frc.robot.subsystems.dummy.DummyIntake;
import frc.robot.subsystems.dummy.DummyMagazine;
import frc.robot.subsystems.dummy.DummyShooter;
import frc.robot.subsystems.interfaces.Climber;
import frc.robot.subsystems.interfaces.CompresserManager;
import frc.robot.subsystems.interfaces.Drivetrain;
import frc.robot.subsystems.interfaces.Intake;
import frc.robot.subsystems.interfaces.Magazine;
import frc.robot.subsystems.interfaces.Shooter;
import frc.robot.subsystems.dummy.DummyClimber;
import frc.robot.subsystems.dummy.DummyCompressor;
import frc.robot.subsystems.physical.PhysicalClimber;
import frc.robot.subsystems.physical.PhysicalCompressor;
import frc.robot.subsystems.physical.PhysicalDrivetrain;
import frc.robot.subsystems.physical.PhysicalIntake;
import frc.robot.subsystems.physical.PhysicalMagazine;
import frc.robot.subsystems.physical.PhysicalShooter;
import frc.robot.subsystems.physical.PhysicalSparkDrivetrain;
import frc.robot.io.ControlBoard;
import frc.robot.utils.Constants;
import frc.robot.utils.LoggingManager;
import frc.robot.vision.DummyLimelight;
import frc.robot.vision.Limelight;
import frc.robot.vision.PhysicalLimelight;
import frc.robot.vision.Pipeline;
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
  private final Drivetrain m_drivetrain;
  private final Climber m_climber;
  private final Intake m_intake;
  private final Magazine m_magazine;
  private final Shooter m_shooter;
  private final CompresserManager m_compressor;

  // Commands
  private final Command m_intakeShift;
  private final Command m_climberUp;
  private final Command m_climberDown;
  private final Command m_autoDriveBack;
  private final Command m_autoShoot;
  private final Command m_autoDriveBackAndShoot;
  private final Command m_driveShift;
  private final Command m_drive;
  private final Command m_takeBalls;
  private final Command m_revShooterFast;
  private final Command m_revShooterSlow;
  private final Command m_runMag;
  private final Command m_turnOnLimelight;
  private final Command m_turnOffLimelight;
  private final Command m_aim;
  private final Command m_FindRange;

  private final Limelight m_limelight;

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

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Define Subsystems
    m_climber = Constants.CLIMBER_ENABLED ? new PhysicalClimber() : new DummyClimber();
    m_drivetrain = Constants.DRIVETRAIN_ENABLED ? (Constants.SPARK_DRIVETRAIN_ENABLED ? new PhysicalSparkDrivetrain() : new PhysicalDrivetrain()) : new DummyDrivetrain();
    m_intake = Constants.INTAKE_ENABLED ? new PhysicalIntake() : new DummyIntake();
    m_magazine = Constants.MAGAZINE_ENABLED ? new PhysicalMagazine() : new DummyMagazine();
    m_shooter = Constants.SHOOTER_ENABLED ? new PhysicalShooter() : new DummyShooter();
    m_limelight = Constants.LIMELIGHT_ENABLED ? new PhysicalLimelight(Pipeline.N_E_D) : new DummyLimelight();
    m_compressor = Constants.COMPRESSOR_ENABLED ? new PhysicalCompressor() : new DummyCompressor();

    // Define Commands
    m_driveShift = Commands.driveShifters(m_drivetrain);
    m_drive = Commands.drive(m_drivetrain, () -> getMove(), () -> getTurn());
    
    m_runMag = Commands.runMag(m_magazine, () -> 1);

    m_climberUp = Commands.runClimber(m_climber, Constants.CLIMBER_SPEED);
    m_climberDown = Commands.runClimber(m_climber, -Constants.CLIMBER_SPEED);

    m_takeBalls = Commands.runIntake(m_intake, () -> 1);
    m_intakeShift = Commands.intakeShifters(m_intake);

    m_revShooterFast = Commands.revShooter(m_shooter, Constants.SHOOTER_FAST_SPEED);
    m_revShooterSlow = Commands.revShooter(m_shooter, Constants.SHOOTER_SLOW_SPEED);

    m_autoDriveBack = AutoCommands.autoDriveBack(m_drivetrain, Constants.DRIVE_AUTO_SPEED, Constants.AUTO_DRIVE_BACK_TIME);
    m_autoShoot = AutoCommands.autoShoot(m_shooter, m_magazine, m_intake, Constants.SHOOT_AUTO_SPEED);
    m_autoDriveBackAndShoot = AutoCommands.fullAuto(m_drivetrain, Constants.DRIVE_AUTO_SPEED, Constants.AUTO_DRIVE_BACK_TIME, m_shooter, m_magazine, m_intake, Constants.SHOOT_AUTO_SPEED);

    m_turnOnLimelight = VisionCommands.turnOnLimelight(m_limelight);
    m_turnOffLimelight = VisionCommands.turnOffLimelight(m_limelight);
    m_aim = VisionCommands.Aim(m_drivetrain);
    m_FindRange = VisionCommands.findRange(m_drivetrain);

    // Define
    m_logManager = new LoggingManager();
    m_controlBoard = new ControlBoard();

    // Configure the button bindings
    if (RobotBase.isSimulation()) m_logManager.robotLogger.setLevel(Level.FINER);
    
    configureButtonBindings();

    // Network Table stuff
    NTButton.startConcurrentHandling();
    m_useNTShooterControlEntry = NetworkTableInstance.getDefault().getEntry("Use network tables for shooter control");
    m_shooterSpeedEntry = NetworkTableInstance.getDefault().getEntry("Shooter set speed");
    m_useNTShooterControlEntry.setBoolean(false);
    m_shooterSpeedEntry.setDouble(0);

    m_compressor.setClosedLoopControl(true);

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
    m_controlBoard.extreme.baseMiddleLeft.whenPressed(m_intakeShift);
    m_controlBoard.extreme.baseMiddleRight.whileHeld(m_takeBalls);

    m_controlBoard.extreme.joystickTopLeft.whileHeld(m_climberUp);
    m_controlBoard.extreme.joystickTopRight.whileHeld(m_climberDown);
    
    m_controlBoard.xboxController.rightBumper.whenPressed(m_driveShift);
    m_drivetrain.setDefaultCommand(m_drive);

    m_controlBoard.extreme.joystickBottomLeft.whenPressed(m_turnOnLimelight);
    m_controlBoard.extreme.joystickBottomLeft.whileHeld(m_aim);
    m_controlBoard.extreme.joystickBottomLeft.whenReleased(m_turnOffLimelight);
    
    m_controlBoard.extreme.joystickBottomRight.whenPressed(m_turnOnLimelight);
    m_controlBoard.extreme.joystickBottomRight.whileHeld(m_FindRange);
    m_controlBoard.extreme.joystickBottomRight.whenReleased(m_turnOffLimelight);
    
    m_controlBoard.extreme.sideButton.whileHeld(m_revShooterFast);
    m_controlBoard.extreme.baseBackLeft.whileHeld(m_revShooterSlow);

    m_controlBoard.extreme.trigger.whileHeld(m_runMag);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   * 
   * @return The command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_autoDriveBackAndShoot;
  }
} 
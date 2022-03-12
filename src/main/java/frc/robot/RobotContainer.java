package frc.robot;

import java.util.logging.Level;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Vision.DummyLimelight;
import frc.robot.Vision.Limelight;
import frc.robot.Vision.PhysicalLimelight;
import frc.robot.Vision.Pipeline;
import frc.robot.io.NTButton;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.CompresserManager;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.commands.auto.Autonomous;
import frc.robot.commands.auto.DriveAutoCommand;
import frc.robot.commands.auto.ShootAutoCommand;
import frc.robot.commands.teleop.ActuateShiftCommand;
import frc.robot.commands.teleop.ClimberCommand;
import frc.robot.commands.teleop.DriveShiftCommand;
import frc.robot.commands.teleop.DrivetrainCommand;
import frc.robot.commands.teleop.IntakeCommand;
import frc.robot.commands.teleop.RevShooterCommand;
import frc.robot.commands.teleop.RunMagCommand;
import frc.robot.commands.vision.Aim;
import frc.robot.commands.vision.FindRange;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.dummy.DummyDrivetrain;
import frc.robot.subsystems.dummy.DummyIntake;
import frc.robot.subsystems.dummy.DummyMagazine;
import frc.robot.subsystems.dummy.DummyShooter;
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
  private final Drivetrain m_drivetrainSub;
  private final Climber m_climber;
  private final Intake m_intakeSub;
  private final Magazine m_magazine;
  private final Shooter m_shooter;
  private final CompresserManager m_compressor;

  // Commands
  private final Command m_intakeShift;
  private final Command m_climberUpComamnd;
  private final Command m_climberDownComamnd;
  private final Command m_autoDriveBack;
  private final Command m_autoShoot;
  private final Command m_autoDriveBackAndShoot;
  private final Command m_driveShift;
  private final Command m_drivetrainCommand;
  private final Command m_intakeCommand;
  private final Command m_revShooter;
  private final Command m_revShooter2;
  private final Command m_runMag;
  private final Command m_shootLowGoal;
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
    // Define optionals
    m_climber = Constants.climberEnabled ? new PhysicalClimber() : new DummyClimber();
    m_drivetrainSub = Constants.drivetrainEnabled ? (Constants.sparkDrivetrainEnabled ? new PhysicalSparkDrivetrain() : new PhysicalDrivetrain()) : new DummyDrivetrain();
    m_intakeSub = Constants.intakeEnabled ? new PhysicalIntake() : new DummyIntake();
    m_magazine = Constants.magazineEnabled ? new PhysicalMagazine() : new DummyMagazine();
    m_shooter = Constants.shooterEnabled ? new PhysicalShooter() : new DummyShooter();
    m_limelight = Constants.limelightEnabled ? new PhysicalLimelight(Pipeline.N_E_D) : new DummyLimelight();

    // Define commands
    // m_auto = new DriveBackAuto(m_drivetrainSub, Constants.DRIVE_AUTO_SPEED, Constants.AUTO_DRIVE_BACK_DISTANCE);
    m_intakeShift = new ActuateShiftCommand(m_intakeSub);
    m_driveShift = new DriveShiftCommand(m_drivetrainSub);
    m_drivetrainCommand = new DrivetrainCommand(m_drivetrainSub, () -> { return getMove(); }, () -> { return getTurn(); });
    // m_runFlywheel = new RunFlywheel(m_shooter);
    m_runMag = new RunMagCommand(m_magazine, () -> 1);
    m_shootLowGoal = null; // TODO: idk what this is

    m_FindRange = new FindRange(m_drivetrainSub);
    m_climberUpComamnd = new ClimberCommand(m_climber, Constants.climberSpeed);
    m_climberDownComamnd = new ClimberCommand(m_climber, -Constants.climberSpeed);
    m_intakeCommand = new IntakeCommand(m_intakeSub, () -> 1);
    m_revShooter = new RevShooterCommand(m_shooter, .75);
    m_revShooter2 = new RevShooterCommand(m_shooter, .25);
    // m_runFlywheel = m_shooter.isPresent() ? new RunFlywheel(m_shooter.get()) : null;

    m_compressor = Constants.compressorEnabled ? new PhysicalCompressor() : new DummyCompressor();

    m_autoDriveBack = new DriveAutoCommand(m_drivetrainSub, Constants.DRIVE_AUTO_SPEED, Constants.AUTO_DRIVE_BACK_DISTANCE);
    m_autoShoot = new ShootAutoCommand((RevShooterCommand) m_revShooter, (RunMagCommand) m_runMag, (IntakeCommand) m_intakeCommand);
    m_autoDriveBackAndShoot = new Autonomous((DriveAutoCommand) m_autoDriveBack, (ShootAutoCommand) m_autoShoot);

    m_aim = new Aim(m_drivetrainSub, m_limelight);
    // m_FindRange = m_drivetrainSub.isPresent() ? new FindRange(m_drivetrainSub.get()) :null;
    // m_aim = m_drivetrainSub.isPresent() ? new Aim(m_drivetrainSub.get(), m_limelight):null;
    // m_FindRange = m_drivetrainSub.isPresent() ? new FindRange(m_drivetrainSub.get()) :null;

    // m_runFlywheel = m_shooter.isPresent() ? new RunFlywheelCommand(m_shooter.get()) : null;
    

    
    

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
    // TODO Make correct controls
    m_controlBoard.extreme.baseMiddleLeft.whenPressed(m_intakeShift);
    m_controlBoard.extreme.baseMiddleRight.whileHeld(m_intakeCommand);

    m_controlBoard.extreme.joystickTopLeft.whileHeld(m_climberUpComamnd);
    m_controlBoard.extreme.joystickTopRight.whileHeld(m_climberDownComamnd);
    // m_auto command here
    m_controlBoard.xboxController.rightBumper.whenPressed(m_driveShift);
    m_drivetrainSub.setDefaultCommand(m_drivetrainCommand);
    
    m_controlBoard.extreme.sideButton.whileHeld(m_revShooter);
    m_controlBoard.extreme.baseBackLeft.whileHeld(m_revShooter2);
    // if (m_shooter.isPresent()) m_controlBoard.buttonBox.topWhite.whileHeld(m_runFlywheel);
    m_controlBoard.extreme.trigger.whileHeld(m_runMag);

    // if (m_drivetrainSub.isPresent()) m_controlBoard.extreme.joystickBottomLeft.whileHeld(m_aim);
    // if (m_drivetrainSub.isPresent()) m_controlBoard.extreme.joystickBottomRight.whileHeld(m_FindRange); 
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
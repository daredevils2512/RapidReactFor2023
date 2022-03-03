package frc.robot;

import java.util.logging.Level;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DriveBackAuto;
import frc.robot.commands.ActuateShiftCommand;
import frc.robot.commands.Aim;
import frc.robot.commands.ClimberCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.io.NTButton;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.CompresserManager;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
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
import frc.robot.commands.RunFlywheel;
import frc.robot.commands.RunMag;
import frc.robot.commands.ShootLowGoal;
import frc.robot.commands.DriveShiftCommand;
import frc.robot.commands.DrivetrainCommand;
import frc.robot.commands.FindRange;
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

  private final boolean shooterEnabled = true;
  private final boolean magazineEnabled = true;
  private final boolean intakeEnabled = true;
  private final boolean climberEnabled = true;
  private final boolean drivetrainEnabled = true;
  private final boolean sparkDrivetrainEnabled = false;
  private final boolean compressorEnabled = true;

  // Subsystems
  private final Drivetrain m_drivetrainSub;
  private final Climber m_climber;
  private final Intake m_intakeSub;
  private final Magazine m_magazine;
  private final Shooter m_shooter;
  private final CompresserManager m_compressor = compressorEnabled ? new PhysicalCompressor() : new DummyCompressor();

  // Commands
  private final ActuateShiftCommand m_intakeShift;
  private final ClimberCommand m_climberUpComamnd;
  private final ClimberCommand m_climberDownComamnd;
  private final DriveBackAuto m_auto;
  private final DriveShiftCommand m_driveShift;
  private final DrivetrainCommand m_drivetrainCommand;
  private final IntakeCommand m_intakeCommand;
  private final RevShooter m_revShooter;
  private final RevShooter m_revShooter2;
  // private final RunFlywheel m_runFlywheel;
  private final RunMag m_runMag;
  private final ShootLowGoal m_shootLowGoal;
  private final Aim m_aim;
  private final FindRange m_FindRange;
 

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
    m_climber = climberEnabled ? new PhysicalClimber() : new DummyClimber();
    m_drivetrainSub = drivetrainEnabled ? (sparkDrivetrainEnabled ? new PhysicalSparkDrivetrain() : new PhysicalDrivetrain()) : new DummyDrivetrain();
    m_intakeSub = intakeEnabled ? new PhysicalIntake() : new DummyIntake();
    m_magazine = magazineEnabled ? new PhysicalMagazine() : new DummyMagazine();
    m_shooter = shooterEnabled ? new PhysicalShooter() : new DummyShooter();

    // Define commands
    m_intakeShift = new ActuateShiftCommand(m_intakeSub);
    m_climberUpComamnd = new ClimberCommand(m_climber, .5);
    m_climberDownComamnd = new ClimberCommand(m_climber, -.5);
    m_auto = new DriveBackAuto(m_drivetrainSub, Constants.DRIVE_AUTO_SPEED, Constants.AUTO_DRIVE_BACK_DISTANCE);
    m_driveShift = new DriveShiftCommand(m_drivetrainSub);
    m_drivetrainCommand = new DrivetrainCommand(m_drivetrainSub, () -> { return getMove(); }, () -> { return getTurn(); });
    m_intakeCommand = new IntakeCommand(m_intakeSub, ()-> 1);
    m_revShooter = new RevShooter(m_shooter, .75);
    m_revShooter2 = new RevShooter(m_shooter, .25);
    // m_runFlywheel = new RunFlywheel(m_shooter);
    m_runMag = new RunMag(m_magazine, () -> 1);
    m_shootLowGoal = null; // TODO: idk what this is

    m_aim = new Aim(m_drivetrainSub);
    m_FindRange = new FindRange(m_drivetrainSub);

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
    m_controlBoard.extreme.baseBackLeft.whenPressed(m_intakeShift);
    m_controlBoard.extreme.joystickTopLeft.whileHeld(m_climberUpComamnd);
    m_controlBoard.extreme.joystickTopRight.whileHeld(m_climberDownComamnd);
    // m_auto command here
    m_controlBoard.extreme.baseBackRight.whenPressed(m_driveShift);
    m_drivetrainSub.setDefaultCommand(m_drivetrainCommand);
    m_controlBoard.extreme.joystickBottomLeft.whileHeld(m_intakeCommand);
    m_controlBoard.extreme.sideButton.whileHeld(m_revShooter);
    // m_controlBoard.buttonBox.topWhite.whileHeld(m_runFlywheel);
    m_controlBoard.extreme.trigger.whileHeld(m_runMag);
    m_controlBoard.buttonBox.yellow.whileHeld(m_aim);
    m_controlBoard.buttonBox.green.whileHeld(m_FindRange); 
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
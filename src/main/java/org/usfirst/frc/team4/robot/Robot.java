package org.usfirst.frc.team4.robot;

import org.usfirst.frc.team4.robot.commands.automodes.DoNothing;
import org.usfirst.frc.team4.robot.commands.automodes.TuneDistance;
import org.usfirst.frc.team4.robot.commands.automodes.TuneTurn;
import org.usfirst.frc.team4.robot.commands.automodes.VisionTurn;
import org.usfirst.frc.team4.robot.constants.ControllerConstants;
import org.usfirst.frc.team4.robot.constants.LimelightConstants;
import org.usfirst.frc.team4.robot.subsystems.Arm;
import org.usfirst.frc.team4.robot.subsystems.Chassis;
import org.usfirst.frc.team4.robot.subsystems.Intake;
import org.usfirst.frc.team4.robot.subsystems.Limelight;
import org.usfirst.frc.team4.robot.subsystems.Pneumatics;
import org.usfirst.frc.team4.robot.subsystems.Ramp;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static OI m_oi;
	public static Chassis m_chassis;
	public static Arm m_arm;
	public static Pneumatics m_pneumatics;
	public static Intake m_intake;
	public static Limelight m_limelight;
	public static Ramp m_ramp;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

		
		// Initializing USB Camera (in the RoboRio) with preferred settings
		new Thread(() -> {
			UsbCamera cam1 = CameraServer.getInstance().startAutomaticCapture(0);
			/* cam1.setExposureManual(90); */ cam1.setFPS(40);
			cam1.setResolution(160, 120);
		}).start();

		ControllerConstants.init();
		m_chassis = new Chassis();
		m_arm = new Arm();
		m_pneumatics = new Pneumatics();
		m_intake = new Intake();
		m_limelight = new Limelight();
		m_ramp = new Ramp();
		m_oi = new OI();

		// chooser.addOption("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		Robot.m_limelight.setLEDMode(LimelightConstants.eLEDMode.OFF);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {

		m_chooser = new SendableChooser<>();

		// Tuning Automodes
		m_chooser.addOption("Tune Turn", new TuneTurn());
		m_chooser.addOption("Tune Drive", new TuneDistance());
		m_chooser.addOption("Tune vision", new VisionTurn());

		// ETC
		m_chooser.setDefaultOption("No Command", new DoNothing());


		m_autonomousCommand = new TuneDistance();
		//SmartDashboard.putData("Auto modes", m_chooser);

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}

		Robot.m_chassis.reset();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
	

		//Cancles Auton 
		if(ControllerConstants.driveX.get()){
			m_autonomousCommand.cancel();
		
	}else{
		Robot.m_chassis.log();
	}
		}

	@Override
	public void teleopInit() {
		Robot.m_limelight.setCamMode(LimelightConstants.CameraMode.VISION_PROCESSING);
		Robot.m_limelight.setLEDMode(LimelightConstants.eLEDMode.ON);
		Robot.m_chassis.log();
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		Robot.m_chassis.reset();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		m_chassis.log();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}

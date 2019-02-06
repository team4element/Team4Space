package org.usfirst.frc.team4.robot;

import org.usfirst.frc.team4.robot.commands.automodes.DoNothing;
import org.usfirst.frc.team4.robot.commands.automodes.TestMotionProfile;
import org.usfirst.frc.team4.robot.commands.automodes.VisionTurn;
import org.usfirst.frc.team4.robot.commands.automodes.tune.TuneDistance;
import org.usfirst.frc.team4.robot.commands.automodes.tune.TuneTurn;
import org.usfirst.frc.team4.robot.constants.ControllerConstants;
import org.usfirst.frc.team4.robot.constants.LimelightConstants;
import org.usfirst.frc.team4.robot.subsystems.Arm;
import org.usfirst.frc.team4.robot.subsystems.Chassis;
import org.usfirst.frc.team4.robot.subsystems.Intake;
import org.usfirst.frc.team4.robot.subsystems.Limelight;
import org.usfirst.frc.team4.robot.subsystems.Pneumatics;
import org.usfirst.frc.team4.robot.subsystems.Ramp;
import org.usfirst.frc.team4.robot.utilities.AutoChooser;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public static OI m_oi;
	public static Chassis m_chassis;
	public static Arm m_arm;
	public static Pneumatics m_pneumatics;
	public static Intake m_intake;
	public static Limelight m_limelight;
	public static Ramp m_ramp;
	 
	AutoChooser m_AutoChooser;

	Command m_autonomousCommand;

	@Override
	public void robotInit() {

		
		// Initializing USB Camera (in the RoboRio) with preferred settings
		new Thread(() -> {
			UsbCamera cam1 = CameraServer.getInstance().startAutomaticCapture(0);
			/* cam1.setExposureManual(90); */ cam1.setFPS(40);
			cam1.setResolution(160, 120);
		}).start();

		m_AutoChooser = new AutoChooser(); 
		ControllerConstants.init();
		m_chassis = new Chassis();
		m_arm = new Arm();
		m_pneumatics = new Pneumatics();
		m_intake = new Intake();
		m_limelight = new Limelight();
		m_ramp = new Ramp();
		m_oi = new OI();

	}

	@Override
	public void disabledInit() {
		Robot.m_limelight.setLEDMode(LimelightConstants.eLEDMode.OFF);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
	
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}

		Robot.m_chassis.reset();

		m_autonomousCommand = new AutoChooser().getSelectedAuto();
	}

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

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		m_chassis.log();
	}

	@Override
	public void testPeriodic() {
	}
}
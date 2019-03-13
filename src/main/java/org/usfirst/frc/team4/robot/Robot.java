package org.usfirst.frc.team4.robot;

import org.usfirst.frc.team4.robot.commands.auton.tune.TuneTurn;
import org.usfirst.frc.team4.robot.constants.ControllerConstants;
import org.usfirst.frc.team4.robot.constants.LimelightConstants;
import org.usfirst.frc.team4.robot.subsystems.Arm;
import org.usfirst.frc.team4.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4.robot.subsystems.HatchRelease;
import org.usfirst.frc.team4.robot.subsystems.Intake;
import org.usfirst.frc.team4.robot.subsystems.Limelight;
import org.usfirst.frc.team4.robot.subsystems.Ramp;
import org.usfirst.frc.team4.robot.utilities.AutoChooser;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {
	public static OI m_oi;
	public static DriveTrain m_driveTrain;
	public static Arm m_arm;
	public static HatchRelease m_hatchRelease;
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
			
			cam1.setFPS(20);
			cam1.setResolution(160, 120);

			UsbCamera cam2 = CameraServer.getInstance().startAutomaticCapture(1);
			cam2.setFPS(20);
			cam2.setResolution(160, 120);
			
			
			
		}).start();

		m_AutoChooser = new AutoChooser(); 
		ControllerConstants.init();
		m_driveTrain = new DriveTrain();
		m_arm = new Arm();
		m_hatchRelease = new HatchRelease();
		m_intake = new Intake();
		m_limelight = new Limelight();
		m_ramp = new Ramp();
		m_oi = new OI();

		m_driveTrain.log();
		Robot.m_driveTrain.log();
		Robot.m_arm.log();

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

		Robot.m_driveTrain.reset();

		// m_autonomousCommand = new AutoChooser().getSelectedAuto();
		m_autonomousCommand = new TuneTurn();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

		m_driveTrain.log();
		Robot.m_driveTrain.log();
		Robot.m_arm.log();
		
	

		//Cancles Auton 
		if(ControllerConstants.driveX.get()){
			m_autonomousCommand.cancel();
		
	}else{
		Robot.m_driveTrain.log();
	}
		}

	@Override
	public void teleopInit() {
		// Robot.m_limelight.setCamMode(LimelightConstants.CameraMode.VISION_PROCESSING);
		// Robot.m_limelight.setLEDMode(LimelightConstants.eLEDMode.ON);
		Robot.m_limelight.setCamMode(LimelightConstants.CameraMode.DRIVERSTATION_FEEDBACK);
		Robot.m_driveTrain.reset();
		
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		m_driveTrain.log();
		Robot.m_driveTrain.log();
		Robot.m_arm.log();
		
	}

	@Override
	public void testPeriodic() {
	}
}
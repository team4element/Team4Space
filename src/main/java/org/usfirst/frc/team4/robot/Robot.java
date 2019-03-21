package org.usfirst.frc.team4.robot;

import org.usfirst.frc.team4.robot.commands.auton.DoNothing;
import org.usfirst.frc.team4.robot.constants.ControllerConstants;
import org.usfirst.frc.team4.robot.subsystems.Arm;
import org.usfirst.frc.team4.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4.robot.subsystems.HatchHook;
import org.usfirst.frc.team4.robot.subsystems.Intake;
import org.usfirst.frc.team4.robot.subsystems.Ramp;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {
	public static OI m_oi;
	public static DriveTrain m_driveTrain;
	public static Arm m_arm;
	public static HatchHook m_hatchHook;
	public static Intake m_intake;
	public static Ramp m_ramp;


	Command m_autonomousCommand;

	@Override
	public void robotInit() {

		
		// Initializing USB Camera (in the RoboRio) with preferred settings
	
		UsbCamera cam1 = CameraServer.getInstance().startAutomaticCapture(0);	
		cam1.setFPS(20);
		cam1.setResolution(160, 120);

		UsbCamera cam2 = CameraServer.getInstance().startAutomaticCapture(1);
		cam2.setFPS(20);
		cam2.setResolution(160, 120);
			
			
			


		ControllerConstants.init();
		m_driveTrain = new DriveTrain();
		m_arm = new Arm();
		m_hatchHook = new HatchHook();
		m_intake = new Intake();
		m_ramp = new Ramp();
		m_oi = new OI();

		Robot.m_arm.log();

	}

	@Override
	public void disabledInit() {
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


		// m_autonomousCommand = new AutoChooser().getSelectedAuto();
		m_autonomousCommand = new DoNothing();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

	}

	@Override
	public void teleopInit() {
		
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
			
		}
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		Robot.m_arm.log();
	}

	@Override
	public void testPeriodic() {
	}
}
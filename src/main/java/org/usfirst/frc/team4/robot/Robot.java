package org.usfirst.frc.team4.robot;

import org.usfirst.frc.team4.robot.commands.automodes.VisionTurn;
import org.usfirst.frc.team4.robot.commands.automodes.tune.TuneDistance;
import org.usfirst.frc.team4.robot.commands.automodes.tune.TuneTurn;
import org.usfirst.frc.team4.robot.constants.ChassisConstants;
import org.usfirst.frc.team4.robot.constants.ControllerConstants;
import org.usfirst.frc.team4.robot.constants.LimelightConstants;
import org.usfirst.frc.team4.robot.constants.TrajectoryConstants;
import org.usfirst.frc.team4.robot.subsystems.Arm;
import org.usfirst.frc.team4.robot.subsystems.Chassis;
import org.usfirst.frc.team4.robot.subsystems.Intake;
import org.usfirst.frc.team4.robot.subsystems.Limelight;
import org.usfirst.frc.team4.robot.subsystems.Pneumatics;
import org.usfirst.frc.team4.robot.subsystems.Ramp;
import org.usfirst.frc.team4.robot.utilities.trajectory.PathFinder;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class Robot extends TimedRobot {
	public static Waypoint[] points;
	
	public static Trajectory trajectory;

	public static TankModifier modifier;

	public static Notifier followNotifier;

	public static EncoderFollower left;
	public static EncoderFollower right;



	public static OI m_oi;
	public static Chassis m_chassis;
	public static Arm m_arm;
	public static Pneumatics m_pneumatics;
	public static Intake m_intake;
	public static Limelight m_limelight;
	public static Ramp m_ramp;
	public static PathFinder m_pathFinder;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

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
		// m_pneumatics = new Pneumatics();x
		m_intake = new Intake();
		m_limelight = new Limelight();
		m_ramp = new Ramp();
		m_oi = new OI();
		m_pathFinder = new PathFinder();

 		//Tuning Automodes
		m_chooser.addOption("Tune Turn", new TuneTurn());
		m_chooser.addOption("Tune Drive", new TuneDistance());
		m_chooser.addOption("Tune vision", new VisionTurn());
		// m_chooser.addOption("Pathfinder Test", new PathfinderTest());


		//ETC Auto Modeds
		// m_chooser.setDefaultOption("No Command", new DoNothing());

		// SmartDashboard.putData("Auto mode", m_chooser);

		
		
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
		points = new Waypoint[]{
			new Waypoint(30, 60, 0),
			new Waypoint(150, 60, 0)
		};

		
			Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 50, TrajectoryConstants.kV, TrajectoryConstants.kA, 0);
			
			trajectory = Pathfinder.generate(points, config);
		
			System.out.println(points);

			
	  
	  
		TankModifier modifier = new TankModifier(trajectory).modify(ChassisConstants.wheelbase_width);
			left = new EncoderFollower(modifier.getLeftTrajectory());
			right = new EncoderFollower(modifier.getRightTrajectory());

			 followNotifier = new Notifier(this::followPath);
			 followNotifier.startPeriodic(trajectory.get(1).dt);
			 
			
  

    
		// m_autonomousCommand = m_chooser.getSelected();
		// m_autonomousCommand = new PathfinderTest();
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}

		Robot.m_chassis.reset();
	}

	private void followPath(){
		if(left.isFinished() || right.isFinished()){
			followNotifier.stop();
		}else{
		double lPower = left.calculate(m_chassis.getRawLeftEncoder());
		double rPower= right.calculate(m_chassis.getRawRightEncoder());
		System.out.println("Right Power is: "+ rPower);
		System.out.println("Left Power is: " +lPower);
		double gyro_heading = Robot.m_chassis.getGyro();
		double desired_heading = Pathfinder.r2d(left.getHeading());  
  
		double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
	   
		double turn = 0.8 * (-1.0/80.0) * angleDifference;
		// Robot.m_chassis.setPower(lPower + turn , rPower - turn);
		}

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
		followNotifier.stop();
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
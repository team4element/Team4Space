package org.usfirst.frc.team4.robot.constants;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * Constants for chassis subsystem
 */
public class ChassisConstants {

	// Motors
	public static final int MOTOR_LEFT_FRONT = 0;
	public static final int MOTOR_LEFT_MIDDLE = 1;
	public static final int MOTOR_LEFT_REAR = 2;
	public static final int MOTOR_RIGHT_FRONT = 3;
	public static final int MOTOR_RIGHT_MIDDLE = 4;
	public static final int MOTOR_RIGHT_REAR = 5;
	
	//Encoder Constants
	public static double kWheelDiameter = 6.0;
	public static double gearRatio = (.64);
	public static double circumference = (kWheelDiameter * Math.PI);
	public static double ticksPerRevolution = 4096;
	public static double kTicksPerFoot = (ticksPerRevolution*gearRatio) / Math.PI / 2;
	public static double wheelbase_width = 1.75;


	public static Waypoint[] testPoints = new Waypoint[] {
		new Waypoint(-4, 0, Pathfinder.d2r(-45)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
		new Waypoint(-1, -0, 0),                        // Waypoint @ x=-2, y=-2, exit angle=0 radians
		new Waypoint(0, 0, 0)                           // Waypoint @ x=0, y=0,   exit angle=0 radians
	};

	public static Trajectory.Config testConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);

	// Generate the trajectory
	public static Trajectory testTrajectory = Pathfinder.generate(testPoints, testConfig);


	// Create the Modifier Object
	TankModifier modifier = new TankModifier(testTrajectory).modify(wheelbase_width);



	Trajectory left  = modifier.getLeftTrajectory();       // Get the Left Side
	Trajectory right = modifier.getRightTrajectory();      // Get the Right Side
	
	
	
		
}

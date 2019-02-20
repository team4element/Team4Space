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
	public static double kTicksPerInch = kTicksPerFoot / 12;
	public static double wheelbase_width = 1.75;


	//Velocity Constants
	public static double kLeftForwardStatic = 0.4064;
	public static double kLeftBackwardStatic = 0.3760;
	public static double kRightForwardStatic = 0.4104;
	public static double kRightBackwardStatic = 0.3822;

	public static double kLeftStatic = (kLeftForwardStatic + kLeftBackwardStatic)/2;
	public static double kRightStatic = (kRightForwardStatic + kRightBackwardStatic)/2;	
		
}

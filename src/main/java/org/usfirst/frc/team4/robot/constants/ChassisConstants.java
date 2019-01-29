package org.usfirst.frc.team4.robot.constants;

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
	public static double kWheelDiameter = 4.0;
	public static double gearRatio = (.64);
	public static double circumference = (kWheelDiameter * Math.PI);
	public static int ticksPerRevolution = 1024;
	
	public static double slowDownRate = 0.95;

	public static final int HIGHGEAR_IDX= 0;
	public static final int LOWGEAR_IDX= 1;

	public static double PHYSICAL_MAX_LOW_SPEED_TICKS = 700;
	public static double PHYSICAL_MAX_HIGH_SPEED_TICKS = 1300;

	public static final double wheelbase_width = 0.7;
	
		
}

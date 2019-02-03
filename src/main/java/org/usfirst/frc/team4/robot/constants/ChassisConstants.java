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
	public static double kWheelDiameter = 6.0;
	public static double gearRatio = (.64);
	public static double circumference = (kWheelDiameter * Math.PI);
	public static double ticksPerRevolution = 4096.0; 
	
	
		
}

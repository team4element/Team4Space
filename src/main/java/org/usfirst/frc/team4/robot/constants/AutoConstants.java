package org.usfirst.frc.team4.robot.constants;
/**
 * Constants for auton 
 */
public class AutoConstants {
	public final static double WHOLE_ROBOT = 30.5;
	public final static double CENTER_OF_ROBOT = WHOLE_ROBOT / 2.0;
	


	//Drive forward
	public static final double drivekP = .03;
	public static final double drivekI = 0;
	public static final double drivekD = .38;

	//Drive Angle
	public static final double angleKP = .07;
	public static final double angleKI = 0;
	public static final double angleKD = .39;

	//Arm Angle
	public static final double armkP = 0;
	public static final double armkI = 0;
	public static final double armkD = 0;

	//Synchronous PID distances in inches
	public static final double DISTANCE_TO_LINE = 220.25;
	public static final double HALF_DISTANCE_TO_LINE = 220.25/2;
	public static final double HALF_DISTANCE_TO_TARGET = 60.0;

	public static final double DISTANCE_FRONT_TARGET = 40.5;
	public static final double DISTANCE_MIDDLE_TARGET = 40.5 + 21.75;

	public static final int ANGLE_FOR_LEFT = -10;
	public static final int ANGLE_FOR_RIGHT = 10;

	public static final int ANGLE_FOR_MIDDLE_LEFT = -13;
	public static final int ANGLE_FOR_MIDDLE_RIGHT = 13;

	public static final int TURN_RIGHT = 90;
	public static final int TURN_LEFT = -90;

	public static final int TURN_RIGHT_EXTRA = 90+10;
	public static final int TURN_LEFT_EXTRA = -90-10;

	public static final int TURN_SLIGHTLY_LEFT = -10;
	public static final int TURN_SLIGHTLY_RIGHT = 10;

	public static final int STRAIGHT = 0;
}

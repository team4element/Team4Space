package org.usfirst.frc.team4.robot.constants;
/**
 * Constants for auton 
 */
public class AutoConstants {

	//Drive forward
	public static final double drivekP = .03;
	public static final double drivekI = 0;
	public static final double drivekD = .38;

	//Drive Angle
	public static final double anglekP = .07;
	public static final double anglekI = 0;
	public static final double anglekD = .39;

	//Arm Angle
	public static final double armkP = 0;
	public static final double armkI = 0;
	public static final double armkD = 0;

	//Motion Profiling
	public static final double kRotP = 0.5;
	public static final double kModifier = .840500551;
	public static final double maxVelocity = 33.8;//30.546765878497643934859019815506;  //Original was 30; Higher Value is smaller speed because 1/maxVelcity
	public static final double maxAcceleration = 65; //69.455488765954816577556633804123;
	public static final double kV = (1/maxVelocity)*kModifier;
	public static final double kA = 1/maxAcceleration;
	public static final double kP = 0;
	public static final double kI = 0;
	public static final double kD = 0;



	public enum ePosition {
		Left, Right, Middle;

	}
	public enum eLevel {
		One, Two;
	}

	public enum eTarget {
		Front, Middle, Back;
	}
}
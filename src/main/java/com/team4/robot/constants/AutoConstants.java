package com.team4.robot.constants;

/**
 * Add your docs here.
 */
public class AutoConstants {

	public static final double kArmP = .6;
	public static final double kArmI = 0;
	public static final double kArmD = .01;

	public static final double kDriveTurnP = .18;
	public static final double kDriveTurnI = 0;
	public static final double kDriveTurnD = .15;

   	//Gains for left side
	public static final double kDriveLeftVelocityKp = .9;
	public static final double kDriveLeftVelocityKi = 0.0; //.005
	public static final double kDriveLeftVelocityKd = 0.004; //1.6
	public static final double kDriveLeftVelocityKf = 0.17;

	public static final double kDriveRightVelocityKp = .9;
	public static final double kDriveRightVelocityKi = 0.000; //0.00014
	public static final double kDriveRightVelocityKd = 1.2; //.005
	public static final double kDriveRightVelocityKf = 0.16;

	public static final int kDriveLeftVelocityIZone = 0;
	public static final int kDriveRightVelocityIZone = 0; //1
	public static final double kDriveVelocityRampRate = 0.005;
	public static final double kDriveMaxVelocity = 712; // rpm
	public static final double kDriveMaxAccel = 450; // rpm/s
	public static final double kDriveMaxSetpoint = 6.0 * 12.0; // 6 fps
}

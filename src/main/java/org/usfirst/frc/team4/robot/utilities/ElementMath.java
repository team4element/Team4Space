package org.usfirst.frc.team4.robot.utilities;

/**
 * General Math Equations we use 
 */

public class ElementMath {

	// Higher sensitivity on joystick
	public static double squareInput(double input) {
		return Math.pow(input, 2);
	}

	// Higher sensitivity on joystick
	public static double cubeInput(double input) {
		return Math.pow(input, 3);
	}

	// Deadband for TeleOp Drive
	public static double handleDeadband(double val, double deadband) {
		return (Math.abs(val) > Math.abs(deadband) ? val : 0.0);
	}

	public static double ticksToInches(double ticks, double circumference, double gearRatio,
			double TicksPerRevolution) {
		return ticks * ((circumference * gearRatio) / TicksPerRevolution);

	}

}

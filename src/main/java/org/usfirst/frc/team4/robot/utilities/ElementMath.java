package org.usfirst.frc.team4.robot.utilities;

/**
 * General math equations we use 
 */

public class ElementMath {

	// Higher sensitivity on joystick
	public static double squareInput(double input) {
		return Math.pow(input, 2) * input/Math.abs(input);
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
	public static double visionDistance(double h1, double h2, double a1, double a2){
		return (h1-h2)/Math.tan(a1+a2);
	}

}

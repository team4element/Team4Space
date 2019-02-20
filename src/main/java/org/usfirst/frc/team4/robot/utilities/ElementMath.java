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
	
	public static double ticksToFeet(double ticks, double circumference, double gearRatio,
	double TicksPerRevolution) {
		return ticks * ((circumference * gearRatio) / TicksPerRevolution)/12;

	}
	public static double distanceFormula(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2));

	}
	public static double threshold(double value, double minimum, double maximum){
		double sign = Math.signum(value);
		if (Math.abs(value) < Math.abs(minimum)){
			value = minimum * sign;
		}else if (Math.abs(value) > Math.abs(maximum)){
			value = maximum * sign;
		}
		return value;
	}

	//Convert Inches to Meters
	public static double inchesToMeters(double inches){
		return inches * 0.0254;
	}
}
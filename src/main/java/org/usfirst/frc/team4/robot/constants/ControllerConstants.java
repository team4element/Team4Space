package org.usfirst.frc.team4.robot.constants;
/**
 * Constants for Controllers
 */
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class ControllerConstants {

	public static final int CONTROLLER_DRIVE = 0;
	public static final int CONTROLLER_OPERATOR = 1;

	// Button, Trigger and Axis Constants
	public static final int BUTTON_A = 1;
	public static final int BUTTON_B = 2;
	public static final int BUTTON_X = 3;
	public static final int BUTTON_Y = 4;

	public static final int BUMPER_LEFT = 5;
	public static final int BUMPER_RIGHT = 6;

	public static final int TRIGGER_LEFT = 2;
	public static final int TRIGGER_RIGHT = 3;
	public static final int BUTTON_LEFT_AXIS = 9;
	public static final int BUTTON_RIGHT_AXIS = 10;
	public static final int BUTTON_SELECT = 7;
	public static final int BUTTON_START = 8;

	public static final int AXIS_LEFT_Y = 1;
	public static final int AXIS_LEFT_X = 0;

	public static final int AXIS_RIGHT_Y = 5;
	public static final int AXIS_RIGHT_X = 4;

	public static final int POV_TOP = 0;
	public static final int POV_BOT = 180;

	public static final int POV_LEFT = 270;
	public static final int POV_RIGHT = 90;

	// Drive Controller
	public static Joystick driveController;

	public static JoystickButton driveA;
	public static JoystickButton driveB;
	public static JoystickButton driveX;
	public static JoystickButton driveY;

	public static JoystickButton driveLeftBumper;
	public static JoystickButton driveRightBumper;

	public static JoystickButton driveLeftTrigger;
	public static JoystickButton driveRightTrigger;

	public static JoystickButton driveLeftButton;
	public static JoystickButton driveRightButton;

	public static JoystickButton driveSelect;
	public static JoystickButton driveStart;

	public static JoystickButton drivePOVTop;
	public static JoystickButton drivePOVBot;

	public static JoystickButton drivePOVLeft;
	public static JoystickButton drivePOVRight;

	// Operator Controller
	public static Joystick operatorController;

	public static JoystickButton operatorA;
	public static JoystickButton operatorB;
	public static JoystickButton operatorX;
	public static JoystickButton operatorY;

	public static JoystickButton operatorLeftBumper;
	public static JoystickButton operatorRightBumper;

	public static JoystickButton operatorLeftTrigger;
	public static JoystickButton operatorRightTrigger;

	public static JoystickButton operatorLeftButton;
	public static JoystickButton operatorRightButton;

	public static JoystickButton operatorSelect;
	public static JoystickButton operatorStart;

	public static JoystickButton operatorPOVTop;
	public static JoystickButton operatorPOVBot;

	public static JoystickButton operatorPOVLeft;
	public static JoystickButton operatorPOVRight;

	// Joystick and Button Initializer
	public static void init() {
		// Drive Controller
		driveController = new Joystick(CONTROLLER_DRIVE);

		driveA = new JoystickButton(driveController, BUTTON_A);
		driveB = new JoystickButton(driveController, BUTTON_B);
		driveX = new JoystickButton(driveController, BUTTON_X);
		driveY = new JoystickButton(driveController, BUTTON_Y);

		driveLeftBumper = new JoystickButton(driveController, BUMPER_LEFT);
		driveRightBumper = new JoystickButton(driveController, BUMPER_RIGHT);

		driveLeftTrigger = new JoystickButton(driveController, TRIGGER_LEFT);
		driveRightTrigger = new JoystickButton(driveController, TRIGGER_RIGHT);

		driveLeftButton = new JoystickButton(driveController, BUTTON_LEFT_AXIS);
		driveRightButton = new JoystickButton(driveController, BUTTON_RIGHT_AXIS);

		driveSelect = new JoystickButton(driveController, BUTTON_SELECT);
		driveStart = new JoystickButton(driveController, BUTTON_START);

		drivePOVTop = new JoystickButton(operatorController, POV_TOP);
		drivePOVBot = new JoystickButton(operatorController, POV_BOT);

		drivePOVLeft = new JoystickButton(operatorController, POV_LEFT);
		drivePOVRight = new JoystickButton(operatorController, POV_RIGHT);

		// Operator Controller
		operatorController = new Joystick(CONTROLLER_OPERATOR);

		operatorA = new JoystickButton(operatorController, BUTTON_A);
		operatorB = new JoystickButton(operatorController, BUTTON_B);
		operatorX = new JoystickButton(operatorController, BUTTON_X);
		operatorY = new JoystickButton(operatorController, BUTTON_Y);

		operatorLeftBumper = new JoystickButton(operatorController, BUMPER_LEFT);
		operatorRightBumper = new JoystickButton(operatorController, BUMPER_RIGHT);

		operatorLeftTrigger = new JoystickButton(operatorController, TRIGGER_LEFT);
		operatorRightTrigger = new JoystickButton(operatorController, TRIGGER_RIGHT);

		operatorLeftButton = new JoystickButton(operatorController, BUTTON_LEFT_AXIS);
		operatorRightButton = new JoystickButton(operatorController, BUTTON_RIGHT_AXIS);

		operatorSelect = new JoystickButton(operatorController, BUTTON_SELECT);
		operatorStart = new JoystickButton(operatorController, BUTTON_START);

		operatorPOVTop = new JoystickButton(operatorController, POV_TOP);
		operatorPOVBot = new JoystickButton(operatorController, POV_BOT);

		operatorPOVLeft = new JoystickButton(operatorController, POV_LEFT);
		operatorPOVRight = new JoystickButton(operatorController, POV_RIGHT);
	}

}
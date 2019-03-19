
package org.usfirst.frc.team4.robot;


import org.usfirst.frc.team4.robot.commands.DeployRamp;
import org.usfirst.frc.team4.robot.commands.PulseHatchHook;
import org.usfirst.frc.team4.robot.commands.ToggleCompressor;
import org.usfirst.frc.team4.robot.commands.PID.ArmPID;
import org.usfirst.frc.team4.robot.constants.ControllerConstants;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is usually used for declaring what buttons do things.
 */
public class OI {

	public double leftX(Joystick cont) {
		return cont.getRawAxis(ControllerConstants.AXIS_LEFT_X);
	}

	public double leftY(Joystick cont) {
		return cont.getRawAxis(ControllerConstants.AXIS_LEFT_Y);
	}

	public double rightX(Joystick cont) {
		return cont.getRawAxis(ControllerConstants.AXIS_RIGHT_X);
	}

	public double rightY(Joystick cont) {
		return cont.getRawAxis(ControllerConstants.AXIS_RIGHT_Y);
	}

	public double leftTrigger(Joystick cont) {
		return cont.getRawAxis(ControllerConstants.TRIGGER_RIGHT);
	}

	public OI() {

		//Driver

		ControllerConstants.driveStart.whenPressed(new DeployRamp());
		

		//Operator
		ControllerConstants.operatorA.toggleWhenPressed(new PulseHatchHook());
		ControllerConstants.operatorB.whileHeld(new ArmPID((5.2))); // Placing Hatch Position
		ControllerConstants.operatorY.whileHeld(new ArmPID(0)); //Shooting Rocket Position
		ControllerConstants.operatorX.whileHeld(new ArmPID(6.83)); // Shooting Cargo Position
		ControllerConstants.operatorStart.whileHeld(new ArmPID(1.39)); // Ball Pick Up Position
		ControllerConstants.operatorSelect.toggleWhenPressed(new ToggleCompressor());

		

	}
}

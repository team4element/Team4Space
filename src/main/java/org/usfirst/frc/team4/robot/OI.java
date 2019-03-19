
package org.usfirst.frc.team4.robot;


import org.usfirst.frc.team4.robot.commands.DeployRamp;
import org.usfirst.frc.team4.robot.commands.PulseHatchRelease;
import org.usfirst.frc.team4.robot.commands.ReduceTurnSpeed;
import org.usfirst.frc.team4.robot.commands.ToggleCamera;
import org.usfirst.frc.team4.robot.commands.ToggleCompressor;
import org.usfirst.frc.team4.robot.commands.ToggleLED;
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

		// ControllerConstants.driveA.whileHeld(new ReduceTurnSpeed());
		//ControllerConstants.driveB.toggleWhenPressed(new ToggleLED());
		//ControllerConstants.driveX.toggleWhenPressed(new ToggleCamera());
		ControllerConstants.driveStart.whenPressed(new DeployRamp());
		

		//Operator
		ControllerConstants.operatorA.toggleWhenPressed(new PulseHatchRelease());
		ControllerConstants.operatorB.whileHeld(new ArmPID((5.2))); // Hatch Position
		ControllerConstants.operatorY.whileHeld(new ArmPID(3.25)); // Shoot Position
		ControllerConstants.operatorX.whileHeld(new ArmPID(6.83)); // Back Position
		ControllerConstants.operatorStart.whileHeld(new ArmPID(1.39)); // Back Position
		ControllerConstants.operatorSelect.toggleWhenPressed(new ToggleCompressor());

		

	}
}

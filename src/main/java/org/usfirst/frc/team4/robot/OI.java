
package org.usfirst.frc.team4.robot;


import org.usfirst.frc.team4.robot.commands.ArmFloorPosition;
import org.usfirst.frc.team4.robot.commands.ArmHatchPosition;
import org.usfirst.frc.team4.robot.commands.ArmShootPosition;
import org.usfirst.frc.team4.robot.commands.DeployRamp;
import org.usfirst.frc.team4.robot.commands.PulseHatchRelease;
import org.usfirst.frc.team4.robot.commands.TeleOPVision;
import org.usfirst.frc.team4.robot.commands.ToggleCamera;
import org.usfirst.frc.team4.robot.commands.ToggleCompressor;
import org.usfirst.frc.team4.robot.commands.ToggleLED;
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
		ControllerConstants.driveA.whileHeld(new TeleOPVision());
		ControllerConstants.driveB.toggleWhenPressed(new ToggleLED());
		ControllerConstants.driveX.toggleWhenPressed(new ToggleCamera());
		ControllerConstants.driveStart.toggleWhenPressed(new DeployRamp());
		

		//Operator
		ControllerConstants.operatorA.whenPressed(new PulseHatchRelease());
		ControllerConstants.operatorY.whileHeld(new ArmHatchPosition());
		ControllerConstants.operatorB.whileHeld(new ArmShootPosition());
		ControllerConstants.operatorX.whileHeld(new ArmFloorPosition());
		ControllerConstants.operatorStart.toggleWhenPressed(new ToggleCompressor());

		

	}
}

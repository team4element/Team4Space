/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4.robot;


import org.usfirst.frc.team4.robot.commands.DeployRamp;
import org.usfirst.frc.team4.robot.commands.PulsePistons;
import org.usfirst.frc.team4.robot.commands.TeleOPVision;
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

		//Operator
		ControllerConstants.operatorA.whenPressed(new PulsePistons());
		ControllerConstants.operatorB.toggleWhenPressed(new ToggleCompressor());
		ControllerConstants.operatorStart.toggleWhenPressed(new DeployRamp());

	}
}

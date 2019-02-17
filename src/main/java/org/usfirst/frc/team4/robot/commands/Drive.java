package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.ControllerConstants;
import org.usfirst.frc.team4.robot.utilities.ElementMath;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	This command is for manually driving
 */
public class Drive extends Command {

	public Drive() {
		requires(Robot.m_driveTrain);
	}

	protected void initialize() {
		Robot.m_driveTrain.reset();
	}

	protected void execute() {
		double rotationPower = ElementMath.handleDeadband(-ElementMath.cubeInput(-Robot.m_oi.leftY(ControllerConstants.driveController)), .05);
		double straightPower = ElementMath.handleDeadband(ElementMath.cubeInput(Robot.m_oi.rightX(ControllerConstants.driveController)) * .5, .05);
		Robot.m_driveTrain.setPower(straightPower + rotationPower, straightPower - rotationPower);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.m_driveTrain.setPower(0, 0);
	}

	protected void interrupted() {
	}
}
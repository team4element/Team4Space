package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.ControllerConstants;
import org.usfirst.frc.team4.robot.utilities.ElementMath;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	This command is for manually driving
 */
public class Drive extends Command {

	double rotationPower;
	double straightPower;

	public Drive() {
		requires(Robot.m_driveTrain);
	}

	protected void initialize() {
		Robot.m_driveTrain.reset();
		Robot.m_driveTrain.isReduced = false;
	}

	protected void execute() {
			 rotationPower = ElementMath.squareInput(-Robot.m_oi.leftY(ControllerConstants.driveController));
			 straightPower = ElementMath.squareInput(Robot.m_oi.rightX(ControllerConstants.driveController) * .75);
			Robot.m_driveTrain.setPower(straightPower + rotationPower, straightPower - rotationPower);
		// Robot.m_driveTrain.setPower(Robot.m_oi.leftY(ControllerConstants.driveController), Robot.m_oi.rightY(ControllerConstants.driveController));
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
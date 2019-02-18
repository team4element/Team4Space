package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.ControllerConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command manually moves the arm
 */
public class ArmMove extends Command {

	public ArmMove() {
		requires(Robot.m_arm);
	}

	protected void initialize() {
		
	}

	protected void execute() {
		Robot.m_arm.setPower(Robot.m_oi.leftY(ControllerConstants.operatorController) * .75);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.m_arm.setPower(0);
	}

	protected void interrupted() {
	}
}

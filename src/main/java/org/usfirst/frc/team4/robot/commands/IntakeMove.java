package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.ControllerConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will move the intake on the arms
 */
public class IntakeMove extends Command {

	public IntakeMove() {
		requires(Robot.m_intake);
	}

	protected void initialize() {

	}

	protected void execute() {
		// In-take
		if (ControllerConstants.operatorLeftBumper.get()) {
			Robot.m_intake.setMotorsSpeed(.50);

			// Out-take
		} else if (ControllerConstants.operatorRightBumper.get()) {
			Robot.m_intake.setMotorsSpeed(-.50);
		} else {
			Robot.m_intake.motorsStop();
		}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {

	}
}

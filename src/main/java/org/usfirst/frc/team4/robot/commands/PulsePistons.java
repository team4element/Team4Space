package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will Pulse the pistons on the arms.
 * We are using single action pistons so they pulse back mechanically
 */
public class PulsePistons extends Command {

	public PulsePistons() {
		requires(Robot.m_pneumatics);
	}

	protected void initialize() {
		Robot.m_pneumatics.pulseSolenoids();

	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {

	}

	protected void interrupted() {

	}

}

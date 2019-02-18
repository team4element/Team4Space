package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will pulse the pistons on the arms.
 * We are using single action pistons so they pulse back mechanically
 */
public class PulseHatchRelease extends Command {

	public PulseHatchRelease() {
		requires(Robot.m_hatchRelease);
	}

	protected void initialize() {
		Robot.m_hatchRelease.pulseSolenoids();

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

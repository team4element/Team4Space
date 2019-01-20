package org.usfirst.frc.team4.robot.commands.automodes;

import org.usfirst.frc.team4.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	The auton command we use when either our robot, or code is broken.
 */
public class DoNothing extends Command {

    public DoNothing() {
         requires(Robot.m_chassis);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.m_chassis.Brake();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

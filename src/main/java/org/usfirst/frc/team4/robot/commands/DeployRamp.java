package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *	This command deploys the ramp
 */
public class DeployRamp extends Command {

    public DeployRamp() {
        requires(Robot.m_ramp);
    }

    protected void initialize() {
    	Robot.m_ramp.setPistons(Value.kForward);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.m_ramp.setPistons(Value.kOff);
    }

    protected void interrupted() {
    	Robot.m_ramp.setPistons(Value.kReverse);
    }
}

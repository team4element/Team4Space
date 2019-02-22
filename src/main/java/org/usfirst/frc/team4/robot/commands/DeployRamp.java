package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command deploys the ramp
 */
public class DeployRamp extends Command {

    public DeployRamp() {
        requires(Robot.m_ramp);
    }

    protected void initialize() {
        Robot.m_ramp.pulsePistons();
        // Robot.m_ramp.movePistons(Value.kReverse);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
      
        // Robot.m_ramp.movePistons(Value.kForward);
    }
}

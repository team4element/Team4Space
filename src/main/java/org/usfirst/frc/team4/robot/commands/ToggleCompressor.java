package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will toggle the compressor
 */
public class ToggleCompressor extends Command {

    public ToggleCompressor() {
         requires(Robot.m_hatchRelease);
    }

    protected void initialize() {
    	Robot.m_hatchRelease.compressorStart();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.m_hatchRelease.compressorStop();
    }

    protected void interrupted() {
    }
}

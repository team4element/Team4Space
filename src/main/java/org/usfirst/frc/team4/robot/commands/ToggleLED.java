package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.LimelightConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will Toggle the LED's on the Limelight
 */
public class ToggleLED extends Command {

    public ToggleLED() {
        requires(Robot.m_limelight);
    }

    protected void initialize() {
    Robot.m_limelight.setLEDMode(LimelightConstants.eLEDMode.ON);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    	Robot.m_limelight.setLEDMode(LimelightConstants.eLEDMode.OFF);
    }
}

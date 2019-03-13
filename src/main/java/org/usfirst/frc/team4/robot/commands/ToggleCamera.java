package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.LimelightConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Changes camera modes on the Limelight from vision to not vision
 */
public class ToggleCamera extends Command {

    public ToggleCamera() {
        requires(Robot.m_limelight);
    }

    protected void initialize() {
    	Robot.m_limelight.setCamMode(LimelightConstants.CameraMode.VISION_PROCESSING);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        Robot.m_limelight.setCamMode(LimelightConstants.CameraMode.DRIVERSTATION_FEEDBACK);
        
    }
}

package org.usfirst.frc.team4.robot.utilities;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.AutoConstants;
import org.usfirst.frc.team4.robot.constants.LimelightConstants;
import org.usfirst.frc.team4.robot.subsystems.Limelight;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The auto we use for vision angle
 * Can be used for Tuning
 * Requires Limelight and Gyro
 */
public class VisionTurn extends Command {

	private SynchronusPID turn;
	private final Limelight limelight;

	public VisionTurn() {
		requires(Robot.m_driveTrain);
		limelight = Limelight.getInstance();

		turn = new SynchronusPID(AutoConstants.angleKP, AutoConstants.angleKI, AutoConstants.angleKD, false);
		turn.setSetpoint(0);

		SmartDashboard.putData("Vision Turn", turn);
	}

	protected void initialize() {
		Robot.m_driveTrain.reset();
		if(LimelightConstants.isLedOn == false){
			Robot.m_limelight.setLEDMode(LimelightConstants.eLEDMode.ON);
			LimelightConstants.isLedOn = true;
		}
		else if(LimelightConstants.isVisionMode == false){
				Robot.m_limelight.setCamMode(LimelightConstants.CameraMode.VISION_PROCESSING);
				LimelightConstants.isVisionMode = true;
		}
	}

	

	protected void execute() {
		double angle = turn.calculate(Limelight.getInstance().getTX());
		Robot.m_driveTrain.arcadeDrive(0, -angle);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		if(LimelightConstants.isLedOn == true){
			Robot.m_limelight.setLEDMode(LimelightConstants.eLEDMode.OFF);
			LimelightConstants.isLedOn = false;
		}
	}
	protected void interrupted() {
		end();
	}
}

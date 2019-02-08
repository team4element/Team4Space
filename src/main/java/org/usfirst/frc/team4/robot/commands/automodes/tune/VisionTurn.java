package org.usfirst.frc.team4.robot.commands.automodes.tune;
import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.AutoConstants;
import org.usfirst.frc.team4.robot.subsystems.Limelight;
import org.usfirst.frc.team4.robot.utilities.SynchronusPID;

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
		requires(Robot.m_chassis);
		limelight = Limelight.getInstance();

		turn = new SynchronusPID(AutoConstants.angleKP, AutoConstants.angleKI, AutoConstants.angleKD, false);
		turn.setSetpoint(0);

		SmartDashboard.putData("Vision Turn", turn);
	}

	protected void initialize() {
		Robot.m_chassis.reset();
//		Robot.m_limelight.setLEDMode(LimelightConstants.eLEDMode.ON);
//		Robot.m_limelight.setCamMode(LimelightConstants.CameraMode.VISION_PROCESSING);
	}

	protected void execute() {
		double angle = turn.calculate(Limelight.getInstance().getTX());
		Robot.m_chassis.arcadeDrive(0, -angle);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
//		Robot.m_limelight.setLEDMode(LimelightConstants.eLEDMode.OFF);
//		Robot.m_limelight.setCamMode(LimelightConstants.CameraMode.DRIVERSTATION_FEEDBACK);
	}

	protected void interrupted() {
//		Robot.m_limelight.setLEDMode(LimelightConstants.eLEDMode.OFF);
//		Robot.m_limelight.setCamMode(LimelightConstants.CameraMode.DRIVERSTATION_FEEDBACK);
	}
}

package org.usfirst.frc.team4.robot.commands.PID;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.AutoConstants;
import org.usfirst.frc.team4.robot.utilities.SynchronusPID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * PID controller for drive
 */
public class DrivePID extends Command {

	SynchronusPID distancePID;
	private SynchronusPID anglePID;
	private double gyro, distance;
	private boolean canGoForward = false;


	public DrivePID(double distance, double angle) {
		distancePID = new SynchronusPID(AutoConstants.drivekP, AutoConstants.drivekI, AutoConstants.drivekD, false);
		anglePID = new SynchronusPID(AutoConstants.angleKP, AutoConstants.angleKI, AutoConstants.angleKD, false);


		distancePID.setSetpoint(distance);
		anglePID.setSetpoint(angle);

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.m_driveTrain.reset();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SmartDashboard.putData("Distance Sync PID", distancePID);
		SmartDashboard.putData("Angle Sync PID", anglePID);

		if (!canGoForward) {
			gyro = Robot.m_driveTrain.getGyro();
			distance = Robot.m_driveTrain.getDistance();

			double straightPower = distancePID.calculate(distance);
			double rotationPower = anglePID.calculate(gyro);

			Robot.m_driveTrain.arcadeDrive(-straightPower * .5, rotationPower);
		}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.m_driveTrain.setPower(0, 0);
	}

	protected void interrupted() {
		end();
	}
}
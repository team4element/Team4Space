package org.usfirst.frc.team4.robot.commands.PID;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.AutoConstants;
import org.usfirst.frc.team4.robot.utilities.SynchronusPID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * PID Controller for angle
 */
public class AnglePID extends Command {

	private SynchronusPID pid;

	public AnglePID(double setpoint) {
		requires(Robot.m_driveTrain);

		pid = new SynchronusPID(AutoConstants.angleKP, AutoConstants.angleKI, AutoConstants.angleKD, false);
		pid.setSetpoint(setpoint);

		SmartDashboard.putData("Tune Turn Controller", pid);

	}

	protected void initialize() {
		Robot.m_driveTrain.reset();
	}

	protected void execute() {

		double angle = pid.calculate(Robot.m_driveTrain.getGyro());
		Robot.m_driveTrain.setPower(angle, angle);
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
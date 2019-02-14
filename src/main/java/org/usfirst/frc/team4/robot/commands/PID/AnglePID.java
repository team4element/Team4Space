package org.usfirst.frc.team4.robot.commands.PID;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.AutoConstants;
import org.usfirst.frc.team4.robot.utilities.SynchronusPID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Our PID Controller for AnglePID
 */
public class AnglePID extends Command {

	private SynchronusPID pid;

	public AnglePID(double setpoint) {
		requires(Robot.m_chassis);

		pid = new SynchronusPID(AutoConstants.angleKP, AutoConstants.angleKI, AutoConstants.angleKD, false);
		pid.setSetpoint(setpoint);

		SmartDashboard.putData("Tune Turn Controller", pid);

	}

	protected void initialize() {
		Robot.m_chassis.reset();
	}

	protected void execute() {

		double angle = pid.calculate(Robot.m_chassis.getGyro());
		Robot.m_chassis.setPower(angle, angle);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.m_chassis.setPower(0, 0);
	}

	protected void interrupted() {
		end();
	}
}
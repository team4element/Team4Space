package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.AutoConstants;
import org.usfirst.frc.team4.robot.constants.ControllerConstants;
import org.usfirst.frc.team4.robot.constants.LimelightConstants;
import org.usfirst.frc.team4.robot.subsystems.Limelight;
import org.usfirst.frc.team4.robot.utilities.ElementMath;
import org.usfirst.frc.team4.robot.utilities.SynchronusPID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command allows for vision to correct angle while manually 
 * driving forward. We have it set to Driver A button when held in OI
 */
public class TeleOPVision extends Command {

	private SynchronusPID pid;
	private final Limelight limelight;

	public TeleOPVision() {
		requires(Robot.m_driveTrain);
		limelight = Limelight.getInstance();
		
		pid = new SynchronusPID(AutoConstants.angleKP, AutoConstants.angleKI, AutoConstants.angleKD, false);
		pid.setSetpoint(0);

		SmartDashboard.putData("Tune Turn Controller", pid);

	}

	protected void initialize() {
		Robot.m_driveTrain.reset();
		Robot.m_limelight.setLEDMode(LimelightConstants.eLEDMode.ON);
		Robot.m_limelight.setCamMode(LimelightConstants.CameraMode.VISION_PROCESSING);
	}
	
	protected void execute() {
		double angle = pid.calculate(Limelight.getInstance().getTX());
		Robot.m_driveTrain.arcadeDrive(ElementMath.squareInput(-Robot.m_oi.leftY(ControllerConstants.driveController)), -angle);
		System.out.println(angle);
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

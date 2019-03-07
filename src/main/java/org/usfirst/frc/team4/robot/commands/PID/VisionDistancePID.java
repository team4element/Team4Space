package org.usfirst.frc.team4.robot.commands.PID;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.AutoConstants;
import org.usfirst.frc.team4.robot.constants.LimelightConstants;
import org.usfirst.frc.team4.robot.subsystems.Limelight;
import org.usfirst.frc.team4.robot.utilities.ElementMath;
import org.usfirst.frc.team4.robot.utilities.SynchronusPID;

import edu.wpi.first.wpilibj.command.Command;

public class VisionDistancePID extends Command {
  private SynchronusPID pid;

  private double target;

  public VisionDistancePID(double target) {
    requires(Robot.m_driveTrain);

    this.target = target;

    pid = new SynchronusPID(AutoConstants.drivekP, AutoConstants.drivekI, AutoConstants.drivekD, false);
    pid.setSetpoint(20);
  }

  // Called just before this Command runs the first time
  @Override
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

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double distance = pid.calculate(ElementMath.visionDistance(LimelightConstants.floorToCamera, target ,LimelightConstants.mountingAngle, Limelight.getInstance().getTY()));
    double angle = pid.calculate(Limelight.getInstance().getTX());
    Robot.m_driveTrain.arcadeDrive(distance, angle);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_driveTrain.setPower(0,0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}

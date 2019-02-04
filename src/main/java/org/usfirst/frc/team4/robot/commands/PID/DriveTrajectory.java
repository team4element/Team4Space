

package org.usfirst.frc.team4.robot.commands.PID;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.utilities.TrajectoryFollower;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Trajectory;

public class DriveTrajectory extends Command {
  
  private TrajectoryFollower m_controller;
  private double m_leftVelocity, m_rightVelocity, m_startTime, m_time, m_lastTime;

  public DriveTrajectory(Trajectory traj, int lookahead, Boolean goingForwards, double kP, double kD) {
    m_controller = new TrajectoryFollower(traj, lookahead, goingForwards, kP, kD);
    requires(Robot.m_chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    m_startTime = Timer.getFPGATimestamp();
    m_lastTime = Timer.getFPGATimestamp();
    m_time = Timer.getFPGATimestamp() - m_startTime;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    m_time = Timer.getFPGATimestamp() - m_startTime;
    m_controller.calculate(Robot.m_chassis.getXpos(), Robot.m_chassis.getYpos(), Robot.m_chassis.getGyro(), m_time - m_lastTime);
    Robot.m_chassis.setVelocityFPS(m_controller.getLeftVelocity(), m_controller.getRightVelocity());
    m_lastTime = m_time;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return m_controller.isFinished();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_chassis.Brake();
    }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
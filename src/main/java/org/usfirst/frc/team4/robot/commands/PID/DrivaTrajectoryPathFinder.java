package org.usfirst.frc.team4.robot.commands.PID;


import java.io.File;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.AutoConstants;
import org.usfirst.frc.team4.robot.constants.ChassisConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import org.usfirst.frc.team4.robot.utilities.DistanceFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class DrivaTrajectoryPathFinder extends Command {
  private Trajectory m_leftTrajectory, m_rightTrajectory, m_sourceTrajectory;
  private DistanceFollower m_leftFollower, m_rightFollower;
  private double m_leftOutput, m_rightOutput, m_turn, m_angularError;
  private TankModifier m_modifier;

  public DrivaTrajectoryPathFinder(Trajectory traj) {
    m_sourceTrajectory = traj;
    m_modifier = new TankModifier(m_sourceTrajectory);
    m_modifier.modify(ChassisConstants.wheelbase_width);    
    m_leftTrajectory = m_modifier.getLeftTrajectory();
    m_rightTrajectory = m_modifier.getRightTrajectory();
    requires(Robot.m_chassis);
  }

  public DrivaTrajectoryPathFinder(String file) {
    File traj = new File("/home/lvuser/deploy/output/" + file + ".pf1.csv");
    SmartDashboard.putBoolean("Source exists", true);
    m_sourceTrajectory = Pathfinder.readFromCSV(traj);
    File leftTraj = new File("/home/lvuser/deploy/output/" + file + ".left.pf1.csv");
    SmartDashboard.putBoolean("Left exists", true);
    m_leftTrajectory = Pathfinder.readFromCSV(leftTraj);
    File rightTraj = new File("/home/lvuser/deploy/output/" + file + ".right.pf1.csv");
    SmartDashboard.putBoolean("Right exists", true);
    m_rightTrajectory = Pathfinder.readFromCSV(rightTraj);
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    m_leftFollower = new DistanceFollower(m_leftTrajectory);
    m_rightFollower = new DistanceFollower(m_rightTrajectory);
    m_leftFollower.configurePIDVA(AutoConstants.kP, AutoConstants.kI, AutoConstants.kD,
    AutoConstants.kV, 0/*AutoConstants.maxAcceleration*/);
    m_rightFollower.configurePIDVA(AutoConstants.kP, AutoConstants.kI, AutoConstants.kD,
    AutoConstants.kV, 0/*AutoConstants.maxAcceleration*/);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    m_leftOutput = m_leftFollower.calculate(Robot.m_chassis.getLeftEncoderInches());/* + Chassis
    AutoConstants.kLeftStatic*/;
    m_rightOutput = -m_rightFollower.calculate(Robot.m_chassis.getRightEncoderInches());/* + Chassis
    AutoConstants.kRightStatic;*/

    m_angularError = Pathfinder.boundHalfDegrees(Pathfinder.r2d(-m_leftFollower.getHeading()) - Robot.m_chassis.getGyro());
    m_turn = /*0.8 * (-1.0/80.0) */ AutoConstants.kRotP * m_angularError;
    Robot.m_chassis.addDesiredVelocities(m_leftFollower.getSegment().velocity, m_rightFollower.getSegment().velocity);
    System.out.println("left Output: " + m_leftOutput);
    System.out.println("Right Output: " + m_rightOutput);
    Robot.m_chassis.setPower((m_leftOutput - m_turn), (m_rightOutput + m_turn));
    SmartDashboard.putNumber("Velocity", m_leftFollower.getSegment().velocity);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return m_rightFollower.isFinished() && m_leftFollower.isFinished();
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
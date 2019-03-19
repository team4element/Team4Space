

package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ReduceTurnSpeed extends Command {
  public ReduceTurnSpeed() {
      requires(Robot.m_driveTrain);
  }

  @Override
  protected void initialize() {
    
    Robot.m_driveTrain.isReduced =! Robot.m_driveTrain.isReduced;
  }

  @Override
  protected void execute() {
   
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
   }
}

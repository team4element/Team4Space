package org.usfirst.frc.team4.robot.commands.auton;

import org.usfirst.frc.team4.robot.constants.ControllerConstants;

import edu.wpi.first.wpilibj.command.Command;

public class EndAuto extends Command {
/**
 * Ends autonomous
 * We use it in comman groups 
 */

  public EndAuto() {
  }

  @Override
  protected void initialize() {
    ControllerConstants.driveX.get();
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}

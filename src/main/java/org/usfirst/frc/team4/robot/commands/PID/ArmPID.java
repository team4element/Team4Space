package org.usfirst.frc.team4.robot.commands.PID;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.AutoConstants;
import org.usfirst.frc.team4.robot.utilities.SynchronusPID;

import edu.wpi.first.wpilibj.command.Command;

/**
 * PID for the angle of the arm
 */

public class ArmPID extends Command {

  SynchronusPID armPID;

  public ArmPID(double setpoint) {
    requires(Robot.m_arm);

    armPID = new SynchronusPID(AutoConstants.armkP, AutoConstants.armkI, AutoConstants.armkD, false);

    armPID.setSetpoint(setpoint);
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected void execute() {
    double distance = Robot.m_arm.getPot();
    double output = armPID.calculate(distance);
    Robot.m_arm.setPower(output * .75);
}

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.m_arm.setPower(0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}

package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.commands.PID.ArmPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ArmFloorPosition extends CommandGroup {
  /**
   * Puts the arm in the position to pick up balls off the floor
   */
  public ArmFloorPosition() {
    if(Robot.m_arm.getPot() < 9.3){
      Robot.m_arm.setPower(0);
    }else{

    addSequential(new ArmPID(9.45));
    }
  }
}

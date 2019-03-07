package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.commands.PID.ArmPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ArmShootPosition extends CommandGroup {
  /**
   * Puts the arm arm in the position to shoor the ball 
   */
  public ArmShootPosition() {
    addSequential(new ArmPID(7.21));
  }
}


package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.commands.PID.ArmPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ArmHatchPosition extends CommandGroup {
  /**
   * Puts the arm in the position to score the hatch
   */
  public ArmHatchPosition() {
    addSequential(new ArmPID(5.6));
  }
}


package org.usfirst.frc.team4.robot.commands.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DoNothing extends CommandGroup {
  /**
   * Does Nothing
   */
  public DoNothing() {
    addSequential(new EndAuto());
  }
}

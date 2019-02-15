

package org.usfirst.frc.team4.robot.commands.automodes;

import org.usfirst.frc.team4.robot.commands.EndAuto;
import org.usfirst.frc.team4.robot.commands.PID.AnglePID;
import org.usfirst.frc.team4.robot.commands.PID.DrivePID;
import org.usfirst.frc.team4.robot.constants.AutoConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftToMiddle extends CommandGroup {
  /**
   * Starts in Left Position and targets the Middle Peg
   */
  public LeftToMiddle() {

  //Drives to line 
  addSequential(new DrivePID(-AutoConstants.CENTER_OF_ROBOT+AutoConstants.DISTANCE_TO_LINE , AutoConstants.STRAIGHT), 1.5);
  //Turns to -10 degree angle
  addSequential(new AnglePID(AutoConstants.ANGLE_FOR_LEFT), 1);
  //Drives to Middle Target
  addSequential(new DrivePID(-AutoConstants.CENTER_OF_ROBOT+AutoConstants.DISTANCE_MIDDLE_TARGET , AutoConstants.STRAIGHT), 1);
  //Turns 90 + 10
  addSequential(new AnglePID(AutoConstants.TURN_RIGHT_EXTRA), 1); 
  //End Auto
  addSequential(new EndAuto(), .5); 
  }
}
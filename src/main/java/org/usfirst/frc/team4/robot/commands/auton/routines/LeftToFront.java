
package org.usfirst.frc.team4.robot.commands.auton.routines;

import org.usfirst.frc.team4.robot.commands.EndAuto;
import org.usfirst.frc.team4.robot.commands.PID.AnglePID;
import org.usfirst.frc.team4.robot.commands.PID.DrivePID;
import org.usfirst.frc.team4.robot.constants.AutoConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftToFront extends CommandGroup {
  /**
   * Starts in left position and targets the front target
   */
  public LeftToFront() {
    //Drives to line 
    addSequential(new DrivePID(-AutoConstants.CENTER_OF_ROBOT+AutoConstants.DISTANCE_TO_LINE , AutoConstants.STRAIGHT), 1.5);
    //Turns to -10 degree angle
    addSequential(new AnglePID(AutoConstants.ANGLE_FOR_LEFT), 1);
    //Drives to Front Target
    addSequential(new DrivePID(-AutoConstants.CENTER_OF_ROBOT+AutoConstants.DISTANCE_FRONT_TARGET , AutoConstants.STRAIGHT), 1);
    //Turns 90 + 10
    addSequential(new AnglePID(AutoConstants.TURN_RIGHT_EXTRA), 1); 
    //End Auto
    addSequential(new EndAuto(), .5); 
  }
}

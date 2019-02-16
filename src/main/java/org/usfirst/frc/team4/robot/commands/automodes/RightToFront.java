
package org.usfirst.frc.team4.robot.commands.automodes;

import org.usfirst.frc.team4.robot.commands.EndAuto;
import org.usfirst.frc.team4.robot.commands.PID.AnglePID;
import org.usfirst.frc.team4.robot.commands.PID.DrivePID;
import org.usfirst.frc.team4.robot.constants.AutoConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightToFront extends CommandGroup {
  /**
   * Starts on the right, and ends on the front
   */
  public RightToFront() {
     //Drives to line 
     addSequential(new DrivePID(-AutoConstants.CENTER_OF_ROBOT+AutoConstants.DISTANCE_TO_LINE , AutoConstants.STRAIGHT), 1.5);
     //Turns to 10 degree angle
     addSequential(new AnglePID(AutoConstants.ANGLE_FOR_RIGHT), 1);
     //Drives to Front Target
     addSequential(new DrivePID(-AutoConstants.CENTER_OF_ROBOT+AutoConstants.DISTANCE_FRONT_TARGET , AutoConstants.STRAIGHT), 1);
     //Turns -90 - 10
     addSequential(new AnglePID(AutoConstants.TURN_LEFT_EXTRA), 1); 
     //End Auto
     addSequential(new EndAuto(), .5); 
  }
}

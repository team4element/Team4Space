package org.usfirst.frc.team4.robot.commands.automodes;

import org.usfirst.frc.team4.robot.commands.EndAuto;
import org.usfirst.frc.team4.robot.commands.PID.AnglePID;
import org.usfirst.frc.team4.robot.commands.PID.DrivePID;
import org.usfirst.frc.team4.robot.constants.AutoConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MiddleToLeft extends CommandGroup {
  /**
   * Starts in the middle and targets the left
   */
  public MiddleToLeft() {
   //Drives to half way line
    addSequential(new DrivePID(-AutoConstants.CENTER_OF_ROBOT+AutoConstants.HALF_DISTANCE_TO_LINE, AutoConstants.STRAIGHT), 1.5);
    //Turns -13 degrees
    addSequential(new AnglePID(AutoConstants.TURN_SLIGHTLY_LEFT), 1);
    //Drives closer to left Target
    addSequential(new DrivePID(-AutoConstants.CENTER_OF_ROBOT+AutoConstants.HALF_DISTANCE_TO_TARGET, AutoConstants.STRAIGHT), 1.5);
    //Ends Auto
    addSequential(new EndAuto(), .5);
  }
}

package org.usfirst.frc.team4.robot.utilities;

import org.usfirst.frc.team4.robot.commands.automodes.DoNothing;
import org.usfirst.frc.team4.robot.commands.automodes.LeftToFront;
import org.usfirst.frc.team4.robot.commands.automodes.LeftToMiddle;
import org.usfirst.frc.team4.robot.commands.automodes.MiddleToLeft;
import org.usfirst.frc.team4.robot.commands.automodes.MiddleToRight;
import org.usfirst.frc.team4.robot.commands.automodes.RightToFront;
import org.usfirst.frc.team4.robot.commands.automodes.RightToMiddle;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser {
    /**
     * This class handles choosers for the autonomous period.
     */


    SendableChooser<startingPosition> startingPositionChooser;
    SendableChooser<target> targetChooser;
   
    public AutoChooser() { 
        
        startingPositionChooser = new SendableChooser<>();
        startingPositionChooser.setDefaultOption("Center", startingPosition.CENTER);
        startingPositionChooser.addOption("Left", startingPosition.LEFT);
        startingPositionChooser.addOption("Right", startingPosition.RIGHT);
        
        targetChooser = new SendableChooser<>();
        targetChooser.setDefaultOption("None", target.NONE);
        targetChooser.addOption("Front", target.FRONT);
        targetChooser.addOption("Middle", target.MIDDLE);
        targetChooser.addOption("Back", target.BACK);
        
        SmartDashboard.putData("Starting Position", startingPositionChooser);
        SmartDashboard.putData("Target", targetChooser);
      
    }
    public target getTarget() {
        return targetChooser.getSelected();
    }

    public startingPosition getStartingPosition() {
        return startingPositionChooser.getSelected();
    }

    public CommandGroup getSelectedAuto() {
        if(getTarget() == target.NONE){
        return new DoNothing();}    
        else if(getStartingPosition() == startingPosition.LEFT && getTarget() == target.FRONT){
          return new LeftToFront();
        }
        else if(getStartingPosition() == startingPosition.LEFT && getTarget() == target.MIDDLE){
          return new LeftToMiddle();
        }
        else if(getStartingPosition() == startingPosition.RIGHT && getTarget() == target.FRONT){
          return new RightToFront();
        }
        else if(getStartingPosition() == startingPosition.RIGHT && getTarget() == target.MIDDLE){
          return new RightToMiddle();
        }
        else if(getStartingPosition() == startingPosition.CENTER && getTarget() == target.LEFT){
          return new MiddleToLeft();
        }
        else if(getStartingPosition() == startingPosition.CENTER && getTarget() == target.RIGHT){
          return new MiddleToRight();
        

        } else {
        return new CommandGroup();
        }
    }
    enum startingPosition {
        CENTER, LEFT, RIGHT;
    }

    enum target{
        FRONT, MIDDLE, BACK, NONE, LEFT, RIGHT;
        }
}
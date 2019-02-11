package org.usfirst.frc.team4.robot.utilities;

import org.usfirst.frc.team4.robot.commands.automodes.DoNothing;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser {

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
        return new DoNothing();
        // }
        // else if(getStartingPosition() == startingPosition.LEFT && getTarget() == target.FRONT){
        //   return new
        // }
        // else if(getStartingPosition() == startingPosition.LEFT && getTarget() == target.MIDDLE){
        //   return new
        // }
        // else if(getStartingPosition() == startingPosition.RIGHT && getTarget() == target.FRONT){
        //   return new
        // }
        
        // else if(getStartingPosition() == startingPosition.RIGHT && getTarget() == target.MIDDLE){
        //   return new
        // }
        // else if(getStartingPosition() == startingPosition.MIDDLE && getTarget() == target.LEFT){
        //   return new 
        // }
        // else if(getStartingPosition() == startingPosition.MIDDLE && getTarget() == target.RIGHT){
        //   return new
        

        } else {
        return new CommandGroup();
        }
    }
    enum startingPosition {
        CENTER, LEFT, RIGHT;
    }

    enum target{
        FRONT, MIDDLE, BACK, NONE;
        }
      
    enum gamePiece {
        BALL, Hatch;
    }
}
package org.usfirst.frc.team4.robot.utilities;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser {

    SendableChooser<startingPosition> startingPositionChooser;
    SendableChooser<target> targetChooser;
    SendableChooser<startingLevel> startingLevelChooser;
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
       
       startingLevelChooser = new SendableChooser<>();
       startingLevelChooser.setDefaultOption("Level 2", startingLevel.TWO);
       startingLevelChooser.addOption("Level 1", startingLevel.ONE);
       
        // Add additional choosers based on what the game needs
        SmartDashboard.putData(startingPositionChooser);
        SmartDashboard.putData(targetChooser);
        SmartDashboard.putData(startingLevelChooser);
    }

    public target getDirection() {
        return targetChooser.getSelected();
    }

    public startingPosition getStartingPosition() {
        return startingPositionChooser.getSelected();
    }
    public startingLevel getStaringLevel(){
        return startingLevelChooser.getSelected();
    }

    public CommandGroup getSelectedAuto() {
        return new CommandGroup();
        // replace with auto selection logic
    }
}

enum startingPosition {
    CENTER, LEFT, RIGHT;
}

enum startingLevel {
    ONE, TWO;
}

enum target{
    FRONT, MIDDLE, BACK, NONE;
}
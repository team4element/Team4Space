package com.team4.robot.auto.modes;

import com.team4.robot.auto.AutoModeEndedException;
import com.team4.robot.auto.actions.DrivePathAction;
import com.team4.robot.paths.StraightPath;

public class DriveStraightMode extends AutoModeBase{
    DrivePathAction straightPath;

    public DriveStraightMode(){
        straightPath = new DrivePathAction(new StraightPath());
    }

    
    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(straightPath); 
    }
}
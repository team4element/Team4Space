package com.team4.robot.auto.modes;

import java.util.Arrays;

import com.team4.robot.auto.AutoModeEndedException;
import com.team4.robot.auto.actions.DrivePathAction;
import com.team4.robot.auto.actions.LEDOffAction;
import com.team4.robot.auto.actions.ParallelAction;
import com.team4.robot.auto.actions.SeriesAction;
import com.team4.robot.auto.actions.WaitForPathMarkerAction;
import com.team4.robot.paths.SplinePath;

public class DriveSplineMode extends AutoModeBase{
    DrivePathAction splinePath;
    
    public DriveSplineMode(){
        splinePath = new DrivePathAction(new SplinePath());
    }

    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new ParallelAction(Arrays.asList(splinePath, 
        new SeriesAction(Arrays.asList(new WaitForPathMarkerAction("LEDOff"), new LEDOffAction())))));
    }
}
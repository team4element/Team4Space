package com.team4.robot.auto.actions;

import com.team4.lib.drivers.VisionSubsystem.LedMode;
import com.team4.robot.subsystems.VisionTracker;

public class LEDOffAction extends RunOnceAction{
    
    VisionTracker mVisionTracker;

    public LEDOffAction(){
        mVisionTracker = VisionTracker.getInstance();
    }

    
    @Override
    public void runOnce() {
        mVisionTracker.setLEDMode(LedMode.OFF);
    }
}
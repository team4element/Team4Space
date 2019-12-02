package com.team4.robot.auto.modes;

import com.team4.robot.auto.AutoModeEndedException;
import com.team4.robot.auto.actions.DriveForwardAndTurnToTargetAction;

public class DriveForwardAndTrackAction extends AutoModeBase{
    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new DriveForwardAndTurnToTargetAction(2, 10));
    }
}
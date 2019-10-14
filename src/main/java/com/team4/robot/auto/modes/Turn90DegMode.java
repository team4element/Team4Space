package com.team4.robot.auto.modes;

import com.team4.robot.actions.TurnToHeadingAction;
import com.team4.robot.auto.framework.AutoModeBase;
import com.team4.robot.auto.framework.AutoModeEndedException;

public class Turn90DegMode extends AutoModeBase{
    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new TurnToHeadingAction(90));
    }
}
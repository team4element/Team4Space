package com.team4.robot.auto.modes;

import com.team254.lib.geometry.Rotation2d;
import com.team4.robot.auto.AutoModeEndedException;
import com.team4.robot.auto.actions.TurnToHeadingAction;

public class TuneTurnToHeadingMode extends AutoModeBase {
    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new TurnToHeadingAction(Rotation2d.fromDegrees(180)));
        // runAction(new WaitAction(15));
    }
}
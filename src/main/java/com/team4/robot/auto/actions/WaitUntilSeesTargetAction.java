package com.team4.robot.auto.actions;

import com.team4.robot.RobotState;
import com.team4.robot.constants.TargetingConstants;

public class WaitUntilSeesTargetAction implements Action {
    @Override
    public void start() {}

    @Override
    public void update() {}

    @Override
    public boolean isFinished() {
        return RobotState.getInstance().getAimingParameters(false, -1, TargetingConstants.kMaxGoalTrackAge).isPresent();
    }

    @Override
    public void done() {}
}
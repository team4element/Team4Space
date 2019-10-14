package com.team4.robot.auto.modes;

import com.team4.robot.auto.framework.AutoModeBase;
import com.team4.robot.auto.framework.AutoModeEndedException;

public class DoNothingMode extends AutoModeBase {
    @Override
    protected void routine() throws AutoModeEndedException {
        System.out.println("doing nothing");
    }
}

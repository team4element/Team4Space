package com.team4.robot.auto.modes;

import com.team4.robot.auto.AutoModeBase;
import com.team4.robot.auto.AutoModeEndedException;

public class DoNothingMode extends AutoModeBase {
    @Override
    protected void routine() throws AutoModeEndedException {
        System.out.println("Doing nothing");
    }
}

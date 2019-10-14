package com.team4.robot.auto.modes;

import com.team195.lib.motion.PathContainer;
import com.team4.robot.actions.DrivePathAction;
import com.team4.robot.actions.ResetPoseFromPathAction;
import com.team4.robot.actions.framework.WaitAction;
import com.team4.robot.auto.framework.AutoModeBase;
import com.team4.robot.auto.framework.AutoModeEndedException;
import com.team4.robot.auto.paths.StraightPath;

public class StraightPathMode extends AutoModeBase {
    @Override
    protected void routine() throws AutoModeEndedException {
        PathContainer pathContainer = new StraightPath();
        runAction(new ResetPoseFromPathAction(pathContainer));
        runAction(new  DrivePathAction(pathContainer));
        runAction(new WaitAction(15));
    } 
}

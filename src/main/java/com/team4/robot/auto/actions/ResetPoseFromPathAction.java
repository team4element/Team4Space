package com.team4.robot.auto.actions;

import com.team254.lib.geometry.Rotation2d;
import com.team4.robot.RobotState;
import com.team4.robot.paths.PathContainer;

import edu.wpi.first.wpilibj.Timer;

public class ResetPoseFromPathAction extends RunOnceAction{
    PathContainer mPathContainer;

    public ResetPoseFromPathAction(PathContainer pathContainer){
        mPathContainer = pathContainer;
    }

    @Override
    public void runOnce() {
        RobotState.getInstance().reset(Timer.getFPGATimestamp(), mPathContainer.getStartPose(), Rotation2d.identity());
    }
}
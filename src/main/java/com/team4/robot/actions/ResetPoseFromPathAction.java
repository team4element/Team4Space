package com.team4.robot.actions;

import com.team195.lib.motion.PathContainer;
import com.team195.lib.motion.PathFollowerRobotState;
import com.team195.lib.motion.RigidTransform2d;
import com.team4.robot.actions.framework.RunOnceAction;
import com.team4.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.Timer;

/**
 * Resets the robot's current pose based on the starting pose stored in the pathContainer object.
 *
 */
public class ResetPoseFromPathAction extends RunOnceAction {

    protected PathContainer mPathContainer;

    public ResetPoseFromPathAction(PathContainer pathContainer) {
        mPathContainer = pathContainer;
    }

    @Override
    public synchronized void runOnce() {
        RigidTransform2d startPose = mPathContainer.getStartPose();
        PathFollowerRobotState.getInstance().reset(Timer.getFPGATimestamp(), startPose);
        Drive.getInstance().setGyroAngle(startPose.getRotation());
    }
}

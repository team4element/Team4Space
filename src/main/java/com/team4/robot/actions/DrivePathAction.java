package com.team4.robot.actions;

import com.team195.lib.motion.Path;
import com.team195.lib.motion.PathContainer;
import com.team195.lib.reporters.ConsoleReporter;
import com.team195.lib.reporters.MessageLevel;
import com.team4.robot.actions.framework.Action;
import com.team4.robot.subsystems.Drive;

/**
 * Drives the robot along the Path defined in the PathContainer object. The action finishes once the robot reaches the
 * end of the path.
 *
 */
public class DrivePathAction implements Action {

    private PathContainer mPathContainer;
    private Path mPath;
    private Drive mDrive = Drive.getInstance();

    public DrivePathAction(PathContainer p) {
        mPathContainer = p;
        mPath = mPathContainer.buildPath();
        ConsoleReporter.report(p.getClass().getName(), MessageLevel.INFO);
    }

    @Override
    public boolean isFinished() {
        return mDrive.isDoneWithPath();
    }

    @Override
    public void update() {
        // Nothing done here, controller updates in mEnabedLooper in robot
    }

    @Override
    public void done() {

    }

    @Override
    public void start() {
        mDrive.setWantDrivePath(mPath, mPathContainer.isReversed());
    }
}

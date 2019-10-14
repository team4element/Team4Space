package com.team4.robot.actions;

import com.team195.lib.motion.Rotation2d;
import com.team4.robot.actions.framework.Action;
import com.team4.robot.subsystems.Drive;

/**
 * Drives the robot along the Path defined in the PathContainer object. The action finishes once the robot reaches the
 * end of the path.
 *
 */
public class TurnToHeadingAction implements Action {

	private Drive mDrive = Drive.getInstance();
	private double heading;

	public TurnToHeadingAction(double rotationDeg) {
		heading = rotationDeg;
	}

	@Override
	public boolean isFinished() {
		return mDrive.isDoneWithTurn();
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
		mDrive.getNavXBoard().zeroYaw();
		mDrive.setWantTurnToHeading(heading);
	}
}

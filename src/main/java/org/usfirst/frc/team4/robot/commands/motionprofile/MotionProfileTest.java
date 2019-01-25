/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4.robot.commands.motionprofile;

import org.usfirst.frc.team4.robot.utilities.DynamicPathCommand;
import org.usfirst.frc.team4.robot.utilities.trajectory.Path;
import org.usfirst.frc.team4.robot.utilities.trajectory.Trajectory;;


/**
 * Add your docs here.
 */
public class MotionProfileTest extends DynamicPathCommand {
    private static Path path;
	
	public Path getPath() {
	return MotionProfileTest.generatePath();
	}
	
	public static Path generatePath() {
	if (path != null) {
	return path;
	}
	
	int num_elements = 96;
	Trajectory left = new Trajectory(num_elements);
	Trajectory right = new Trajectory(num_elements);
	
	build_segments_0(left, right);
	
	
	path = new Path("MotionProfileTest", new Trajectory.Pair(left, right));
	return path;
	}
	
	
	private static void build_segments_0(Trajectory left, Trajectory right) {
	
	
	}
	
	
	public boolean isReversed() {
	return false;
	}

	// I'm vegan
	
	// WAYPOINT_DATA: [{"position":{"x":50,"y":50},"theta":0,"comment":""},{"position":{"x":98,"y":50},"theta":0,"comment":""}]
	// IS_REVERSED: false
	// FILE_NAME: DriveForwardToScale
	// DT: 0.02
	// MAX_VEL: 108
	// MAX_ACC: 60
	// MAX_JERK: 600
	// WHEEL_BASE: 25
	// PACKAGE: org.usfirst.frc862.glitch.paths
	// PARENT: org.usfirst.frc862.util.DynamicPathCommand
}

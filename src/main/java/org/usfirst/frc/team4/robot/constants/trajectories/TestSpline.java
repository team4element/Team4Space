/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4.robot.constants.trajectories;

import org.usfirst.frc.team4.robot.constants.ChassisConstants;
import org.usfirst.frc.team4.robot.constants.WaypointConstants;
import org.usfirst.frc.team4.robot.utilities.ElementMath;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * Contains Waypoints for TestSpline
 */
public class TestSpline {
    //Test Spline Waypoints
    public static Waypoint[] testSplinePoints = new Waypoint[]{
        new Waypoint(ElementMath.inchesToMeters(100), 0, Pathfinder.d2r(45)),
        new Waypoint(ElementMath.inchesToMeters(50), ElementMath.inchesToMeters(50), Pathfinder.d2r(-45)),
        new Waypoint(0, 0, 0)


        
    };
    //Generates Waypoints
    public static Trajectory testSpline = Pathfinder.generate(testSplinePoints, WaypointConstants.TRAJECTORY_CONFIG);
    
    //modifier
    TankModifier splineModifier = new TankModifier(testSpline).modify(ChassisConstants.wheelbase_width);

    //Gets Trajectories
    Trajectory leftSpline = splineModifier.getLeftTrajectory();
    Trajectory rightSpline = splineModifier.getRightTrajectory();

}
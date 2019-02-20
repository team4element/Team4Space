package org.usfirst.frc.team4.robot.constants.trajectories;

import org.usfirst.frc.team4.robot.constants.ChassisConstants;
import org.usfirst.frc.team4.robot.constants.WaypointConstants;
import org.usfirst.frc.team4.robot.utilities.ElementMath;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class TestStraight {
    //Test Waypoints
    public static Waypoint[] testStraightPoints = new Waypoint[]{
        new Waypoint(ElementMath.inchesToMeters(100),0,0),
        new Waypoint(ElementMath.inchesToMeters(50),0,0),
        new Waypoint(0,0,0)
    };

    //Generates Test Trajectory
    public static Trajectory testStraightTrajectory = Pathfinder.generate(testStraightPoints, WaypointConstants.TRAJECTORY_CONFIG);

    //Test Modifier
    TankModifier straightModifier = new TankModifier(testStraightTrajectory).modify(ChassisConstants.wheelbase_width);

    //Gets Trajectories
    Trajectory testLeft = straightModifier.getLeftTrajectory();
    Trajectory testRight = straightModifier.getRightTrajectory();
}

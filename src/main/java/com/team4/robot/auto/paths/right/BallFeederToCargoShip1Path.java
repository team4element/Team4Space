package com.team4.robot.auto.paths.right;

import java.util.ArrayList;

import com.team195.lib.motion.Path;
import com.team195.lib.motion.PathBuilder;
import com.team195.lib.motion.PathBuilder.Waypoint;
import com.team195.lib.motion.PathContainer;
import com.team195.lib.motion.RigidTransform2d;
import com.team195.lib.motion.Rotation2d;
import com.team195.lib.motion.Translation2d;

public class BallFeederToCargoShip1Path implements PathContainer {

    @Override
    public Path buildPath() {
        ArrayList<Waypoint> sWaypoints = new ArrayList<Waypoint>();
        sWaypoints.add(new Waypoint(75,78,0,0));
        sWaypoints.add(new Waypoint(130,78,30,80));
        sWaypoints.add(new Waypoint(210,115,25,60));
        sWaypoints.add(new Waypoint(255,115,15,60));
        sWaypoints.add(new Waypoint(261,90,0,60));

        return PathBuilder.buildPathFromWaypoints(sWaypoints);
    }
    
    @Override
    public RigidTransform2d getStartPose() {
        return new RigidTransform2d(new Translation2d(75, 78), Rotation2d.fromDegrees(0)); 
    }

    @Override
    public boolean isReversed() {
        return false; 
    }
}
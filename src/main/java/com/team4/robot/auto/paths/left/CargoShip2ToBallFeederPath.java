package com.team4.robot.auto.paths.left;

import java.util.ArrayList;

import com.team195.lib.motion.Path;
import com.team195.lib.motion.PathBuilder;
import com.team195.lib.motion.PathBuilder.Waypoint;
import com.team195.lib.motion.PathContainer;
import com.team195.lib.motion.RigidTransform2d;
import com.team195.lib.motion.Rotation2d;
import com.team195.lib.motion.Translation2d;

public class CargoShip2ToBallFeederPath implements PathContainer {

    @Override
    public Path buildPath() {
        ArrayList<Waypoint> sWaypoints = new ArrayList<Waypoint>();
        sWaypoints.add(new Waypoint(284,220,0,0));
        sWaypoints.add(new Waypoint(284,240,15,80));
        sWaypoints.add(new Waypoint(229,242,30,100));
        sWaypoints.add(new Waypoint(120,242,0,80));
        sWaypoints.add(new Waypoint(76,242,0,40));

        return PathBuilder.buildPathFromWaypoints(sWaypoints);
    }
    
    @Override
    public RigidTransform2d getStartPose() {
        return new RigidTransform2d(new Translation2d(284, 220), Rotation2d.fromDegrees(0)); 
    }

    @Override
    public boolean isReversed() {
        return false; 
    }
}
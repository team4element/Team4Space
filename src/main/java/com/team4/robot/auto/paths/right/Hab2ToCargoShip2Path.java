package com.team4.robot.auto.paths.right;

import java.util.ArrayList;

import com.team195.lib.motion.Path;
import com.team195.lib.motion.PathBuilder;
import com.team195.lib.motion.PathBuilder.Waypoint;
import com.team195.lib.motion.PathContainer;
import com.team195.lib.motion.RigidTransform2d;
import com.team195.lib.motion.Rotation2d;
import com.team195.lib.motion.Translation2d;

public class Hab2ToCargoShip2Path implements PathContainer {

    @Override
    public Path buildPath() {
        ArrayList<Waypoint> sWaypoints = new ArrayList<Waypoint>();
        sWaypoints.add(new Waypoint(20,120,0,0));
        sWaypoints.add(new Waypoint(70,120,0,80));
        sWaypoints.add(new Waypoint(180,120,30,100));
        sWaypoints.add(new Waypoint(280,115,16,80));
        sWaypoints.add(new Waypoint(284,90,0,40));

        return PathBuilder.buildPathFromWaypoints(sWaypoints);
    }
    
    @Override
    public RigidTransform2d getStartPose() {
        return new RigidTransform2d(new Translation2d(20, 120), Rotation2d.fromDegrees(0)); 
    }

    @Override
    public boolean isReversed() {
        return false; 
    }
}
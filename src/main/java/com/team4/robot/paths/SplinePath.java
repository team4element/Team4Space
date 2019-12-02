package com.team4.robot.paths;

import java.util.ArrayList;

import com.team254.lib.control.Path;
import com.team254.lib.geometry.Pose2d;
import com.team254.lib.geometry.Rotation2d;
import com.team254.lib.geometry.Translation2d;
import com.team4.robot.paths.PathBuilder.Waypoint;

public class SplinePath implements PathContainer {
    @Override
    public Path buildPath() {
        ArrayList<Waypoint> sWaypoints = new ArrayList<Waypoint>();
        sWaypoints.add(new Waypoint(0,0,0,0));
        sWaypoints.add(new Waypoint(60,0,15,60));
        sWaypoints.add(new Waypoint(80,30,15,60,"LEDOff"));
        sWaypoints.add(new Waypoint(160,30,0,60));

        return PathBuilder.buildPathFromWaypoints(sWaypoints);
    }

    @Override
    public Pose2d getStartPose() {
        return new Pose2d(new Translation2d(0,0), Rotation2d.fromDegrees(180));
    }

    @Override
    public boolean isReversed() {
        return false; 
    }
}
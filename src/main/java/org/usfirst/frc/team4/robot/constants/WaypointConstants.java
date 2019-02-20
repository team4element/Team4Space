package org.usfirst.frc.team4.robot.constants;

import org.usfirst.frc.team4.robot.utilities.ElementMath;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Config;

/**
 * Contains constants for Waypoints and Trajectories
 */
public class WaypointConstants {
    
    //Configures rules for generating trajectories
    public static Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH,
                                                                    AutoConstants.kTimeStep, ElementMath.inchesToMeters(AutoConstants.maxVelocity), 
                                                                    ElementMath.inchesToMeters(AutoConstants.maxAcceleration), 
                                                                    ElementMath.inchesToMeters(AutoConstants.maxJerk));
    
    //Makes config unmodifiable
    public static final Config TRAJECTORY_CONFIG = config;
}
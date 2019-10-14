package com.team4.robot.constants;

/**
 * Add your docs here.
 */
public class PathFollowingConstants {
    public static final double kMinLookAhead = 9;
    public static final double kMinLookAheadSpeed = 6;
    public static final double kMaxLookAhead = 12;
    public static final double kMaxLookAheadSpeed = 24;
	public static final double kDeltaLookAhead = kMaxLookAhead - kMinLookAhead;
	public static final double kDeltaLookAheadSpeed = kMaxLookAheadSpeed - kMinLookAheadSpeed;

	public static final double kInertiaSteeringGain = 0.001;
    public static final double kSegmentCompletionTolerance = 1;
    public static final double kPathFollowingMaxAccel = 30.0;
	public static final double kPathFollowingMaxVel = 72.0; 

	public static final double kPathFollowingProfileKp = 5.0; //5
	public static final double kPathFollowingProfileKi = 0.0;//.03
	public static final double kPathFollowingProfileKv = 0.1; //.2
	public static final double kPathFollowingProfileKffv = .77; //1023/((120/.633/600)*4096)
	public static final double kPathFollowingProfileKffa = .07; //.05
	public static final double kPathFollowingGoalPosTolerance = 1;
	public static final double kPathFollowingGoalVelTolerance = 12.0;
	public static final double kPathStopSteeringDistance = 100.0;
    
}

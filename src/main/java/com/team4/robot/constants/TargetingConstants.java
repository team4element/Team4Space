package com.team4.robot.constants;

import com.team254.lib.geometry.Pose2d;
import com.team254.lib.geometry.Rotation2d;
import com.team254.lib.geometry.Translation2d;
import com.team4.lib.drivers.VisionSubsystem.VisionConstants;

public class TargetingConstants {
      // limelight
      public static final double kHorizontalFOV = 59.6; // degrees
      public static final double kVerticalFOV = 49.7; // degrees
      public static final double kVPW = 2.0 * Math.tan(Math.toRadians(kHorizontalFOV / 2.0));
      public static final double kVPH = 2.0 * Math.tan(Math.toRadians(kVerticalFOV / 2.0));
      public static final double kImageCaptureLatency = 11.0 / 1000.0; // seconds
  
      public static final double kMaxTrackerDistance = 9.0;
      public static final double kMaxGoalTrackAge = 2.5;
      public static final double kMaxGoalTrackAgeNotTracking = 0.1;
      public static final double kMaxGoalTrackSmoothingTime = 0.5;
      public static final double kTrackStabilityWeight = 0.0;
      public static final double kTrackAgeWeight = 10.0;
      public static final double kTrackSwitchingWeight = 100.0;
  
      public static final double kCameraFrameRate = 90.0;
      public static final double kMinStability = 0.5;
      public static final int kPortPipeline = 0;
      public static final int kBallPipeline = 2;
      public static final double kPortTargetHeight = 39.125;
      public static final double kHatchTargetHeight = 31.5;

          // limelight
    public static final VisionConstants kLimelightConstants = new VisionConstants();
    static {
        kLimelightConstants.kName = "Limelight";
        kLimelightConstants.kTableName = "limelight";
        kLimelightConstants.kHeight = 44.047;  // inches
        kLimelightConstants.kTurretToLens = new Pose2d(new Translation2d(-7.685, 0.0), Rotation2d.fromDegrees(0.0));
        kLimelightConstants.kHorizontalPlaneToLens = Rotation2d.fromDegrees(-24.0);
    }

    // raspi
    public static final VisionConstants kRaspiConstants = new VisionConstants();
    static {
        kRaspiConstants.kName = "Raspi";
        kRaspiConstants.kTableName = "raspi";
        kRaspiConstants.kHeight = 7.221;  // inches
        kRaspiConstants.kTurretToLens = new Pose2d(new Translation2d(-1.293, 2.556), Rotation2d.fromDegrees(2.0));
        kRaspiConstants.kHorizontalPlaneToLens = Rotation2d.fromDegrees(47.5);
    }
}
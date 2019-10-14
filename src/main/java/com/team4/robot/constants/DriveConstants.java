package com.team4.robot.constants;

public class DriveConstants {
    /** Physical Constants     */
        // Wheels
        public static final double kDriveWheelTrackWidthInches = 24;
        public static final double kDriveWheelDiameterInches = 6;
        public static final double kDriveWheelCircumferenceInches = kDriveWheelDiameterInches * Math.PI;
        public static final double kDriveWheelRadiusInches = kDriveWheelDiameterInches / 2.0;
        public static final double kTrackScrubFactor = 1.0;  // Tune me!
        public static final double kTrackWidthInches = 24;

        public static final double kGearRatio = .633;
        public static final double kSensorUnitsPerRotation = 4096;
    
        // Geometry
        // public static final double kCenterToFrontBumperDistance = 38.25 / 2.0;
        // public static final double kCenterToRearBumperDistance = 38.25 / 2.0;
        // public static final double kCenterToSideBumperDistance = 33.75 / 2.0;
    
        /** Speed Controller IDs   */
        // Drive
        public static final int kLeftDriveMasterId = 1;
        public static final int kLeftDriveSlaveAId = 0;
        public static final int kLeftDriveSlaveBId = 2;
        public static final int kRightDriveMasterId = 4;
        public static final int kRightDriveSlaveAId = 3;
        public static final int kRightDriveSlaveBId = 5;

        /** Speed Controller PDPChannel */
        public static final int kLeftDriveMasterChannel = 2;
        public static final int kLeftDriveSlaveAChannel = 1;
        public static final int kLeftDriveSlaveBChannel = 3;
        public static final int kRightDriveMasterChannel = 13;
        public static final int kRightDriveSlaveAChannel = 14;
        public static final int kRightDriveSlaveBChannel = 12;
}
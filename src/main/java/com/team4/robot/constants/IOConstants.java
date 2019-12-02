package com.team4.robot.constants;

public class IOConstants {
    /* I/O */
    // (Note that if multiple talons are dedicated to a mechanism, any sensors
    // are attached to the master)

    public static final int kCANTimeoutMs = 10; //use for on the fly updates
    public static final int kLongCANTimeoutMs = 100; //use for constructors

    // Drive
    public static final int kLeftDriveMasterId = 1;
    public static final int kLeftDriveSlaveAId = 0;
    public static final int kLeftDriveSlaveBId = 2;
    public static final int kRightDriveMasterId = 4;
    public static final int kRightDriveSlaveAId = 3;
    public static final int kRightDriveSlaveBId = 5;

    public static final int kDriveEncoderPPR = 4096;

}
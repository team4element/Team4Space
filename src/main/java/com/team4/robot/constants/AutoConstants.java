package com.team4.robot.constants;

public class AutoConstants{
    // PID gains for drive velocity loop
    // Units: setpoint, error, and output are in ticks per second.
    public static final double kDriveLowGearVelocityKp = 0.9;
    public static final double kDriveLowGearVelocityKi = 0.0;
    public static final double kDriveLowGearVelocityKd = 10.0;
    public static final double kDriveLowGearVelocityKf = 0.0;
    public static final int kDriveLowGearVelocityIZone = 0;
    public static final double kDriveVoltageRampRate = 0.0;

    // PID gains for drive position loop
    // Units: setpoint, error, and output are in ticks per second.
    public static final double kDriveLowGearPositionKp = 0.9;
    public static final double kDriveLowGearPositionKi = 0.0;
    public static final double kDriveLowGearPositionKd = 10.0;
    public static final double kDriveLowGearPositionKf = 0.0;
    public static final int kDriveLowGearPositionIZone = 0;

    public static final double kDriveLowGearMaxVelocity = 570;
    public static final double kDriveLowGearMaxAccel = 480;


}
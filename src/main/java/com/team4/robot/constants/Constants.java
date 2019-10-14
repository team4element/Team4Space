package com.team4.robot.constants;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * A list of constants used by the rest of the robot code. This include physics constants as well as constants
 * determined through calibrations.
 */
public class Constants {
    public static final double kLooperDt = 0.005;
    public static final double k100msPerMinute = 600;

    public static final boolean REPORTING_ENABLED = true;
	public static final boolean REPORT_TO_DRIVERSTATION_INSTEAD_OF_CONSOLE = false;

    public static final int kTimeoutMs = 20;
    public static final int kTimeoutMsFast = 10;
    public static final int kActionTimeoutS = 2;
    public static final int kTalonRetryCount = 3;

    /* Thread Priorities */
    public static final int kRobotThreadPriority = 9;
	public static final int kLooperThreadPriority = Thread.MAX_PRIORITY;
	public static final int kConsoleReporterThreadPriority = Thread.NORM_PRIORITY;


    /* ROBOT PHYSICAL CONSTANTS */

    /* CONTROL LOOP GAINS */
    public static final int kCANTimeoutMs = 10; //use for on the fly updates
    public static final int kLongCANTimeoutMs = 100; //use for constructors


    // Solenoids
    public static final int kLeftRampSolenoid = 5;
    public static final int kRightRampSolenoid = 0;

    //Joystick Constants
    public static final int DRIVE_CONTROLLER = 0;
    public static final int OPERATOR_CONTROLLER = 1;

    public static final int BUTTON_A = 1;
    public static final int BUMPER_LEFT = 5;
	public static final int BUMPER_RIGHT = 6;
    public static final int BUTTON_SELECT = 7;
	public static final int BUTTON_START = 8;


    public static final int kControllerThreadPriority =9;
    public static final double kJoystickDeadband = 0.08;
    public static final double kJoystickThreshold = 0.5;
    public static final double kJoystickJogThreshold = 0.4;
    
   // Goal tracker constants
   public static final double kMaxGoalTrackAge = 1.0;
   public static final double kMaxTrackerDistance = 18.0;
   public static final double kCameraFrameRate = 30.0;
   public static final double kTrackReportComparatorStablityWeight = 1.0;
   public static final double kTrackReportComparatorAgeWeight = 1.0;

   // Pose of the camera frame w.r.t. the robot frame
   public static final double kCameraXOffset = -3.3211;
   public static final double kCameraYOffset = 0.0;
   public static final double kCameraZOffset = 20.9;
   public static final double kCameraPitchAngleDegrees = 29.56; // Measured on 4/26
   public static final double kCameraYawAngleDegrees = 0.0;
   public static final double kCameraDeadband = 0.0;

    // Collision Detection
	public static final double kCollisionDetectionJerkThreshold = 950;
    public static final double kTippingThresholdDeg = 11;

    public static final double kBoilerTargetTopHeight = 88.0;
	public static final double kBoilerRadius = 7.5;
    
    /**
     * @return the MAC address of the robot
     */
    public static String getMACAddress() {
        try {
            Enumeration<NetworkInterface> nwInterface = NetworkInterface.getNetworkInterfaces();
            StringBuilder ret = new StringBuilder();
            while (nwInterface.hasMoreElements()) {
                NetworkInterface nis = nwInterface.nextElement();
                if (nis != null) {
                    byte[] mac = nis.getHardwareAddress();
                    if (mac != null) {
                        for (int i = 0; i < mac.length; i++) {
                            ret.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        return ret.toString();
                    } else {
                        System.out.println("Address doesn't exist or is not accessible");
                    }
                } else {
                    System.out.println("Network Interface for the specified address is not found.");
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return "";
    }
}

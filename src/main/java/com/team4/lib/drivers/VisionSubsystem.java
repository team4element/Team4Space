package com.team4.lib.drivers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.team254.lib.geometry.Pose2d;
import com.team254.lib.geometry.Rotation2d;
import com.team254.lib.geometry.Translation2d;
import com.team254.lib.util.Util;
import com.team254.lib.vision.TargetInfo;
import com.team4.robot.RobotState;
import com.team4.robot.constants.TargetingConstants;
import com.team4.robot.subsystems.Subsystem;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for interacting with the Vision system
 */
public abstract class VisionSubsystem extends Subsystem {
    public final static int kDefaultPipeline = 0;
    public final static int kSortTopPipeline = 1;

    public static class VisionConstants {
        public String kName = "";
        public String kTableName = "";
        public double kHeight = 0.0;
        public Pose2d kTurretToLens = Pose2d.identity();
        public Rotation2d kHorizontalPlaneToLens = Rotation2d.identity();
    }

    protected NetworkTable mNetworkTable;

    public VisionSubsystem(VisionConstants constants) {
        mConstants = constants;
        mNetworkTable = NetworkTableInstance.getDefault().getTable(constants.kTableName);
    }

    public static class PeriodicIO {
        // INPUTS
        public double latency;
        public int givenLedMode;
        public int givenPipeline;
        public double xOffset;
        public double yOffset;
        public double area;

        // OUTPUTS
        public int ledMode = 1; // 0 - use pipeline mode, 1 - off, 2 - blink, 3 - on
        public int camMode = 0; // 0 - vision processing, 1 - driver camera
        public int pipeline = 0; // 0 - 9
        public int stream = 2; // sets stream layout if another webcam is attached
        public int snapshot = 0; // 0 - stop snapshots, 1 - 2 Hz
    }

    protected VisionConstants mConstants = null;
     private PeriodicIO mPeriodicIO = new PeriodicIO();
    private boolean mOutputsHaveChanged = true;
   private double[] mZeroArray = new double[]{0, 0, 0, 0, 0, 0, 0, 0};
    private List<TargetInfo> mTargets = new ArrayList<>();
    private boolean mSeesTarget = false;

    public abstract Pose2d getTurretToLens();

    public abstract double getLensHeight();

    public abstract Rotation2d getHorizontalPlaneToLens();

    public enum LedMode {
        PIPELINE, OFF, BLINK, ON
    }

    public abstract void setLed(LedMode mode);

    public abstract void setPipeline(int mode);


    public synchronized void triggerOutputs() {
        mOutputsHaveChanged = true;
    }

    public synchronized int getPipeline() {
        return mPeriodicIO.pipeline;
    }

    public synchronized boolean seesTarget() {
        return mSeesTarget;
    }

    /**
     * @return two targets that make up one hatch/port or null if less than two targets are found
     */
    public abstract List<TargetInfo> getTarget();

    protected abstract List<TargetInfo> getRawTargetInfos();

    protected abstract List<double[]> getTopCorners();

    public abstract List<double[]> extractTopCornersFromBoundingBoxes(double[] xCorners, double[] yCorners);

    public abstract double getLatency();
}

package org.usfirst.frc.team4.robot.subsystems;

import org.usfirst.frc.team4.robot.constants.LimelightConstants.CameraMode;
import org.usfirst.frc.team4.robot.constants.LimelightConstants.eLEDMode;
import org.usfirst.frc.team4.robot.constants.LimelightConstants.eSnapShotMode;
import org.usfirst.frc.team4.robot.constants.LimelightConstants.eStreamerMode;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for Limelight
 */
public class Limelight extends Subsystem {

	private NetworkTable limeTable = NetworkTableInstance.getDefault().getTable("limelight");

	public Limelight() {
		limeTable.getEntry("camMode").setNumber(CameraMode.VISION_PROCESSING.val);
	}

	public void initDefaultCommand() {
	}

	// Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
	public double getTX() {
		NetworkTableEntry tx = limeTable.getEntry("tx");
		return tx.getDouble(0);
	}

	// Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
	public double getTY() {
		NetworkTableEntry ty = limeTable.getEntry("ty");
		return ty.getDouble(0);
	}

	// Skew or rotation (-90 degrees to 0 degrees)
	public double getTS() {
		NetworkTableEntry ts = limeTable.getEntry("ts");
		return ts.getDouble(0);
	}

	// The pipelines latency contribution (ms) Add at least 11ms for image
	// capture latency.
	public double getTL() {
		NetworkTableEntry tl = limeTable.getEntry("tl");
		return tl.getDouble(0);

	}

	// Target Area (0% of image to 100% of image)
	public double getTA() {
		NetworkTableEntry ta = limeTable.getEntry("ta");
		return ta.getDouble(0);
	}

	// Whether the limelight has any valid targets (0 or 1)
	public boolean hasTarget() {
		NetworkTableEntry tv = limeTable.getEntry("tv");
		return tv.getDouble(0) == 1;
	}

	public void setLEDMode(eLEDMode ledMode) {
		limeTable.getEntry("ledMode").setNumber(ledMode.val);
	}

	public void setCamMode(CameraMode camMode) {
		limeTable.getEntry("camMode").setNumber(camMode.val);
	}

	public void setPipeline(int pipeline) {
		limeTable.getEntry("pipeline").setNumber(pipeline);
	}
	
	public void setStreamerMode(eStreamerMode streamerMode){
		limeTable.getEntry("stream").setNumber(streamerMode.val);
	}

	private static Limelight m_instance = null;

	public static Limelight getInstance() {
		if (m_instance == null) {
			m_instance = new Limelight();
		}

		return m_instance;
	}
	
	public void setSnapShotMode(eSnapShotMode SnapShotMode) {
		limeTable.getEntry("snapshot").setNumber(SnapShotMode.val);
		
	}
}

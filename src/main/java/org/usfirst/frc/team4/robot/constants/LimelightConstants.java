package org.usfirst.frc.team4.robot.constants;
/**
 * Constants for limelight
 */
public class LimelightConstants {

	// Camera Modes
	public enum CameraMode {
		VISION_PROCESSING(0), DRIVERSTATION_FEEDBACK(1);

		public int val;

		CameraMode(int i) {
			val = i;
		}
	}

	// LED Modes
	public enum eLEDMode {
		OFF(1), ON(0), BLINKING(2);

		public int val;

		eLEDMode(int i) {
			val = i;
		}
	}
	
	public enum eStreamerMode {
		PIP_MAIN(1), STANDARD(0), PIP_SECONDARY(2);

		public int val;

		eStreamerMode(int i) {
			val = i;
		}
	}
	public enum eSnapShotMode {
		ON(1), OFF(0);

		public int val;

		eSnapShotMode(int i) {
			val = i;
		}
	}

}

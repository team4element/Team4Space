package org.usfirst.frc.team4.robot.constants;
/**
 * Constants for limelight
 */
public class LimelightConstants {

	 public static boolean isLedOn = false;
	 public static boolean isVisionMode = false;

	 public static double floorToCamera = 18.75;
	 
	 public static double mountingAngle = 0;


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
	public enum eFloorToTarget{
		FRONT_ROCKET(39.125), SIDE_ROCKET(31.5), LOADING_STATION(31.5), CARGO(31.5);
		
		public double val;

		eFloorToTarget(double i){
			val = i;
		}
	}

}

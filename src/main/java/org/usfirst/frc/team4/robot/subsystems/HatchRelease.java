package org.usfirst.frc.team4.robot.subsystems;

import org.usfirst.frc.team4.robot.constants.HatchReleaseConstants;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for HatchRelease (pneumatics)
 */
public class HatchRelease extends Subsystem {
	
	//Declaring Solenoids and Compressor
	
	public static Compressor compressor;

	// Left
	public static Solenoid solenoidLeftTop;
	public static Solenoid solenoidLeftBot;
	// Right
	public static Solenoid solenoidRightTop;
	public static Solenoid solenoidRightBot;

	public HatchRelease() {
		//Instantiating Solenoids and Compressor
		
		compressor = new Compressor(HatchReleaseConstants.COMPRESSOR);

		solenoidLeftTop = new Solenoid(HatchReleaseConstants.SOLENOID_LEFT_TOP);
		solenoidLeftBot = new Solenoid(HatchReleaseConstants.SOLENOID_LEFT_BOT);

		solenoidRightBot = new Solenoid(HatchReleaseConstants.SOLENOID_RIGHT_TOP);
		solenoidRightTop = new Solenoid(HatchReleaseConstants.SOLENOID_RIGHT_BOT);

		compressor.setClosedLoopControl(false);

	}

	public void initDefaultCommand() {

	}

	public void pulseSolenoids() {
		solenoidLeftTop.startPulse();
		solenoidLeftTop.setPulseDuration(3);

		solenoidLeftBot.startPulse();
		solenoidLeftBot.setPulseDuration(3);

		solenoidRightTop.startPulse();
		solenoidRightTop.setPulseDuration(3);

		solenoidRightBot.startPulse();
		solenoidRightBot.setPulseDuration(3);

	}

	public void compressorStart() {
		compressor.start();
	}

	public void compressorStop() {
		compressor.stop();
	}
}
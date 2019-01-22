package org.usfirst.frc.team4.robot.subsystems;

import org.usfirst.frc.team4.robot.constants.PneumaticsConstants;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for pneumatics (hatch)
 */
public class Pneumatics extends Subsystem {
	
	//Declaring Solenoids and Compressor
	
	public static Compressor compressor;

	// Left
	public static Solenoid solenoidLeftTop;
	public static Solenoid solenoidLeftBot;
	// Right
	public static Solenoid solenoidRightTop;
	public static Solenoid solenoidRightBot;

	public Pneumatics() {
		//Instantiating Solenoids and Compressor
		
		compressor = new Compressor(PneumaticsConstants.COMPRESSOR);

		solenoidLeftTop = new Solenoid(PneumaticsConstants.SOLENOID_LEFT_TOP);
		solenoidLeftBot = new Solenoid(PneumaticsConstants.SOLENOID_LEFT_BOT);

		solenoidRightBot = new Solenoid(PneumaticsConstants.SOLENOID_RIGHT_TOP);
		solenoidRightTop = new Solenoid(PneumaticsConstants.SOLENOID_RIGHT_BOT);

	}

	public void initDefaultCommand() {

	}

	public void pulseSolenoids() {
		solenoidLeftTop.startPulse();
		solenoidLeftTop.setPulseDuration(.75);

		solenoidLeftBot.startPulse();
		solenoidLeftBot.setPulseDuration(.75);

		solenoidRightTop.startPulse();
		solenoidRightTop.setPulseDuration(.75);

		solenoidRightBot.startPulse();
		solenoidRightBot.setPulseDuration(.75);

	}

	public void compressorStart() {
		compressor.start();
	}

	public void compressorStop() {
		compressor.stop();
	}
}
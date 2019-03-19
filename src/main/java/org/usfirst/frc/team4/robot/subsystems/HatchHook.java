package org.usfirst.frc.team4.robot.subsystems;

import org.usfirst.frc.team4.robot.constants.HatchHookConstants;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for HatchHook (
 */
public class HatchHook extends Subsystem {
	
	//Declaring Solenoids and Compressor
	
	public static Compressor compressor;
	public static DoubleSolenoid DoubleSolenoid;

	public HatchHook() {
		//Instantiating Solenoids and Compressor
		
		compressor = new Compressor(HatchHookConstants.COMPRESSOR);
		DoubleSolenoid = new DoubleSolenoid(HatchHookConstants.DOUBLE_SOLENOID_FORWARD, HatchHookConstants.DOUBLE_SOLENOID_BACKWARD);


		compressor.setClosedLoopControl(false);

	}

	public void initDefaultCommand() {

	}

	public void pulseSolenoid(Value direction) {
		DoubleSolenoid.set(direction);
	}

	public void compressorStart() {
		compressor.start();
	}

	public void compressorStop() {
		compressor.stop();
	}
}
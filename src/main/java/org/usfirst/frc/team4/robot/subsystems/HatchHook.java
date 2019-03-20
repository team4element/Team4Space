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
	public static DoubleSolenoid frontHook;
	public static DoubleSolenoid backHook;

	public HatchHook() {
		//Instantiating Solenoids and Compressor
		
		compressor = new Compressor(HatchHookConstants.COMPRESSOR);
		frontHook = new DoubleSolenoid(HatchHookConstants.FRONT_HOOK_FORWARD, HatchHookConstants.FRONT_HOOK_BACKWARD);
		backHook = new DoubleSolenoid(HatchHookConstants.BACK_HOOK_FORWARD, HatchHookConstants.BACK_HOOK_BACKWARD);


		compressor.setClosedLoopControl(false);

	}

	public void initDefaultCommand() {

	}

	public void pulseHooks(Value direction) {
		frontHook.set(direction);
		backHook.set(direction);
	}

	public void compressorStart() {
		compressor.start();
	}

	public void compressorStop() {
		compressor.stop();
	}
}
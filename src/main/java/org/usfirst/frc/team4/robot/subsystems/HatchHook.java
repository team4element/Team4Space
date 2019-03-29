package org.usfirst.frc.team4.robot.subsystems;

import org.usfirst.frc.team4.robot.constants.HatchHookConstants;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for HatchHook (
 */
public class HatchHook extends Subsystem {
	
	//Declaring Solenoids and Compressor
	
	public static Compressor compressor;
	public static Solenoid frontHook;
	public static Solenoid backHook;

	public boolean isOut = true;

	public HatchHook() {
	//Instantiating Solenoids and Compressor
		
		compressor = new Compressor(HatchHookConstants.COMPRESSOR);
		frontHook = new Solenoid(HatchHookConstants.FRONT_HOOK_FORWARD);
		backHook = new Solenoid(HatchHookConstants.BACK_HOOK_FORWARD);


		compressor.setClosedLoopControl(false);

	}

	public void initDefaultCommand() {

	}

	public void pulseHooks() {
		frontHook.startPulse();
		backHook.startPulse();

	}

	public void hooksForward(){
		frontHook.set(true);
		backHook.set(true);

	}

	public void hooksReverse(){
		frontHook.set(false);
		backHook.set(false);
	}

	public void compressorStart() {
		compressor.start();
	}

	public void compressorStop() {
		compressor.stop();
	}
	public void log(){
		SmartDashboard.putBoolean("IsHookForward?", isOut);
	}
}
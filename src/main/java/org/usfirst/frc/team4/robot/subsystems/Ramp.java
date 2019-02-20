package org.usfirst.frc.team4.robot.subsystems;

import org.usfirst.frc.team4.robot.constants.RampConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for ramp
 */
public class Ramp extends Subsystem {
	// Solenoid leftPiston;
	// Solenoid rightPiston;
	
	DoubleSolenoid leftPiston;
	DoubleSolenoid rightPiston;

	public Ramp() {
		// leftPiston = new Solenoid(RampConstants.SOLENOID_LEFT);
		// rightPiston = new Solenoid(RampConstants.SOLENOID_RIGHT);

		leftPiston = new DoubleSolenoid(RampConstants.SOLENOID_LEFT_A, RampConstants.SOLENOID_LEFT_B);
		rightPiston = new DoubleSolenoid(RampConstants.SOLENOID_RIGHT_A, RampConstants.SOLENOID_RIGHT_B);
	}

	public void initDefaultCommand() {
	}
	
	public void pulsePistons(){
		// leftPiston.startPulse();
		// leftPiston.setPulseDuration(.75);

		// rightPiston.startPulse();
		// rightPiston.setPulseDuration(.75);
	}

	public void movePistons(Value direction){
		leftPiston.set(direction);
		rightPiston.set(direction);
	}
}

package org.usfirst.frc.team4.robot.subsystems;

import org.usfirst.frc.team4.robot.constants.RampConstants;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for ramp
 */
public class Ramp extends Subsystem {
	
	Solenoid leftPiston;
	Solenoid rightPiston;

	public Ramp() {
		leftPiston = new Solenoid(RampConstants.SOLENOID_LEFT);
		rightPiston = new Solenoid(RampConstants.SOLENOID_RIGHT);

	}

	public void initDefaultCommand() {
	}
	
	public void pulsePistons(){
		leftPiston.startPulse();
		leftPiston.setPulseDuration(1);

		rightPiston.startPulse();
		rightPiston.setPulseDuration(1);
	}

}
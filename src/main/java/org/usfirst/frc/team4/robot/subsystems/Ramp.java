package org.usfirst.frc.team4.robot.subsystems;

import org.usfirst.frc.team4.robot.constants.RampConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for ramp
 */
public class Ramp extends Subsystem {
	DoubleSolenoid leftPiston;
	DoubleSolenoid rightPiston;

	public Ramp() {
		leftPiston = new DoubleSolenoid(RampConstants.DOUBLE_SOLENOID_LEFT_A, RampConstants.DOUBLE_SOLENOID_LEFT_B);
		rightPiston = new DoubleSolenoid(RampConstants.DOUBLE_SOLENOID_RIGHT_A, RampConstants.DOUBLE_SOLENOID_RIGHT_B);
	}

	public void initDefaultCommand() {
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public void setPistons(Value direction){
		leftPiston.set(direction);
		rightPiston.set(direction);
	}
}

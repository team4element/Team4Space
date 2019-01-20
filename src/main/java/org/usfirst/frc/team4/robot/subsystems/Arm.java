package org.usfirst.frc.team4.robot.subsystems;

import org.usfirst.frc.team4.robot.commands.ArmMove;
import org.usfirst.frc.team4.robot.constants.ArmConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for arms
 */
public class Arm extends Subsystem {
	// Declaring Motors
	private WPI_VictorSPX leftMotor;

	//Instantiating Motors
	public Arm() {
		leftMotor = new WPI_VictorSPX(ArmConstants.MOTOR_LEFT);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ArmMove());

	}

	public void setPower(double power) {
		leftMotor.set(ControlMode.PercentOutput, power);
	}
}

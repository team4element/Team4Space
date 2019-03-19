package org.usfirst.frc.team4.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.usfirst.frc.team4.robot.commands.ArmMove;
import org.usfirst.frc.team4.robot.constants.ArmConstants;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for arms
 */
public class Arm extends Subsystem {
	// Declaring Motors
	private WPI_TalonSRX leftMotor;
	private WPI_VictorSPX rightMotor;

	private Potentiometer pot;

	//Instantiating Motors
	public Arm() {
		leftMotor = new WPI_TalonSRX(ArmConstants.MOTOR_LEFT);
		leftMotor.setNeutralMode(NeutralMode.Brake);
		
		rightMotor = new WPI_VictorSPX(ArmConstants.MOTOR_RIGHT);
		rightMotor.follow(leftMotor);
		rightMotor.setInverted(true);
		rightMotor.setNeutralMode(NeutralMode.Brake);
		pot = new AnalogPotentiometer(ArmConstants.POTENTIOMETER);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ArmMove());

	}

	public void setPower(double power) {
		leftMotor.set(ControlMode.PercentOutput, power);
	}

	public double getPot(){
	 	return pot.get()*10;
	}

	public void log(){
		SmartDashboard.putNumber("ArmPot", getPot());
	}
}

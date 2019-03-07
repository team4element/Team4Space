package org.usfirst.frc.team4.robot.subsystems;

import org.usfirst.frc.team4.robot.commands.IntakeMove;
import org.usfirst.frc.team4.robot.constants.IntakeConstants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for intake
 */
public class Intake extends Subsystem {
	//Declaring Motors
	public WPI_VictorSPX motorTop;
	public WPI_VictorSPX motorBot;

	public Intake() {

		//Instantiating Motors
		motorTop = new WPI_VictorSPX(IntakeConstants.MOTOR_LEFT);
		motorBot = new WPI_VictorSPX(IntakeConstants.MOTOR_RIGHT);
		motorBot.setInverted(true); //False for comp bot
	}

	public void initDefaultCommand() {
		setDefaultCommand(new IntakeMove());
	}

	public void setMotorsSpeed(double speed) {
		motorTop.set(speed);
		motorBot.set(speed);
	}

	public void motorsStop() {
		motorTop.set(0);
		motorBot.set(0);
	}

}

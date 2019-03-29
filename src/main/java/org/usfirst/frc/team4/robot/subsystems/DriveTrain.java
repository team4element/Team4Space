package org.usfirst.frc.team4.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.usfirst.frc.team4.robot.commands.Drive;
import org.usfirst.frc.team4.robot.constants.DriveTrainConstants;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for chassis
 */
public class DriveTrain extends Subsystem {
	// Declaring navX

	// Declaring Left Motors
	private WPI_VictorSPX leftFrontMotor;
	private WPI_TalonSRX leftMiddleMotor;
	private WPI_VictorSPX leftRearMotor;

	// Declaring Right Motors
	private WPI_VictorSPX rightFrontMotor;
	private WPI_TalonSRX rightMiddleMotor;
	private WPI_VictorSPX rightRearMotor;

	public boolean isReduced = false;

	public DriveTrain() {
		// Instantiating motors
		leftMiddleMotor = new WPI_TalonSRX(DriveTrainConstants.MOTOR_LEFT_MIDDLE);
		leftFrontMotor = new WPI_VictorSPX(DriveTrainConstants.MOTOR_LEFT_FRONT);
		leftRearMotor = new WPI_VictorSPX(DriveTrainConstants.MOTOR_LEFT_REAR);

		rightMiddleMotor = new WPI_TalonSRX(DriveTrainConstants.MOTOR_RIGHT_MIDDLE);
		rightFrontMotor = new WPI_VictorSPX(DriveTrainConstants.MOTOR_RIGHT_FRONT);
		rightRearMotor = new WPI_VictorSPX(DriveTrainConstants.MOTOR_RIGHT_REAR);

		// Setting VictorSPX's to follower mode
		leftFrontMotor.follow(leftMiddleMotor);
		leftRearMotor.follow(leftMiddleMotor);

		rightFrontMotor.follow(rightMiddleMotor);
		rightRearMotor.follow(rightMiddleMotor);

		

	}

	public void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}

	// setPower is used for teleOP
	public void setPower(double leftPower, double rightPower) {
		leftMiddleMotor.set(ControlMode.PercentOutput, leftPower);
		rightMiddleMotor.set(ControlMode.PercentOutput, rightPower);
	}

	// arcade drive is used to handle math for drive and vision PID
	// public void arcadeDrive(double leftPower, double rightPower, double angle) {
		// leftMiddleMotor.set(ControlMode.PercentOutput, leftPower);
		// rightMiddleMotor.set(ControlMode.PercentOutput, rightPower);
// 
	// }

}
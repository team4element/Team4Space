package org.usfirst.frc.team4.robot.subsystems;

import org.usfirst.frc.team4.robot.commands.Drive;
import org.usfirst.frc.team4.robot.constants.ChassisConstants;
import org.usfirst.frc.team4.robot.utilities.DriveSignal;
import org.usfirst.frc.team4.robot.utilities.ElementMath;
import org.usfirst.frc.team4.robot.utilities.LightningMath;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for chassis
 */
public class Chassis extends Subsystem {
	// Declaring navX
	private AHRS navX;

	public double slowUntil = 0;
	private double lastReversed = 0;

	boolean highGear = false;

	// Declaring Left Motors
	private WPI_VictorSPX leftFrontMotor;
	private WPI_TalonSRX leftMiddleMotor;
	private WPI_VictorSPX leftRearMotor;

	// Declaring Right Motors
	private WPI_VictorSPX rightFrontMotor;
	private WPI_TalonSRX rightMiddleMotor;
	private WPI_VictorSPX rightRearMotor;

	public Chassis() {
		// Instantiating motors
		leftMiddleMotor = new WPI_TalonSRX(ChassisConstants.MOTOR_LEFT_MIDDLE);
		leftFrontMotor = new WPI_VictorSPX(ChassisConstants.MOTOR_LEFT_FRONT);
		leftRearMotor = new WPI_VictorSPX(ChassisConstants.MOTOR_LEFT_REAR);

		rightMiddleMotor = new WPI_TalonSRX(ChassisConstants.MOTOR_RIGHT_MIDDLE);
		rightFrontMotor = new WPI_VictorSPX(ChassisConstants.MOTOR_RIGHT_FRONT);
		rightRearMotor = new WPI_VictorSPX(ChassisConstants.MOTOR_RIGHT_REAR);

		// Setting VictorSPX's to follower mode
		leftFrontMotor.follow(leftMiddleMotor);
		leftRearMotor.follow(leftMiddleMotor);

		rightFrontMotor.follow(rightMiddleMotor);
		rightRearMotor.follow(rightMiddleMotor);

		// Declaring Encoders (encoders directly plugged into TalonSRX)
		leftMiddleMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
		rightMiddleMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

		// Instantiating Gyro
		navX = new AHRS(SPI.Port.kMXP);

	}
	public void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}

	public void Brake() {
		leftMiddleMotor.set(ControlMode.PercentOutput, 0);
		rightMiddleMotor.set(ControlMode.PercentOutput, 0);
	}

	// setPower is used for teleOP
	public void setPower(double leftPower, double rightPower) {
		leftMiddleMotor.set(ControlMode.PercentOutput, leftPower);
		rightMiddleMotor.set(ControlMode.PercentOutput, rightPower);
	}

	// arcade drive is used to handle math for drive and vision PID
	public void arcadeDrive(double leftPower, double rightPower, double angle) {
		leftMiddleMotor.set(ControlMode.PercentOutput, leftPower);
		rightMiddleMotor.set(ControlMode.PercentOutput, rightPower);

	}

	public double getRightEncoder() {

		return ElementMath.ticksToInches(-rightMiddleMotor.getSelectedSensorPosition(0),
				ChassisConstants.circumference, ChassisConstants.gearRatio, ChassisConstants.ticksPerRevolution);

	}

	public double getLeftEncoder() {
		return ElementMath.ticksToInches(leftMiddleMotor.getSelectedSensorPosition(0), ChassisConstants.circumference,
				ChassisConstants.gearRatio, ChassisConstants.ticksPerRevolution);
	}

	public double getDistance() {
		return ((getLeftEncoder() + getRightEncoder()) / 2);

	}

	public double getRawLeftEncoder() {
		return -leftMiddleMotor.getSelectedSensorPosition(0);
	}

	public double getRawRightEncoder() {
		return rightMiddleMotor.getSelectedSensorPosition(0);
	}

	public void reset() {
		navX.reset();
		leftMiddleMotor.setSelectedSensorPosition(0, 0, 0);
		rightMiddleMotor.setSelectedSensorPosition(0, 0, 0);
	}

	public double getGyro() {
		return navX.getYaw();
	}

	public void setVelocityIPS(double left, double right) {
        setVelocity(LightningMath.ips2talon(left), LightningMath.ips2talon(right));
	}
	private void checkReverse(double left, double right) {
        if (left < 0 && right < 0) {
            if (Timer.getFPGATimestamp() - lastReversed > 0.3) {
                lastReversed = Timer.getFPGATimestamp();
                // Logger.debug("REV POWER: " + left + ", " + right);
                for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                    // Logger.debug("REV STACK: " + ste.getFileName() + ": " + ste.getMethodName() + ":" + ste.getLineNumber());
                }
            }
        }
    }
    public void setVelocity(double left, double right) {
        SmartDashboard.putNumber("slowuntil", slowUntil);
        SmartDashboard.putNumber("timer", Timer.getFPGATimestamp());
        setControlMode(ControlMode.Velocity, 0);
        if (Timer.getFPGATimestamp() < slowUntil) {
            SmartDashboard.putString("slowing", "true");
            double vel = getAverageSpeed() * ChassisConstants.slowDownRate;
            checkReverse(vel,vel);
            leftMiddleMotor.set(ControlMode.Velocity, vel);
            rightMiddleMotor.set(ControlMode.Velocity, vel);
        } else {
            SmartDashboard.putString("slowing", "false");
            SmartDashboard.putNumber("left target vel", leftMiddleMotor.getClosedLoopTarget(0));
            SmartDashboard.putNumber("right target vel", rightMiddleMotor.getClosedLoopTarget(0));
            checkReverse(left, right);
            leftMiddleMotor.set(ControlMode.Velocity, left);
            rightMiddleMotor.set(ControlMode.Velocity, right);
        }
	}
	public boolean isHighGear() {
        return highGear;
    }
	public double getAverageSpeed() {
        int pidIdx = (isHighGear() ? ChassisConstants.HIGHGEAR_IDX : ChassisConstants.LOWGEAR_IDX);
        double leftSpeed = leftMiddleMotor.getSelectedSensorVelocity(pidIdx);
        double rightSpeed = rightMiddleMotor.getSelectedSensorVelocity(pidIdx);
        return (leftSpeed  + rightSpeed) / 2;
    }
	protected double limit(double value ) {
		if (value > 1.0) {
			return 1.0;
		}
		if (value < -1.0) {
			return -1.0;
		}
		return value;
	}

	public void arcadeDrive(double xSpeed, double zRotation) {

		xSpeed = limit(xSpeed);

		zRotation = limit(zRotation);

		double leftMotorOutput;
		double rightMotorOutput;

		double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);

		if (xSpeed >= 0.0) {
			// First quadrant, else second quadrant
			if (zRotation >= 0.0) {
				leftMotorOutput = maxInput;
				rightMotorOutput = xSpeed - zRotation;
			} else {
				leftMotorOutput = xSpeed + zRotation;
				rightMotorOutput = maxInput;
			}
		} else {
			// Third quadrant, else fourth quadrant
			if (zRotation >= 0.0) {
				leftMotorOutput = xSpeed + zRotation;
				rightMotorOutput = maxInput;
			} else {
				leftMotorOutput = maxInput;
				rightMotorOutput = xSpeed - zRotation;
			}
		}

		setPower(limit(leftMotorOutput), -limit(rightMotorOutput));

	}
	public void setControlMode(ControlMode mode, int profile){
		leftMiddleMotor.set(mode, profile);
		rightMiddleMotor.set(mode, profile);
	}

	public void log() {
		SmartDashboard.putNumber("Left Encoder", getLeftEncoder());
		SmartDashboard.putNumber("Right Encoder", getRightEncoder());
		SmartDashboard.putNumber("Raw Left Encoder", getRawLeftEncoder());
		SmartDashboard.putNumber("Raw Right Encoder", getRawRightEncoder());
		SmartDashboard.putNumber("Encoders", getDistance());
		SmartDashboard.putNumber("Angle", getGyro());
	}
}
package com.team4.robot.subsystems;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.team195.lib.drivers.CKTalonSRX;
import com.team195.lib.drivers.NavX;
import com.team195.lib.drivers.TalonHelper;
import com.team195.lib.motion.Kinematics;
import com.team195.lib.motion.Lookahead;
import com.team195.lib.motion.Path;
import com.team195.lib.motion.PathFollower;
import com.team195.lib.motion.PathFollowerRobotState;
import com.team195.lib.motion.RigidTransform2d;
import com.team195.lib.motion.Rotation2d;
import com.team195.lib.motion.Twist2d;
import com.team195.lib.motion.Util;
import com.team195.lib.reporters.ConsoleReporter;
import com.team195.lib.reporters.MessageLevel;
import com.team195.lib.util.DriveMotorValues;
import com.team254.lib.util.CheesyDriveHelper;
import com.team4.lib.util.SynchronusPID;
import com.team4.robot.constants.AutoConstants;
import com.team4.robot.constants.Constants;
import com.team4.robot.constants.DriveConstants;
import com.team4.robot.constants.PathFollowingConstants;
import com.team4.robot.controllers.Controllers;
import com.team4.robot.controllers.DriveControllers;
import com.team4.robot.loops.Loop;
import com.team4.robot.loops.Looper;

import edu.wpi.first.wpilibj.DriverStation;

public class Drive implements Subsystem{
	private static final int kLowGearPIDSlot = 0;
	private static final int kHighGearPIDSlot = 1;
	private static Drive instance = null;
	private static ReentrantLock _subsystemMutex = new ReentrantLock();
	private PathFollowerRobotState mRobotState = PathFollowerRobotState.getInstance();
	private DriveControlState mControlMode;
	private CKTalonSRX mLeftMaster, mRightMaster;
	private BaseMotorController leftDriveSlave1, leftDriveSlave2, rightDriveSlave1, rightDriveSlave2;
	private DriverStation ds;
	private NavX mNavXBoard;
	private boolean mPrevBrakeModeVal;
	private double leftDriveSpeed, rightDriveSpeed;
	private Path mCurrentPath = null;
	private PathFollower mPathFollower;
	private Rotation2d mTargetHeading = new Rotation2d();
	private boolean mIsOnTarget = false;
	private boolean mIsDriveFaulted = false;

	private double mVisionThrottle;

	private int methodCounter = 0;

	private SynchronusPID mTurnPID;

	private boolean emergencySafetyRequired = false;

	private final Loop mLoop = new Loop() {
		@Override
		public void onFirstStart(double timestamp) {
			synchronized (Drive.this) {
				subsystemHome();
			}
		}

		@Override
		public void onStart(double timestamp) {
			synchronized (Drive.this) {
				setOpenLoop(DriveMotorValues.NEUTRAL);
				setBrakeMode(false);
				setVelocity(new DriveMotorValues(0, 0));
			}
		}

		@Override
		public void onLoop(double timestamp, boolean isAuto) {
			synchronized (Drive.this) {
//				SmartDashboard.putNumber("Left Drive Velocity", getLeftVelocityInchesPerSec());
//				SmartDashboard.putNumber("Right Drive Velocity", getRightVelocityInchesPerSec());
				switch (mControlMode) {
					case OPEN_LOOP:
						break;
					case VISION:
						autoSteer(mVisionThrottle);
						break;
					case VELOCITY:
						break;
					case TURN_TO_HEADING:
						updateTurnToHeading(timestamp);
						break;
					case PATH_FOLLOWING:
						if (mPathFollower != null) {
							updatePathFollower(timestamp);
							//mCSVWriter.add(mPathFollower.getDebug());
						}
						break;
					default:
						ConsoleReporter.report("Unexpected drive control state: " + mControlMode);
						break;
				}

				if (mControlMode != DriveControlState.PATH_FOLLOWING)
					emergencySafetyRequired = mNavXBoard.isCollisionOccurring() || mNavXBoard.isTipping();

			}
		}
		@Override
		public void onStop(double timestamp) {
			setOpenLoop(DriveMotorValues.NEUTRAL);
		}
	};

	@Override
	public void registerEnabledLoops(Looper in) {
		in.register(mLoop);
	}

	private Drive() throws Exception {
		ds = DriverStation.getInstance();
		_subsystemMutex = new ReentrantLock();

		DriveControllers robotControllers = DriveControllers.getInstance();
		mLeftMaster = robotControllers.getLeftMaster();
		leftDriveSlave1 = robotControllers.getLeftSlaveA();
		leftDriveSlave2 = robotControllers.getLeftSlaveB();
		mRightMaster = robotControllers.getRightMaster();
		rightDriveSlave1 = robotControllers.getRightSlaveA();
		rightDriveSlave2 = robotControllers.getRightSlaveB();
		mNavXBoard = robotControllers.getNavX();


		leftDriveSpeed = 0;
		rightDriveSpeed = 0;

		mPrevBrakeModeVal = false;
		setBrakeMode(true);

		mControlMode = DriveControlState.PATH_FOLLOWING;

//		tuneableLeftDrive = new TuneablePID("Left Drive Tuning", mLeftMaster, leftSetpointValue, 5808, true, true);
//		tuneableRightDrive = new TuneablePID("Right Drive Tuning", mRightMaster, rightSetpointValue, 5809, true, true);
//		tuneableLeftDrive.start();
//		tuneableRightDrive.start();
//		mLeftMaster.set(ControlMode.MotionMagic, 0);
//		mRightMaster.set(ControlMode.MotionMagic, 0);

	}

	public static Drive getInstance() {
		if(instance == null) {
			try {
				instance = new Drive();
			} catch (Exception ex) {
				ConsoleReporter.report(ex, MessageLevel.DEFCON1);
			}
		}

		return instance;
	}

	public static Drive getInstance(List<Subsystem> subsystemList) {
		subsystemList.add(getInstance());
		return instance;
	}
    @Override
    public void init() {
        mLeftMaster.setSensorPhase(true);

		mRightMaster.setSensorPhase(true);
		mRightMaster.setInverted(true);
		rightDriveSlave1.setInverted(true);
		rightDriveSlave2.setInverted(true);

		setBrakeMode(true);

		boolean setSucceeded;
		int retryCounter = 0;

		do {
			setSucceeded = true;

			setSucceeded &= mLeftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.kTimeoutMs) == ErrorCode.OK;
			setSucceeded &= mLeftMaster.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_10Ms, Constants.kTimeoutMs) == ErrorCode.OK;
			setSucceeded &= mLeftMaster.configVelocityMeasurementWindow(32, Constants.kTimeoutMs) == ErrorCode.OK;

			setSucceeded &= mRightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.kTimeoutMs) == ErrorCode.OK;
			setSucceeded &= mRightMaster.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_10Ms, Constants.kTimeoutMs) == ErrorCode.OK;
			setSucceeded &= mRightMaster.configVelocityMeasurementWindow(32, Constants.kTimeoutMs) == ErrorCode.OK;

		} while(!setSucceeded && retryCounter++ < Constants.kTalonRetryCount);

		setSucceeded &= TalonHelper.setPIDGains(mLeftMaster, kLowGearPIDSlot, AutoConstants.kDriveLeftVelocityKp, AutoConstants.kDriveLeftVelocityKi, AutoConstants.kDriveLeftVelocityKd, AutoConstants.kDriveLeftVelocityKf, AutoConstants.kDriveVelocityRampRate, AutoConstants.kDriveLeftVelocityIZone);
		setSucceeded &= TalonHelper.setPIDGains(mLeftMaster, kHighGearPIDSlot, AutoConstants.kDriveLeftVelocityKp, AutoConstants.kDriveLeftVelocityKi, AutoConstants.kDriveLeftVelocityKd, AutoConstants.kDriveLeftVelocityKf, AutoConstants.kDriveVelocityRampRate, AutoConstants.kDriveLeftVelocityIZone);
		setSucceeded &= TalonHelper.setPIDGains(mRightMaster, kLowGearPIDSlot, AutoConstants.kDriveRightVelocityKp, AutoConstants.kDriveRightVelocityKi, AutoConstants.kDriveRightVelocityKd, AutoConstants.kDriveRightVelocityKf, AutoConstants.kDriveVelocityRampRate, AutoConstants.kDriveRightVelocityIZone);
		setSucceeded &= TalonHelper.setPIDGains(mRightMaster, kHighGearPIDSlot, AutoConstants.kDriveRightVelocityKp, AutoConstants.kDriveRightVelocityKi, AutoConstants.kDriveRightVelocityKd, AutoConstants.kDriveRightVelocityKf, AutoConstants.kDriveVelocityRampRate, AutoConstants.kDriveRightVelocityIZone);
		setSucceeded &= TalonHelper.setMotionMagicParams(mLeftMaster, (int)AutoConstants.kDriveMaxVelocity, (int)AutoConstants.kDriveMaxAccel);
		setSucceeded &= TalonHelper.setMotionMagicParams(mRightMaster, (int)AutoConstants.kDriveMaxVelocity, (int)AutoConstants.kDriveMaxAccel);

		mLeftMaster.selectProfileSlot(kHighGearPIDSlot, 0);
		mRightMaster.selectProfileSlot(kHighGearPIDSlot, 0);

		mTurnPID = new SynchronusPID(AutoConstants.kDriveTurnP, AutoConstants.kDriveTurnI, AutoConstants.kDriveTurnD, false);

		if (retryCounter >= Constants.kTalonRetryCount || !setSucceeded)
			ConsoleReporter.report("Failed to initialize DriveBaseSubsystem!!!", MessageLevel.DEFCON1);
        
    }
	@Override
	public void subsystemHome() {
		mNavXBoard.zeroYaw();

		boolean setSucceeded;
		int retryCounter = 0;

		do {
			setSucceeded = true;

			setSucceeded &= mLeftMaster.getSensorCollection().setQuadraturePosition(0, Constants.kTimeoutMsFast) == ErrorCode.OK;
			setSucceeded &= mRightMaster.getSensorCollection().setQuadraturePosition(0, Constants.kTimeoutMsFast) == ErrorCode.OK;
			} while(!setSucceeded && retryCounter++ < Constants.kTalonRetryCount);

		if (retryCounter >= Constants.kTalonRetryCount || !setSucceeded)
			ConsoleReporter.report("Failed to zero DriveBaseSubsystem!!!", MessageLevel.DEFCON1);
	}



	public DriveControlState getControlMode() {
		return mControlMode;
	}

	public void setControlMode(DriveControlState controlMode) {
		if (controlMode != mControlMode) {
			try {
				_subsystemMutex.lock();
				mControlMode = controlMode;
				_subsystemMutex.unlock();
			} catch (Exception ex) {
				ConsoleReporter.report(ex);
			}
		}
	}

	public synchronized void setOpenLoop(DriveMotorValues d) {
		setControlMode(DriveControlState.OPEN_LOOP);

		mLeftMaster.set(ControlMode.PercentOutput, d.leftDrive);
		mRightMaster.set(ControlMode.PercentOutput, d.rightDrive);
	}

	public synchronized void setCheesyishDrive(double throttle, double wheel, boolean quickTurn) {
        if (Util.epsilonEquals(throttle, 0.0, 0.04)) {
            throttle = 0.0;
        }

        if (Util.epsilonEquals(wheel, 0.0, 0.035)) {
            wheel = 0.0;
        }

        final double kWheelGain = 0.05;
        final double kWheelNonlinearity = 0.05;
        final double denominator = Math.sin(Math.PI / 2.0 * kWheelNonlinearity);
        // Apply a sin function that's scaled to make it feel better.
        if (!quickTurn) {
            wheel = Math.sin(Math.PI / 2.0 * kWheelNonlinearity * wheel);
            wheel = Math.sin(Math.PI / 2.0 * kWheelNonlinearity * wheel);
            wheel = wheel / (denominator * denominator);
        }

        wheel *= kWheelGain;
        Kinematics.DriveVelocity signal = Kinematics.inverseKinematics(new Twist2d(throttle, 0.0, wheel));
        double scaling_factor = Math.max(1.0, Math.max(Math.abs(signal.left), Math.abs(signal.right)));
        setOpenLoop(new DriveMotorValues(signal.left/ scaling_factor, signal.right / scaling_factor));
	}
	
	public synchronized void autoSteer(double throttle){
		CheesyDriveHelper driveHelper = new CheesyDriveHelper();
		double turn = Math.max(Math.min(VisionTracker.getInstance().getTargetHorizAngleDev() * 0.01, 0.1), -0.1);

		DriveMotorValues signal = driveHelper.cheesyDrive(throttle, turn, true);
		mLeftMaster.set(ControlMode.PercentOutput, signal.leftDrive);
		mRightMaster.set(ControlMode.PercentOutput, signal.rightDrive);

	}


	public synchronized void setVelocity(DriveMotorValues d) {
		setVelocity(d, true);
	}

	public synchronized void setVelocity(DriveMotorValues d, boolean autoChangeMode) {
		if (autoChangeMode)
			setControlMode(DriveControlState.VELOCITY);
		mLeftMaster.set(ControlMode.Velocity, Util.convertRPMToNativeUnits(d.leftDrive), kHighGearPIDSlot);
		mRightMaster.set(ControlMode.Velocity, Util.convertRPMToNativeUnits(d.rightDrive), kHighGearPIDSlot);
	}

	public void setBrakeMode(boolean brakeMode) {
		if (mPrevBrakeModeVal != brakeMode) {
			_subsystemMutex.lock();
			mLeftMaster.setNeutralMode(brakeMode ? NeutralMode.Brake : NeutralMode.Coast);
			leftDriveSlave1.setNeutralMode(brakeMode ? NeutralMode.Brake : NeutralMode.Coast);
			leftDriveSlave2.setNeutralMode(brakeMode ? NeutralMode.Brake : NeutralMode.Coast);
			mRightMaster.setNeutralMode(brakeMode ? NeutralMode.Brake : NeutralMode.Coast);
			rightDriveSlave1.setNeutralMode(brakeMode ? NeutralMode.Brake : NeutralMode.Coast);
			rightDriveSlave2.setNeutralMode(brakeMode ? NeutralMode.Brake : NeutralMode.Coast);
			mPrevBrakeModeVal = brakeMode;
			_subsystemMutex.unlock();
		}
	}

	/**
	 * Called periodically when the robot is in path following mode. Updates the path follower with the robots latest
	 * pose, distance driven, and velocity, then updates the wheel velocity setpoints.
	 */
	private void updatePathFollower(double timestamp) {
		RigidTransform2d robot_pose = mRobotState.getLatestFieldToVehicle().getValue();
		Twist2d command = mPathFollower.update(timestamp, robot_pose,
				PathFollowerRobotState.getInstance().getDistanceDriven(), PathFollowerRobotState.getInstance().getPredictedVelocity().dx);

		if (!mPathFollower.isFinished()) {
			Kinematics.DriveVelocity setpoint = Kinematics.inverseKinematics(command);
			updatePathVelocitySetpoint(setpoint.left, setpoint.right);

			//ConsoleReporter.report(mPathFollower.getDebug());
			//ConsoleReporter.report("Left2Cube: " + inchesPerSecondToRpm(setpoint.left) + ", Right2Cube: " + inchesPerSecondToRpm(setpoint.right));
			//ConsoleReporter.report("Left2Cube Actual: " + Util.convertNativeUnitsToRPM(mLeftMaster.getSelectedSensorVelocity(0)) + ", Right2Cube Actual: " + Util.convertNativeUnitsToRPM(mRightMaster.getSelectedSensorVelocity(0)));
		} else {
			updatePathVelocitySetpoint(0, 0);
			ConsoleReporter.report("Completed path!");
			setControlMode(DriveControlState.VELOCITY);
		}
	}

	private void updatePathVelocitySetpoint(double left_inches_per_sec, double right_inches_per_sec) {
		final double max_desired = Math.max(Math.abs(left_inches_per_sec), Math.abs(right_inches_per_sec));
		final double scale = max_desired > AutoConstants.kDriveMaxSetpoint ? AutoConstants.kDriveMaxSetpoint / max_desired : 1.0;

		mLeftMaster.set(ControlMode.Velocity, Util.convertRPMToNativeUnits(inchesPerSecondToRpm(left_inches_per_sec * scale)), kHighGearPIDSlot);
		mRightMaster.set(ControlMode.Velocity, Util.convertRPMToNativeUnits(inchesPerSecondToRpm(right_inches_per_sec * scale)), kHighGearPIDSlot);

		//ConsoleReporter.report("Requested Drive Velocity Left2Cube/Right2Cube: " + left_inches_per_sec + "/" + right_inches_per_sec);
		//ConsoleReporter.report("Actual Drive Velocity Left2Cube/Right2Cube: " + getLeftVelocityInchesPerSec() + "/" + getRightVelocityInchesPerSec());
	}

	private static double rotationsToInches(double rotations) {
		return rotations * (DriveConstants.kDriveWheelCircumferenceInches * DriveConstants.kGearRatio);
	}

	private static double rpmToInchesPerSecond(double rpm) {
		return rotationsToInches(rpm) / 60;
	}

	private static double inchesToRotations(double inches) {
		return inches / (DriveConstants.kDriveWheelCircumferenceInches * DriveConstants.kGearRatio);
	}

	private static double inchesPerSecondToRpm(double inches_per_second) {
		return inchesToRotations(inches_per_second) * 60;
	}

	public double getLeftDistanceInches() {
		return rotationsToInches(mLeftMaster.getSelectedSensorPosition(0)/DriveConstants.kSensorUnitsPerRotation);
	}

	public double getRightDistanceInches() {
		return rotationsToInches(mRightMaster.getSelectedSensorPosition(0)/DriveConstants.kSensorUnitsPerRotation);
	}

	public double getLeftVelocityInchesPerSec() { return rpmToInchesPerSecond(Util.convertNativeUnitsToRPM(mLeftMaster.getSelectedSensorVelocity(0))); }

	public double getRightVelocityInchesPerSec() { return rpmToInchesPerSecond(Util.convertNativeUnitsToRPM(mRightMaster.getSelectedSensorVelocity(0))); }

	public synchronized Rotation2d getGyroAngle() {
		return mNavXBoard.getYaw();
	}

	public synchronized double getRoll() { return mNavXBoard.getRoll(); }

	public synchronized double getPitch() { return mNavXBoard.getPitch(); }

	public synchronized NavX getNavXBoard() {
		return mNavXBoard;
	}

	public synchronized double getAngleDegrees(){
		return mNavXBoard.getAngleDegress();
	}

	public synchronized void setGyroAngle(Rotation2d angle) {
		mNavXBoard.reset();
		mNavXBoard.setAngleAdjustment(angle);
	}

	public synchronized double getGyroVelocityDegreesPerSec() {
		return mNavXBoard.getYawRateDegreesPerSec();
	}
	
	public void setVisionMode(double throttle){
		setControlMode(DriveControlState.VISION);
		mVisionThrottle = throttle;
	}

	/**
	 * Configures the drivebase to turn to a desired heading
	 */
	public synchronized void setWantTurnToHeading(double heading) {
		if (mControlMode != DriveControlState.TURN_TO_HEADING) {
			mControlMode = DriveControlState.TURN_TO_HEADING;
		// 	updatePositionSetpoint(getLeftDistanceInches(), getRightDistanceInches());
		}
		// if (Math.abs(heading.inverse().rotateBy(mTargetHeading).getDegrees()) > 1E-3) {
		// 	mTargetHeading = heading;
		// 	mIsOnTarget = false;
		// }
		mTurnPID.setSetpoint(heading);
		mIsOnTarget = false;
	}

	public synchronized void updateTurnToHeading(double timestamp){
		double wheel_delta = mTurnPID.calculate(getAngleDegrees());

		mIsOnTarget = mTurnPID.onTarget(.5);
		mLeftMaster.set(ControlMode.PercentOutput, wheel_delta*.5);
		mRightMaster.set(ControlMode.PercentOutput, -wheel_delta*.5);
	}

	/**
	 * Turn the robot to a target heading.
	 *
	 * Is called periodically when the robot is auto-aiming towards the boiler.
	 */
// 	private void updateTurnToHeading(double timestamp) {
// //        if (Superstructure.getInstance().isShooting()) {
// //            // Do not update heading while shooting - just base lock. By not updating the setpoint, we will fight to
// //            // keep position.
// //            return;
// //        }	
// 		//SmartDashboard.putBoolean("TurnOnTarget", mIsOnTarget);
// 		final Rotation2d field_to_robot = mRobotState.getLatestFieldToVehicle().getValue().getRotation();

// 		// Figure out the rotation necessary to turn to face the goal.
// 		final Rotation2d robot_to_target = field_to_robot.inverse().rotateBy(mTargetHeading);

// 		// Check if we are on target
// 		final double kGoalPosTolerance = 0.5; // degrees
// 		final double kGoalVelTolerance = 3.0; // inches per second
// 		if (Math.abs(robot_to_target.getDegrees()) < kGoalPosTolerance
// 				&& Math.abs(getLeftVelocityInchesPerSec()) < kGoalVelTolerance
// 				&& Math.abs(getRightVelocityInchesPerSec()) < kGoalVelTolerance) {
// 			// Use the current setpoint and base lock.
// 			mIsOnTarget = true;
// 			updatePositionSetpoint(getLeftDistanceInches(), getRightDistanceInches());
// 			return;
// 		}

// 		Kinematics.DriveVelocity wheel_delta = Kinematics
// 				.inverseKinematics(new Twist2d(0, 0, robot_to_target.getRadians()));
// 		updatePositionSetpoint(wheel_delta.left + getLeftDistanceInches(),
// 				wheel_delta.right + getRightDistanceInches());
// 	}

	/**
	 * Adjust position setpoint (if already in position mode)
	 *
	 * @param left_position_inches
	 * @param right_position_inches
	 */
	private synchronized void updatePositionSetpoint(double left_position_inches, double right_position_inches) {
		mLeftMaster.set(ControlMode.MotionMagic, inchesToRotations(left_position_inches) * 4096, kLowGearPIDSlot);
		mRightMaster.set(ControlMode.MotionMagic, inchesToRotations(right_position_inches) * 4096, kLowGearPIDSlot);
	}

	/**
	 * Configures the drivebase to drive a path. Used for autonomous driving
	 *
	 * @see Path
	 */
	public synchronized void setWantDrivePath(Path path, boolean reversed) {
		if (mCurrentPath != path || mControlMode != DriveControlState.PATH_FOLLOWING) {
			setControlMode(DriveControlState.PATH_FOLLOWING);
			PathFollowerRobotState.getInstance().resetDistanceDriven();
			mPathFollower = new PathFollower(path, reversed,
					new PathFollower.Parameters(
							new Lookahead(PathFollowingConstants.kMinLookAhead, PathFollowingConstants.kMaxLookAhead,
									PathFollowingConstants.kMinLookAheadSpeed, PathFollowingConstants.kMaxLookAheadSpeed),
							PathFollowingConstants.kInertiaSteeringGain, PathFollowingConstants.kPathFollowingProfileKp,
							PathFollowingConstants.kPathFollowingProfileKi, PathFollowingConstants.kPathFollowingProfileKv,
							PathFollowingConstants.kPathFollowingProfileKffv, PathFollowingConstants.kPathFollowingProfileKffa,
							PathFollowingConstants.kPathFollowingMaxVel, PathFollowingConstants.kPathFollowingMaxAccel,
							PathFollowingConstants.kPathFollowingGoalPosTolerance, PathFollowingConstants.kPathFollowingGoalVelTolerance,
							PathFollowingConstants.kPathStopSteeringDistance));

			mCurrentPath = path;
		} else {
			ConsoleReporter.report("Error setting path for drive!", MessageLevel.ERROR);
		}
	}

	public synchronized boolean isEmergencySafetyRequired() {
		return emergencySafetyRequired;
	}

	public synchronized boolean isDoneWithTurn() {
		if (mControlMode == DriveControlState.TURN_TO_HEADING) {
			ConsoleReporter.report("Robot has completed turning");
			return mIsOnTarget;
		} else {
			ConsoleReporter.report("Robot is not in turning mode");
			return true;
		}
	}

	public synchronized boolean isDoneWithPath() {
		if (mControlMode == DriveControlState.PATH_FOLLOWING && mPathFollower != null) {
			ConsoleReporter.report("Robot has completed the path");
			return mPathFollower.isFinished();
		} else {
			ConsoleReporter.report("Robot is not in path following mode");
			if (mPathFollower != null)
				return mPathFollower.isFinished();
			else
				return true;
		}
	}

	public synchronized void forceDoneWithPath() {
		if (mControlMode == DriveControlState.PATH_FOLLOWING && mPathFollower != null) {
			ConsoleReporter.report("Forcing robot to stop the path");
			mPathFollower.forceFinish();
		} else {
			ConsoleReporter.report("Robot is not in path following mode");
		}
	}

	public synchronized boolean hasPassedMarker(String marker) {
		if (mControlMode == DriveControlState.PATH_FOLLOWING && mPathFollower != null) {
			ConsoleReporter.report("Robot has passed marker " + marker);
			return mPathFollower.hasPassedMarker(marker);
		} else {
			ConsoleReporter.report("Robot is not in path following mode");
			if (mPathFollower != null)
				return (mPathFollower.isFinished() || mPathFollower.hasPassedMarker(marker));
			else {
				//TODO: Test with false value
				return true;
			}
		}
	}
	public enum DriveControlState{
		OPEN_LOOP,
		VISION,
		POSITION,
		VELOCITY,
		TURN_TO_HEADING,
		PATH_FOLLOWING,
		TEST
	};


}

package com.team4.robot;

import java.util.Optional;

import com.team254.lib.geometry.Pose2d;
import com.team254.lib.geometry.Rotation2d;
import com.team254.lib.util.CheesyDriveHelper;
import com.team254.lib.util.CrashTracker;
import com.team254.lib.wpilib.TimedRobot;
import com.team4.lib.util.ElementMath;
import com.team4.robot.auto.AutoModeExecutor;
import com.team4.robot.auto.modes.AutoModeBase;
import com.team4.robot.auto.modes.DriveForwardAndTrackAction;
import com.team4.robot.auto.modes.TuneTurnToHeadingMode;
import com.team4.robot.constants.Constants;
import com.team4.robot.controlboard.ControlBoard;
import com.team4.robot.controlboard.IControlBoard;
import com.team4.robot.loops.Looper;
import com.team4.robot.subsystems.Drive;
import com.team4.robot.subsystems.RobotStateEstimator;
import com.team4.robot.subsystems.VisionTracker;

import edu.wpi.first.wpilibj.Timer;

public class Robot extends TimedRobot {
  private final Looper mEnabledLooper = new Looper();
  private final Looper mDisabledLooper = new Looper();

  private final IControlBoard mControlBoard = ControlBoard.getInstance();

  private final SubsystemManager mSubsystemManager = SubsystemManager.getInstance();
  // private final VisionManager mLLManager = VisionManager.getInstance();
  private final VisionTracker mVisionTracker = VisionTracker.getInstance();

  private final Drive mDrive = Drive.getInstance();

  private final RobotState mRobotState = RobotState.getInstance();
  private final RobotStateEstimator mRobotStateEstimator = RobotStateEstimator.getInstance();

  private AutoModeExecutor mAutoModeExecutor;

  // private LatchedBoolean mAutoSteerPressed = new LatchedBoolean();
   
  
  Robot() {
    CrashTracker.logRobotConstruction();
  }

  @Override
  public void robotInit() {
    try {
    System.out.println("Starting robot init");
      mSubsystemManager.setSubsystems(
        mRobotStateEstimator,
        mDrive,
        mVisionTracker);
      
        mSubsystemManager.registerEnabledLoops(mEnabledLooper);
        mSubsystemManager.registerDisabledLoops(mDisabledLooper);

        mRobotState.reset(Timer.getFPGATimestamp(), Pose2d.identity(), Rotation2d.identity());
        mDrive.setHeading(Rotation2d.identity());

        // mLLManager.setAllLeds(Limelight.LedMode.OFF);

        System.out.println("Finished robot init");
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
  }

  @Override
  public void disabledInit(){
    try {
      CrashTracker.logDisabledInit();
      mEnabledLooper.stop();

      // Reset all auto mode state.
      if (mAutoModeExecutor != null) {
          mAutoModeExecutor.stop();
      }
      mAutoModeExecutor = new AutoModeExecutor();


      mDisabledLooper.start();

    //   mLLManager.setAllLeds(Limelight.LedMode.OFF);
      // mLLManager.triggerOutputs();

      mDrive.setBrakeMode(false);
      // mLLManager.writePeriodicOutputs();
  } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
  }
  }

  @Override
  public void autonomousInit() {
    try {
      CrashTracker.logAutoInit();
      mDisabledLooper.stop();

      // Robot starts forwards.
      mRobotState.reset(Timer.getFPGATimestamp(), Pose2d.identity(), Rotation2d.identity());
      mDrive.setHeading(Rotation2d.identity());


      mAutoModeExecutor.start();

      mEnabledLooper.start();

      // mLLManager.setUseTopLimelight(true);
      // mLLManager.setPipeline(Limelight.kDefaultPipeline);
      // mLLManager.setAllLeds(Limelight.LedMode.ON);

    } catch (Throwable t) {
        CrashTracker.logThrowableCrash(t);
        throw t;
    }
  }

  @Override
  public void teleopInit() {
    // Reset all auto mode state.
    if (mAutoModeExecutor != null) {
        mAutoModeExecutor.stop();
    }
    mAutoModeExecutor = new AutoModeExecutor();

    mDisabledLooper.stop();
    mEnabledLooper.start();

    // mLLManager.setAllLeds(Limelight.LedMode.ON);
  }


  @Override
  public void robotPeriodic() {
      try {
          mSubsystemManager.outputToSmartDashboard();
          mRobotState.outputToSmartDashboard();
          mVisionTracker.readPeriodicInputs();
          mVisionTracker.writePeriodicOutputs();
      } catch (Throwable t) {
          CrashTracker.logThrowableCrash(t);
          throw t;
      }
  }

  @Override
  public void disabledPeriodic() {
      try {
            //   System.out.println("Zeroing Robot!");
              // mLLManager.triggerOutputs();
              // mLLManager.writePeriodicOutputs();

        // mDrive.zeroSensors();

          Optional<AutoModeBase> autoMode = Optional.of(new DriveForwardAndTrackAction());
          if (autoMode.isPresent() && autoMode.get() != mAutoModeExecutor.getAutoMode()) {
            //   System.out.println("Set auto mode to: " + autoMode.get().getClass().toString());
              mAutoModeExecutor.setAutoMode(autoMode.get());
          }
      } catch (Throwable t) {
          CrashTracker.logThrowableCrash(t);
          throw t;
      }
  }
  
  @Override
  public void autonomousPeriodic() {
    // manualControl(true);
  }


  @Override
  public void teleopPeriodic() {
        try {
            manualControl(/*sandstorm=*/false);
        } catch (Throwable t) {
            CrashTracker.logThrowableCrash(t);
            throw t;
    }
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  public void manualControl(boolean sandstorm){
    CheesyDriveHelper mDriveHelper = new CheesyDriveHelper();
    
    double throttle = ElementMath.handleDeadband(mControlBoard.getThrottle(), Constants.kJoystickThreshold);
    double turn;
    
    boolean wants_auto_steer = mControlBoard.getVisionEnable();
 
    mVisionTracker.setVisionEnabled(wants_auto_steer);
    
    if (mVisionTracker.isVisionEnabled()) {
    
      turn = Math.max(Math.min(VisionTracker.getInstance().getTargetHorizAngleDev() * 0.01, 0.1), -0.1);
      mDrive.setOpenLoop(mDriveHelper.cheesyDrive(throttle, turn, true));
      // mDrive.autoSteer(Util.limit(throttle, 0.3), drive_aim_params.get());
    } else{
      turn = ElementMath.handleDeadband(-mControlBoard.getTurn(), Constants.kJoystickThreshold);
      mDrive.setCheesyishDrive(throttle, turn, mControlBoard.getQuickTurn());
    }

  }

}

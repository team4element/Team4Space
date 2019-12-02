package com.team4.robot;

import java.util.Arrays;
import java.util.Optional;

import com.team254.lib.geometry.Pose2d;
import com.team254.lib.util.CrashTracker;
import com.team254.lib.util.DriveSignal;
import com.team4.robot.auto.AutoModeBase;
import com.team4.robot.auto.AutoModeExecutor;
import com.team4.robot.auto.modes.TestMode;
import com.team4.robot.loops.Looper;
import com.team4.robot.paths.TrajectoryGenerator;
import com.team4.robot.subsystems.Drive;
import com.team4.robot.subsystems.RobotStateEstimator;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  private Looper mEnabledLooper = new Looper();
  private Looper mDisabledLooper = new Looper();

  private TrajectoryGenerator mTrajectoryGenerator = TrajectoryGenerator.getInstance();

  private final SubsystemManager mSubsystemManager = new SubsystemManager(
    Arrays.asList(
            RobotStateEstimator.getInstance(),
            Drive.getInstance()
    )
  );

  private Drive mDrive = Drive.getInstance();

  private AutoModeExecutor mAutoModeExecutor;

  public Robot() {
    CrashTracker.logRobotConstruction();
  }


  @Override
  public void robotInit() {
    try {
      CrashTracker.logRobotInit();

      mSubsystemManager.registerEnabledLoops(mEnabledLooper);
      mSubsystemManager.registerDisabledLoops(mDisabledLooper);

      mTrajectoryGenerator.generateTrajectories();


    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
  }

  @Override
  public void disabledInit() {
    SmartDashboard.putString("Match Cycle", "DISABLED");
    
    try {
      CrashTracker.logDisabledInit();
      mEnabledLooper.stop();
      if (mAutoModeExecutor != null) {
          mAutoModeExecutor.stop();
      }

      Drive.getInstance().zeroSensors();

      RobotState.getInstance().reset(Timer.getFPGATimestamp(), Pose2d.identity());

      mAutoModeExecutor = new AutoModeExecutor();

      mDisabledLooper.start();
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
  }

  @Override
  public void disabledPeriodic() {
      SmartDashboard.putString("Match Cycle", "DISABLED");

      try {
          outputToSmartDashboard();

          // Poll FMS auto mode info and update mode creator cache

          Optional<AutoModeBase> autoMode = Optional.of(new TestMode());

          if (autoMode.isPresent() && autoMode.get() != mAutoModeExecutor.getAutoMode()) {
                  System.out.println("Set auto mode to: " + autoMode.get().getClass().toString());
                  mAutoModeExecutor.setAutoMode(autoMode.get());
              }
              System.gc();
      } catch (Throwable t) {
          CrashTracker.logThrowableCrash(t);
          throw t;
      }
  }

  @Override
  public void autonomousInit() {
    SmartDashboard.putString("Match Cycle", "AUTONOMOUS");

    try {
      CrashTracker.logAutoInit();
      mDisabledLooper.stop();

      RobotState.getInstance().reset(Timer.getFPGATimestamp(), Pose2d.identity());

      Drive.getInstance().zeroSensors();

      mAutoModeExecutor.start();
      mEnabledLooper.start();
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
  }

  @Override
  public void autonomousPeriodic() {
      SmartDashboard.putString("Match Cycle", "AUTONOMOUS");

      outputToSmartDashboard();
      try {

      } catch (Throwable t) {
          CrashTracker.logThrowableCrash(t);
          throw t;
      }
  }

  @Override
  public void teleopInit() {
    SmartDashboard.putString("Match Cycle", "TELEOP");
    try {
      CrashTracker.logTeleopInit();
      mDisabledLooper.stop();
      if (mAutoModeExecutor != null) {
          mAutoModeExecutor.stop();
      }


      RobotState.getInstance().reset(Timer.getFPGATimestamp(), Pose2d.identity());
      mEnabledLooper.start();

      mDrive.setVelocity(DriveSignal.NEUTRAL, DriveSignal.NEUTRAL);
      mDrive.setOpenLoop(new DriveSignal(0.05, 0.05));

    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
  }

  @Override
  public void teleopPeriodic() {
    SmartDashboard.putString("Match Cycle", "TELEOP");
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  public void outputToSmartDashboard() {
    RobotState.getInstance().outputToSmartDashboard();
    Drive.getInstance().outputTelemetry();
    mEnabledLooper.outputToSmartDashboard();
  }

}

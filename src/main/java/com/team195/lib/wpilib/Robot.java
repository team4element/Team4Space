package com.team195.lib.wpilib;

import java.util.ArrayList;

import com.team195.lib.reporters.ConsoleReporter;
import com.team195.lib.reporters.MessageLevel;
import com.team195.lib.util.DriveMotorValues;
import com.team195.lib.util.ThreadRateControl;
import com.team195.lib.wpilib.RobbieRobot;
import com.team4.robot.auto.framework.AutoModeBase;
import com.team4.robot.auto.framework.AutoModeExecuter;
import com.team4.robot.auto.modes.SplinePathMode;
import com.team4.robot.auto.modes.StraightPathMode;
import com.team4.robot.constants.Constants;
import com.team4.robot.controllers.Controllers;
import com.team4.robot.controllers.DriveControllers;
import com.team4.robot.loops.Looper;
import com.team4.robot.loops.RobotStateEstimator;
import com.team4.robot.subsystems.Drive;
import com.team4.robot.subsystems.Subsystem;

import edu.wpi.first.wpilibj.Compressor;

public class Robot extends RobbieRobot {
  private Controllers robotControllers;
  private DriveControllers driveControllers;

  private Compressor mCompressor;

  private ArrayList<Subsystem> subsystemVector;
  private Looper mLooper;
  private ThreadRateControl mThreadRateControl;

  private AutoModeExecuter mAutoModeExecuter;
  private AutoModeBase mAutoMode;

  // private Servo mServo;

  private Drive mDrive;
  private RobotStateEstimator mStateEstimator;

  public Robot() {
    ;
  }

  @Override
  public void robotInit() {
    Thread.currentThread().setPriority(Constants.kRobotThreadPriority);

    mCompressor = new Compressor(0);

    mThreadRateControl = new ThreadRateControl();

    // mServo = new Servo(2);

    ConsoleReporter.setReportingLevel(MessageLevel.INFO);
		ConsoleReporter.getInstance().start();
		ConsoleReporter.report("Console Reporter Running!", MessageLevel.INFO);

    robotControllers = Controllers.getInstance();
    driveControllers = DriveControllers.getInstance();
    mLooper = new Looper();
    subsystemVector = new ArrayList<Subsystem>();

    mDrive = Drive.getInstance(subsystemVector);

    mDrive.init();
    

    mThreadRateControl.start(true);

    for(Subsystem subsystem : subsystemVector){
      subsystem.init();
      mThreadRateControl.doRateControl(100);
    }
    for(Subsystem subsystem : subsystemVector){
      subsystem.registerEnabledLoops(mLooper);
    }

    mStateEstimator = RobotStateEstimator.getInstance();
    mLooper.register(mStateEstimator);
  
    ConsoleReporter.report("Robot Init Complete!", MessageLevel.INFO);
  }
  @Override
  public void autonomous() {
    mLooper.start(true);
    mCompressor.stop();
    mDrive.setBrakeMode(true);
    mAutoModeExecuter = new AutoModeExecuter();

    mAutoMode = new SplinePathMode();

    if(mAutoMode != null){
      mAutoModeExecuter.setAutoMode(mAutoMode);
    }else{
      return;
    }

    mAutoModeExecuter.start();
    mThreadRateControl.start(true);
    while (isAutonomous() && isEnabled()) {
      mThreadRateControl.doRateControl(100);
    }
  }

  @Override
  public void operatorControl() {
    exitAuto();
    mCompressor.stop();
    // DigitalOutput mOutput = new DigitalOutput(3);
    
    mLooper.start(false);
    mDrive.setOpenLoop(DriveMotorValues.NEUTRAL);
    mThreadRateControl.start(true);
    while (isOperatorControl() && isEnabled()) {
      // hidController.run();]
      // mServo.set(.5);     
      // mOutput.set(true); 
      // System.out.println(mOutput.get());
      // mOutput.close();
      // mServo.setAngle(90);
			mThreadRateControl.doRateControl(20);
		}
  }

  @Override
  protected void disabled() {
    exitAuto();

		mLooper.stop();
    
		mThreadRateControl.start(true);
    mDrive.setBrakeMode(false);

		while (isDisabled()) {
			mThreadRateControl.doRateControl(100);
		}

  }

  @Override
  public void test() {
  }

  private void exitAuto() {
		try {
			if (mAutoModeExecuter != null)
				mAutoModeExecuter.stop();

			mAutoModeExecuter = null;
		} catch (Throwable t) {
			ConsoleReporter.report(t, MessageLevel.ERROR);
		}
	}


}

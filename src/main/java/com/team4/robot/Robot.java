package com.team4.robot;

import java.util.ArrayList;

import com.team195.lib.reporters.ConsoleReporter;
import com.team195.lib.reporters.MessageLevel;
import com.team195.lib.util.DriveMotorValues;
import com.team195.lib.util.ThreadRateControl;
import com.team254.lib.wpilib.TimedRobot;
import com.team4.robot.auto.framework.AutoModeBase;
import com.team4.robot.auto.framework.AutoModeExecuter;
import com.team4.robot.auto.modes.DriveByCameraMode;
import com.team4.robot.auto.modes.LeftCargoShipMode;
import com.team4.robot.constants.Constants;
import com.team4.robot.controllers.Controllers;
import com.team4.robot.controllers.DriveControllers;
import com.team4.robot.loops.Looper;
import com.team4.robot.loops.RobotStateEstimator;
import com.team4.robot.subsystems.Arm;
import com.team4.robot.subsystems.Drive;
import com.team4.robot.subsystems.Intake;
import com.team4.robot.subsystems.Subsystem;
import com.team4.robot.subsystems.VisionTracker;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Servo;

public class Robot extends TimedRobot{
    private Controllers robotControllers;
    private DriveControllers driveControllers;
  
    private Compressor mCompressor;
  
    private ArrayList<Subsystem> subsystemVector;
    private Looper mLooper;
    private ThreadRateControl mThreadRateControl;
  
    private AutoModeExecuter mAutoModeExecuter;
    private AutoModeBase mAutoMode;
    private AutoModeSelector mAutoModeSelector;

    private HIDController mHidController;
    private Servo mLeftServo;
    private Servo mRightServo;

    private Drive mDrive;
    private VisionTracker mVisionTracker;
    private Arm mArm;
    private Intake mIntake;
    private RobotStateEstimator mStateEstimator;
  
    private boolean mDriveByCameraInAuto = true;

    public Robot() {
      ;
    }
    @Override
    public void robotInit() {
        Thread.currentThread().setPriority(Constants.kRobotThreadPriority);

        //init camera stream
        UsbCamera frontCamera = CameraServer.getInstance().startAutomaticCapture();
        frontCamera.setVideoMode(VideoMode.PixelFormat.kMJPEG, 160, 120   , 15);
        MjpegServer cameraServer = new MjpegServer("Front Camera", 0);
        cameraServer.setSource(frontCamera);

        mCompressor = new Compressor(0);

        mThreadRateControl = new ThreadRateControl();

        mHidController = HIDController.getInstance();
    // mServo = new Servo(2);

        mAutoModeSelector = new AutoModeSelector();

        ConsoleReporter.setReportingLevel(MessageLevel.INFO);
		ConsoleReporter.getInstance().start();
		ConsoleReporter.report("Console Reporter Running!", MessageLevel.INFO);

        robotControllers = Controllers.getInstance();
        driveControllers = DriveControllers.getInstance();
        mLooper = new Looper();
        subsystemVector = new ArrayList<Subsystem>();

        mVisionTracker = VisionTracker.getInstance(subsystemVector);
        mDrive = Drive.getInstance(subsystemVector);
        mArm = Arm.getInstance(subsystemVector);
        mIntake = Intake.getInstance(subsystemVector);
    

        mLeftServo = mHidController.getLeftServo();
        mRightServo = mHidController.getRightServo();

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
    public void robotPeriodic() {

        mArm.output();
        mVisionTracker.readPeriodicInputs();
        mVisionTracker.writePeriodicOutputs();
    }
    @Override
    public void autonomousInit() {
        mLooper.start(true);
        mCompressor.stop();
        mDrive.setBrakeMode(true);
        mAutoModeExecuter = new AutoModeExecuter();
    
        mAutoModeSelector.updateModeCreator();
        // mAutoMode = mAutoModeSelector.getAutoMode();
        mAutoMode = new DriveByCameraMode();
    
        if(mAutoMode != null){
          mAutoModeExecuter.setAutoMode(mAutoMode);
        }else{
          return;
        }
    
        mAutoModeExecuter.start();
        mThreadRateControl.start(true);    
    }
    @Override
    public void autonomousPeriodic() {
        if(mDriveByCameraInAuto){
            mHidController.run();
        }

        if(mArm.getPot() < 1){
            mLeftServo.set(160);
            mRightServo.set(10);
        }
        if(mArm.getPot() > 6){
            mLeftServo.set(0);
            mRightServo.set(170);
        }

        mThreadRateControl.doRateControl(100);
    }
    @Override
    public void teleopInit() {
        exitAuto();
    mCompressor.stop();
    // DigitalOutput mOutput = new DigitalOutput(3);
    
    mLooper.start(false);
    mDrive.setOpenLoop(DriveMotorValues.NEUTRAL);
    mThreadRateControl.start(true);
    }
    @Override
    public void teleopPeriodic() {
        mHidController.run();

        

        mVisionTracker.readPeriodicInputs();
        mVisionTracker.writePeriodicOutputs();

        mThreadRateControl.doRateControl(20);
    }
    @Override
    public void disabledInit() {
        exitAuto();

		mLooper.stop();
    
		mThreadRateControl.start(true);
        mDrive.setBrakeMode(false);

    mThreadRateControl.start(true);
    }
    @Override
    public void disabledPeriodic() {
        mAutoModeSelector.updateModeCreator();
        
        
        mAutoMode = mAutoModeSelector.getAutoMode();
        mAutoModeExecuter = new AutoModeExecuter();

        // mDriveByCameraInAuto = mAutoModeSelector.isDriveByCamera();
        mDriveByCameraInAuto = true;
        if(mAutoMode != mAutoModeSelector.getAutoMode()){
            System.out.println("Set auto mode to: " + mAutoMode.getClass().toString());
        }
        mAutoModeExecuter.setAutoMode(mAutoMode);
        mThreadRateControl.doRateControl(100);
    }

    @Override
    public void testInit() {
    }
    @Override
    public void testPeriodic() {
        
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
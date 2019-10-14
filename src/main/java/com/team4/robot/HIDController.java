package com.team4.robot;

import com.team195.lib.motion.Util;
import com.team195.lib.reporters.ConsoleReporter;
import com.team195.lib.util.DriveMotorValues;
import com.team254.lib.util.CheesyDriveHelper;
import com.team4.lib.util.ElementMath;
import com.team4.robot.constants.ArmConstants;
import com.team4.robot.constants.Constants;
import com.team4.robot.controlboard.ControlBoard;
import com.team4.robot.controlstate.IntakeState;
import com.team4.robot.subsystems.Arm;
import com.team4.robot.subsystems.Drive;
import com.team4.robot.subsystems.Intake;
import com.team4.robot.subsystems.VisionTracker;
import com.team4.robot.subsystems.VisionTracker.TargetMode;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Add your docs here.
 */
public class HIDController implements Runnable{
    private static HIDController instance = null;

    private boolean visionEnable = false;
    private boolean pistonOut = false;

    private double mArmSetpoint;

    private Drive m_drivetrain;
    private Intake m_intake;
    private Arm m_arm;
    // private DashJoy operatorController;
    
    private Servo mLeftServo, mRightServo;

    private ControlBoard mControlBoard;

    Compressor mCompressor;
    
    private Solenoid leftRamp, rightRamp;
    
    private CheesyDriveHelper mCheesyDriveHelper = new CheesyDriveHelper();
    
    // private boolean USE_CHEESY_DRIVE = Robot.USE_CHEZY_DRIVE;
    private boolean USE_CHEESY_DRIVE = true;

    private HIDController() throws Exception{
        m_drivetrain = Drive.getInstance();
        m_arm = Arm.getInstance();
        m_intake = Intake.getInstance();
        // driveController = new DashJoy(Constants.DRIVE_CONTROLLER);
        // operatorController = new DashJoy(Constants.OPERATOR_CONTROLLER);
        
        mControlBoard = ControlBoard.getInstance();

        mCompressor = new Compressor(0);

        mRightServo = new Servo(2);
        mLeftServo = new Servo(1);

        mArmSetpoint = ArmConstants.shootSetpoint;

        // mSetpointDeadband = .01;

        leftRamp = new Solenoid(Constants.kLeftRampSolenoid);
        leftRamp.setPulseDuration(1);
        
        rightRamp = new Solenoid(Constants.kRightRampSolenoid);
        rightRamp.setPulseDuration(1);
    }

    public static HIDController getInstance(){
        if(instance == null){
           try {
            instance = new HIDController();   
           } catch (Exception e) {
            ConsoleReporter.report(e);
        }
        }
        return instance;
    }
    
    @Override
    public void run() {

        if(mControlBoard.toggleCompressor()){
            mCompressor.start();
        }
        double armPow = -ElementMath.handleDeadband(mControlBoard.getArmThrottle(), Constants.kJoystickDeadband);

        // m_arm.setArmOpenLoop(armPow*.5);
        if(mControlBoard.getAutoArm()){
            m_arm.setPosition(mArmSetpoint);
        // }else if(operatorController.getRawButton(Constants.BUTTON_A) && (m_arm.getPot() <= mArmSetpoint + mSetpointDeadband || m_arm.getPot() >= mArmSetpoint - mSetpointDeadband)) {
            // m_intake.setIntakeState(IntakeState.OUTTAKE);
        }else if (!mControlBoard.getAutoArm()){
            m_arm.setArmOpenLoop(armPow);
        }

        if(mControlBoard.getServoForward()){
            mRightServo.setAngle(90);
            mLeftServo.setAngle(90);
        }

        if(mControlBoard.getServoUp()){
            mRightServo.setAngle(170);
            mLeftServo.setAngle(0);
        }
        if(mControlBoard.getServoDown()){
            mRightServo.setAngle(10);
            mLeftServo.setAngle(160);
        }

        if (mControlBoard.getIntake()){
            m_intake.setIntakeState(IntakeState.INTAKE);
        }else if(mControlBoard.getOuttke()){
            m_intake.setIntakeState(IntakeState.OUTTAKE);
        }else{
            m_intake.setIntakeState(IntakeState.IDLE);
        }

        if(mControlBoard.getVisionEnable()){
            visionEnable = true;
        }else{
            visionEnable = false;
        }
        if(!USE_CHEESY_DRIVE){
            double turn = ElementMath.handleDeadband(ElementMath.squareInput(mControlBoard.getTurn()), Constants.kJoystickDeadband) * .75;
            double throttle = ElementMath.handleDeadband(ElementMath.squareInput(mControlBoard.getThrottle()), Constants.kJoystickDeadband);

            m_drivetrain.setBrakeMode(false);

            m_drivetrain.setOpenLoop(new DriveMotorValues(Util.limit(turn + throttle, 1), Util.limit(turn - throttle, 1)));
        }else{

            double turn = ElementMath.handleDeadband(ElementMath.squareInput(mControlBoard.getTurn()), Constants.kJoystickDeadband) * .75;
            double throttle = -ElementMath.handleDeadband(ElementMath.squareInput(mControlBoard.getThrottle()), Constants.kJoystickDeadband);

            m_drivetrain.setBrakeMode(false);
    
            VisionTracker.getInstance().setVisionEnabled(visionEnable);

            if(VisionTracker.getInstance().isVisionEnabled() && VisionTracker.getInstance().getTargetMode() == TargetMode.CARGO_BALL){
            // if (VisionTracker.getInstance().isTargetFound()){
//						turn = Math.max(Math.min(VisionTracker.getInstance().getTargetHorizAngleDev() * 0.007, 0.1), -0.1);
                        turn = Math.max(Math.min(VisionTracker.getInstance().getTargetHorizAngleDev() * 0.01, 0.1), -0.1);
            //   }
            }
            m_drivetrain.setOpenLoop(mCheesyDriveHelper.cheesyDrive(throttle, turn, true, false));
        
        
        }
        if(mControlBoard.getPulseRamp()){
            leftRamp.set(!pistonOut);
            rightRamp.set(!pistonOut);
        }

    }

    public Servo getLeftServo(){
        return mLeftServo;
    }
    public Servo getRightServo(){
        return mRightServo;
    }

}


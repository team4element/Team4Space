package com.team4.robot.actions;

import com.team4.robot.actions.framework.Action;
import com.team4.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.Timer;

// import com.team4.robot.actions.Action;

public class ArmPositionAction implements Action {
    private static final Arm mArm = Arm.getInstance();


    private final double mArmSetpoint, mDuration;

    private double mStartTime;

    public ArmPositionAction(double armSetpoint, double duration){
        mArmSetpoint = armSetpoint;
        mDuration = duration;
    }

    @Override
    public void start() {
        mArm.setPosition(mArmSetpoint);
        mStartTime = Timer.getFPGATimestamp();
    }
    @Override
    public boolean isFinished() {
        // return mArm.getPot() >= (mArmSetpoint - mSetpointDeadband) || mArm.getPot() <= (mArmSetpoint + mSetpointDeadband);
        return Timer.getFPGATimestamp() - mStartTime > mDuration; 
    }

    @Override
    public void update() {
        
    }

    @Override
    public void done() {
        
    }
}

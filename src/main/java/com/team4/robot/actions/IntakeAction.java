package com.team4.robot.actions;

import com.team4.robot.actions.framework.Action;
import com.team4.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.Timer;

public class IntakeAction implements Action {
    private static final Intake mIntake = Intake.getInstance();
    
    private double mStartTime;
    private double mDuration;
    
    public IntakeAction(double duration){
        mDuration = duration;
    }
    
    @Override
    public void start() {
        mIntake.setIntake();
        
        mStartTime = Timer.getFPGATimestamp();
    }

    @Override
    public void update() {
        
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - mStartTime > mDuration;
    }
    
    @Override
    public void done() {
    
    }
}
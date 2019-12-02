package com.team4.robot.auto.actions;

import com.team254.lib.util.DriveSignal;
import com.team4.robot.subsystems.Drive;

public class DriveVelocityAction implements Action{
    
    private final Drive mDrive = Drive.getInstance();

    private double mSetpoint;
    private boolean isFinished;

    public DriveVelocityAction(double setpoint){
        mSetpoint = setpoint;
    }

    @Override
    public void start() {
        if(mSetpoint > mDrive.getLinearVelocity()){
            mDrive.setVelocityIPS(new DriveSignal(mSetpoint, mSetpoint), new DriveSignal(0, 0));
        }
    }

    @Override
    public void update() {
        isFinished = mSetpoint <= mDrive.getLinearVelocity();
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void done() {
        mDrive.setOpenLoop(new DriveSignal(0, 0));
    }
}
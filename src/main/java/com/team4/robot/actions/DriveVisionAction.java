package com.team4.robot.actions;

import com.team195.lib.util.DriveMotorValues;
import com.team4.robot.actions.framework.Action;
import com.team4.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.Timer;

public class DriveVisionAction implements Action{
    private static final Drive mDrive = Drive.getInstance();

    private double mStartTime;
    private final double mDuration, mThrottle;

    public DriveVisionAction(double throttle, double duration) {
        mDuration = duration;
        mThrottle = throttle;
    }

    @Override
    public void start() {
        mDrive.setVisionMode(mThrottle);
        mStartTime = Timer.getFPGATimestamp();
    }

    @Override
    public void update() {}

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - mStartTime > mDuration;
    }

    @Override
    public void done() {
        mDrive.setOpenLoop(new DriveMotorValues(0.0, 0.0));
    }
}
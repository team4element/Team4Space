package com.team4.robot.controlboard;

public interface IOperatorControlBoard{
    double getArmThrottle();

    boolean getIntake();

    boolean getOuttke();

    boolean getIntakeIdle();

    boolean getAutoArm();

    boolean toggleCompressor();

    boolean getServoUp();

    boolean getServoForward();

    boolean getServoDown();
}
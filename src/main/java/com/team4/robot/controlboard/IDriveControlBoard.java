package com.team4.robot.controlboard;

public interface IDriveControlBoard {
    double getThrottle();

    double getTurn();

    boolean getQuickTurn();

    boolean getShoot();

    boolean getVisionEnable();

    boolean getThrust();

    boolean getPulseRamp();

    boolean stopAuto();
}
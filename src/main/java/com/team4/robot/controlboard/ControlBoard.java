package com.team4.robot.controlboard;

public class ControlBoard implements IControlBoard{
    private static ControlBoard instance = null;

    public static ControlBoard getInstance(){
        if(instance == null){
            instance = new ControlBoard();
        }
        return instance;
    }

    private final IDriveControlBoard mDriveControlBoard;
    private final IOperatorControlBoard mOperatorControlBoard;

    private ControlBoard(){
        mDriveControlBoard = GamepadDriveControlBoard.getInstance();
        mOperatorControlBoard = GamepadOperatorControlBoard.getInstance();
    }

    @Override
    public double getArmThrottle() {
        return mOperatorControlBoard.getArmThrottle();
    }

    @Override
    public boolean getAutoArm() {
        return mOperatorControlBoard.getAutoArm();
    }

    @Override
    public boolean getIntake() {
        return mOperatorControlBoard.getIntake();
    }

    @Override
    public boolean getOuttke() {
        return mOperatorControlBoard.getOuttke();
    }

    @Override
    public boolean getIntakeIdle() {
        return mOperatorControlBoard.getIntakeIdle();
    }

    @Override
    public boolean getPulseRamp() {
        return mDriveControlBoard.getPulseRamp();
    }

    @Override
    public boolean getQuickTurn() {
        return mDriveControlBoard.getQuickTurn();
    }

    @Override
    public boolean getShoot() {
        return mDriveControlBoard.getShoot();
    }

    @Override
    public double getThrottle() {
        return mDriveControlBoard.getThrottle();
    }

    @Override
    public boolean getThrust() {
        return mDriveControlBoard.getThrust();
    }

    @Override
    public double getTurn() {
        return mDriveControlBoard.getTurn();
    }

    @Override
    public boolean getVisionEnable() {
        return mDriveControlBoard.getVisionEnable();
    }

    @Override
    public boolean toggleCompressor() {
        return mOperatorControlBoard.toggleCompressor();
    }

    @Override
    public boolean stopAuto() {
        return mDriveControlBoard.stopAuto();
    }

    @Override
    public boolean getServoForward() {
        return mOperatorControlBoard.getServoForward();
    }
    @Override
    public boolean getServoUp() {
        return mOperatorControlBoard.getServoUp();
    }
    @Override
    public boolean getServoDown() {
        return mOperatorControlBoard.getServoDown();
    }

}
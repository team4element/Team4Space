package com.team4.robot.controlboard;

import com.team4.robot.constants.Constants;
import com.team4.robot.controlboard.XboxController.Axis;
import com.team4.robot.controlboard.XboxController.Button;

public class GamepadOperatorControlBoard implements IOperatorControlBoard{
    private static GamepadOperatorControlBoard instance = null;

    public static GamepadOperatorControlBoard getInstance(){
        if (instance == null){
            instance = new GamepadOperatorControlBoard();
        }
        return instance;
    }

    private final XboxController mController;

    private GamepadOperatorControlBoard(){
        mController = new XboxController(Constants.OPERATOR_CONTROLLER);
    }
    
    @Override
    public double getArmThrottle() {
        return mController.getJoystick(XboxController.Side.LEFT, Axis.Y);
    }

    @Override
    public boolean getAutoArm() {
        return mController.getButton(Button.A);
    }

    @Override
    public boolean getIntake() {
        return mController.getButton(Button.RB);
    }
    @Override
    public boolean getOuttke() {
        return mController.getButton(Button.LB);
    }
    @Override
    public boolean getIntakeIdle() {
        return !getOuttke() && !getIntake();
    }

    @Override
    public boolean toggleCompressor() {
        return mController.getButton(Button.START);
    }
    @Override
    public boolean getServoForward() {
        return mController.getButton(Button.X);
    }
    @Override
    public boolean getServoUp() {
        return mController.getButton(Button.B);
    }

    @Override
    public boolean getServoDown() {
        return mController.getButton(Button.Y);
    }
}
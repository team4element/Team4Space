
package com.team4.lib.util;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BooleanChooser {

    enum BooleanState {
        TRUE,
        FALSE
    };

    boolean mBool;

    BooleanState mCachedState = null;

    String mBooleanName;

    SendableChooser<BooleanState> mBooleanChooser;

    public BooleanChooser(String booleanName, boolean bool){
        mBool = bool;
        mBooleanName = booleanName;

        mBooleanChooser = new SendableChooser<>();
        mBooleanChooser.setDefaultOption("True", BooleanState.TRUE);
        mBooleanChooser.addOption("False", BooleanState.FALSE);
        SmartDashboard.putData(mBooleanName, mBooleanChooser);
    }

    public void updateSelectedValue(){
        BooleanState boolState = mBooleanChooser.getSelected();
        if (mCachedState != boolState) {
            System.out.println("Set Boolean " + mBooleanName + " to: " + boolState.name());
            setBoolean(boolState);
        }
        mCachedState = boolState;
    }
    public void setBoolean(BooleanState state){
        switch(state){
            case TRUE:
                mBool = true;
                break;
            case FALSE:
                mBool = false;
                break;
            default:
                break;

        }
    }

    public boolean getBoolean(){
        return mBool;
    }
}
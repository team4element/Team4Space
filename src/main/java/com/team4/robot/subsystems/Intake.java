package com.team4.robot.subsystems;

import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.team4.robot.constants.IntakeConstants;
import com.team4.robot.controlstate.IntakeState;
import com.team4.robot.loops.Loop;
import com.team4.robot.loops.Looper;

public class Intake implements Subsystem {
    private static Intake instance = null;

    private IntakeState mIntakeState;

    private VictorSPX mLeftMotor, mRightMotor;

    public static Intake getInstance(){
        if(instance == null){
            instance = new Intake();
        }
        return instance;
    }

    private Intake(){
        if(mIntakeState != null){
            mIntakeState = IntakeState.IDLE;
        }

        mLeftMotor = new VictorSPX(IntakeConstants.INTAKE_LEFT_ID);
        mRightMotor = new VictorSPX(IntakeConstants.INTAKE_RIGHT_ID);

    }
    public static Intake getInstance(List<Subsystem> subsystemList) {
		subsystemList.add(getInstance());
		return instance;
	}

    public void setIntakeState(IntakeState state){
        mIntakeState = state;
    }
    
    @Override
    public void registerEnabledLoops(Looper enabledLooper) {
        Loop loop = new Loop() {

            @Override
            public void onFirstStart(double timestamp) {
                
            }

            @Override
            public void onStart(double timestamp) {
                setIdle();

            }

            @Override
            public void onLoop(double timestamp, boolean isAuto) {

                synchronized (Intake.this) {
                    switch(mIntakeState){
                    case INTAKE:
                        setIntake();
                        break;
                    case OUTTAKE:
                        setOutake();
                        break;
                    case IDLE:
                        setIdle();
                        break;
                    default:
                        setIdle();
                        break;
                    }
                }

            }

            @Override
            public void onStop(double timestamp) {
            }
        };
        enabledLooper.register(loop);
    }
    public void setOpenLoop(double left, double right){
        mLeftMotor.set(ControlMode.PercentOutput, left);
        mRightMotor.set(ControlMode.PercentOutput, right);
    }  

    public void setIntake(){
    if(mIntakeState != IntakeState.INTAKE){
        mIntakeState = IntakeState.INTAKE;
    }
        setOpenLoop(-.3, -.3);
    }
    
    public void setOutake(){
        if(mIntakeState != IntakeState.OUTTAKE){
            mIntakeState = IntakeState.OUTTAKE;
        }
            setOpenLoop(.55, .55);
        }
        
    public void setIdle(){
        if(mIntakeState != IntakeState.IDLE){
            mIntakeState = IntakeState.IDLE;
        }
            setOpenLoop(0, 0);
        }

    public IntakeState getIntakeState(){
        return mIntakeState;
    }

    @Override
    public void init() {
        
    }

    @Override
    public void subsystemHome() {
        
    }

}
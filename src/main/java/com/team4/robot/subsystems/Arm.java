package com.team4.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.team195.lib.drivers.CANSpeedControllerBuilder;
import com.team4.lib.util.SynchronusPID;
import com.team4.robot.constants.ArmConstants;
import com.team4.robot.constants.AutoConstants;
import com.team4.robot.controlstate.ArmState;
import com.team4.robot.loops.Loop;
import com.team4.robot.loops.Looper;

import java.util.List;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm implements Subsystem {
    private static Arm instance = null;

    TalonSRX mMasterMotor;
    VictorSPX mSlaveMotor;

    Potentiometer mPot;

    SynchronusPID mPID;

    private double mSetpoint;

    Loop mLoop = new Loop(){
        @Override
        public void onFirstStart(double timestamp){

        }
        
        @Override
        public void onStart(double timestamp){

        }
        @Override
        public void onLoop(double timestamp, boolean isAuto){
            switch (mArmState){
                case OPEN_LOOP:
                break;
                
                case POSITION:
                double armPow = mPID.calculate(getPot());
                setPositionPower(armPow);
                break;

                default:
                break;
            }
        }
        @Override
        public void onStop(double timestamp){
            stop();
        }

    };

    private ArmState mArmState;

    public static Arm getInstance(){
        if(instance == null){
            instance = new Arm();
        }
        return instance;
    }

    private Arm(){
        mArmState = ArmState.OPEN_LOOP;
        
        mMasterMotor = CANSpeedControllerBuilder.createDefaultTalonSRX(ArmConstants.MASTER_TALON_ID);
        mSlaveMotor = CANSpeedControllerBuilder.createPermanentVictorSlaveToTalonSRX(ArmConstants.SLAVE_VICTOR_ID, ArmConstants.SLAVE_VICTOR_CHANNEL, mMasterMotor);
        mSlaveMotor.setInverted(true);

        mMasterMotor.setNeutralMode(NeutralMode.Brake);
        mSlaveMotor.setNeutralMode(NeutralMode.Brake);

    }


	public static Arm getInstance(List<Subsystem> subsystemList) {
		subsystemList.add(getInstance());
		return instance;
	}

    @Override
    public void init() {
        mPot = new AnalogPotentiometer(0);

        mPID = new SynchronusPID(AutoConstants.kArmP, AutoConstants.kArmI, AutoConstants.kArmD, false);        
    }

    @Override
    public void registerEnabledLoops(Looper enabledLooper) {
        enabledLooper.register(mLoop);
    }

    public void stop() {
        
    }
    @Override
    public void subsystemHome() {
        
    }

    public void setArmOpenLoop(double power){
        mArmState = ArmState.OPEN_LOOP;
        mMasterMotor.set(ControlMode.PercentOutput, power);
    }
    public void setPositionPower(double power){
        mArmState = ArmState.POSITION;
        mMasterMotor.set(ControlMode.PercentOutput, power);
    }

    public void setPosition(double setpoint){
        mArmState = ArmState.POSITION;
        setSetpoint(setpoint);
    }

    public void setSetpoint(double setpoint){
        mSetpoint = setpoint;
        mPID.setSetpoint(mSetpoint);
    }

    public double getPot(){
        return mPot.get() * 10;
    }
    public double getSetpoint(){
        return mSetpoint;
    }

    public void output(){
        SmartDashboard.putNumber("Arm Angle", getPot());
    }

}

package com.team4.robot.controllers;

import com.team195.lib.drivers.CANSpeedControllerBuilder;
import com.team195.lib.drivers.CKTalonSRX;
import com.team195.lib.drivers.CKVictorSPX;
import com.team195.lib.drivers.NavX;
import com.team195.lib.reporters.ConsoleReporter;
import com.team195.lib.reporters.MessageLevel;
import com.team4.robot.constants.DriveConstants;

import edu.wpi.first.wpilibj.SPI;

public class DriveControllers{
    private static DriveControllers instance = null;

	private CKTalonSRX leftMaster;
	private CKVictorSPX leftSlaveA;
	private CKVictorSPX leftSlaveB;
	private CKTalonSRX rightMaster;
	private CKVictorSPX rightSlaveA;
	private CKVictorSPX rightSlaveB;

    private NavX navX;

    private DriveControllers(){
		//Left Drive Setup
		leftMaster = CANSpeedControllerBuilder.createFastMasterTalonSRX(DriveConstants.kLeftDriveMasterId, DriveConstants.kLeftDriveMasterChannel);
		leftSlaveA = CANSpeedControllerBuilder.createPermanentVictorSlaveToTalonSRX(DriveConstants.kLeftDriveSlaveAId, DriveConstants.kLeftDriveSlaveAChannel, leftMaster);
		leftSlaveB = CANSpeedControllerBuilder.createPermanentVictorSlaveToTalonSRX(DriveConstants.kLeftDriveSlaveBId, DriveConstants.kLeftDriveSlaveBChannel, leftMaster);

		//Right Drive Setup
		rightMaster = CANSpeedControllerBuilder.createFastMasterTalonSRX(DriveConstants.kRightDriveMasterId, DriveConstants.kRightDriveMasterChannel);
		rightSlaveA = CANSpeedControllerBuilder.createPermanentVictorSlaveToTalonSRX(DriveConstants.kRightDriveSlaveAId, DriveConstants.kRightDriveSlaveAChannel, rightMaster);
		rightSlaveB = CANSpeedControllerBuilder.createPermanentVictorSlaveToTalonSRX(DriveConstants.kRightDriveSlaveBId, DriveConstants.kRightDriveSlaveBChannel, rightMaster);

        //Gyro Setup
        try {
	        navX = new NavX(SPI.Port.kMXP);
	    } catch (Exception ex) {
			ConsoleReporter.report(ex, MessageLevel.DEFCON1);
        }
    }

    public static DriveControllers getInstance(){
        if(instance == null){
            instance = new DriveControllers();
        }
        return instance;
    }


	public CKTalonSRX getLeftMaster() {
		return leftMaster;
	}
	
	public CKVictorSPX getLeftSlaveA() {
		return leftSlaveA;
	}
	
	public CKVictorSPX getLeftSlaveB() {
		return leftSlaveB;
	}
	
	public CKTalonSRX getRightMaster() {
		return rightMaster;
	}
	
	public CKVictorSPX getRightSlaveA() {
		return rightSlaveA;
	}
	
	public CKVictorSPX getRightSlaveB() {
		return rightSlaveB;
    }
    
    public NavX getNavX(){
        return navX;
    }



}
package com.team195.lib.drivers;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.team4.robot.controllers.Controllers;

public class CKVictorSPX extends VictorSPX {
	int pdpChannel;

	public CKVictorSPX(int deviceId, int pdpChannel) {
		super(deviceId);
		this.pdpChannel = pdpChannel;
	}

	@Override
	public double getOutputCurrent() {
		return Controllers.getInstance().getPowerDistributionPanel().getCurrent(pdpChannel);
	}
}

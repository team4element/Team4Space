package com.team4.robot.controllers;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Controllers {
    private static Controllers instance = null;
    
    private Compressor compressor;
    private PowerDistributionPanel powerDistributionPanel;
    
    public Controllers() {
		compressor = new Compressor();
		powerDistributionPanel = new PowerDistributionPanel();
    }

    public static Controllers getInstance() {
		if(instance == null)
			instance = new Controllers();
		
		return instance;
	}

    public PowerDistributionPanel getPowerDistributionPanel() {
		return powerDistributionPanel;
	}

	public Compressor getCompressor() {
		return compressor;
	}

}
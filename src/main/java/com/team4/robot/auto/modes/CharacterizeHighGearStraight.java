package com.team4.robot.auto.modes;

import com.team4.robot.auto.AutoModeBase;
import com.team4.robot.auto.AutoModeEndedException;
import com.team4.robot.auto.actions.CollectAccelerationData;
import com.team4.robot.auto.actions.CollectVelocityData;
import com.team4.robot.auto.actions.WaitAction;
import com.team254.lib.physics.DriveCharacterization;

import java.util.ArrayList;
import java.util.List;

public class CharacterizeHighGearStraight extends AutoModeBase {
    @Override
    protected void routine() throws AutoModeEndedException {
        List<DriveCharacterization.VelocityDataPoint> velocityData = new ArrayList<>();
        List<DriveCharacterization.AccelerationDataPoint> accelerationData = new ArrayList<>();

        // runAction(new ShiftHighGearAction(false));
        // runAction(new WaitAction(10));

        runAction(new CollectVelocityData(velocityData, false, true));
        runAction(new WaitAction(10));
        runAction(new CollectAccelerationData(accelerationData, false, true));

        DriveCharacterization.CharacterizationConstants constants = DriveCharacterization.characterizeDrive(velocityData, accelerationData);

        System.out.println("ks: " + constants.ks);
        System.out.println("kv: " + constants.kv);
        System.out.println("ka: " + constants.ka);
    }
}
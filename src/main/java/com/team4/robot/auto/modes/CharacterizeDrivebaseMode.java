package com.team4.robot.auto.modes;

import java.util.ArrayList;
import java.util.List;

import com.team254.lib.physics.DriveCharacterization;
import com.team4.robot.auto.AutoModeEndedException;
import com.team4.robot.auto.actions.CollectAccelerationDataAction;
import com.team4.robot.auto.actions.CollectVelocityDataAction;
import com.team4.robot.auto.actions.WaitAction;

public class CharacterizeDrivebaseMode extends AutoModeBase {
    private final boolean highGear;
    private final boolean reverse;
    private final boolean turn;

    public CharacterizeDrivebaseMode(boolean highGear, boolean reverse, boolean turn) {
        this.highGear = highGear;
        this.reverse = reverse;
        this.turn = turn;
    }

    @Override
    protected void routine() throws AutoModeEndedException {
        List<DriveCharacterization.DataPoint> velocityData = new ArrayList<>();
        List<DriveCharacterization.DataPoint> accelerationData = new ArrayList<>();

        runAction(new CollectVelocityDataAction(velocityData, reverse, turn));
        runAction(new WaitAction(10));
        runAction(new CollectAccelerationDataAction(accelerationData, reverse, turn));

        DriveCharacterization.CharacterizationConstants constants = DriveCharacterization.characterizeDrive(velocityData, accelerationData);

        System.out.println("ks: " + constants.ks);
        System.out.println("kv: " + constants.kv);
        System.out.println("ka: " + constants.ka);
    }
}
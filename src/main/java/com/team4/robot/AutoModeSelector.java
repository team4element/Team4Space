package com.team4.robot;

import com.team4.robot.auto.framework.AutoModeBase;
import com.team4.robot.auto.modes.DoNothingMode;
import com.team4.robot.auto.modes.DriveByCameraMode;
import com.team4.robot.auto.modes.LeftCargoShipMode;
import com.team4.robot.auto.modes.RightCargoShipMode;
import com.team4.robot.auto.modes.SplinePathMode;
import com.team4.robot.auto.modes.StraightPathMode;
import com.team4.robot.auto.modes.Turn90DegMode;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoModeSelector {

    enum DesiredMode {
        DRIVE_BY_CAMERA,
        DO_NOTHING,
        CARGO_SHIP_LEFT_HAB1,
        CARGO_SHIP_LEFT_HAB2,
        CARGO_SHIP_RIGHT_HAB1,
        CARGO_SHIP_RIGHT_HAB2,
        STRAIGHT_PATH,
        SPLINE_PATH,
        TURN_90_DEG
    }

    private DesiredMode mCachedDesiredMode = null;

    private SendableChooser<DesiredMode> mModeChooser;

    private AutoModeBase mAutoMode = null;

    public AutoModeSelector() {
        mModeChooser = new SendableChooser<>();
        mModeChooser.setDefaultOption("Cargo Ship Left - Hab 1", DesiredMode.CARGO_SHIP_LEFT_HAB1);
        mModeChooser.addOption("Carho Ship Left - Hab 2 ", DesiredMode.CARGO_SHIP_LEFT_HAB2);
        mModeChooser.addOption("Cargo Ship Right - Hab 1", DesiredMode.CARGO_SHIP_RIGHT_HAB1);
        mModeChooser.addOption("Cargo Ship Right - Hab 2", DesiredMode.CARGO_SHIP_RIGHT_HAB2);
        mModeChooser.addOption("Do Nothing", DesiredMode.DO_NOTHING);
        mModeChooser.addOption("Drive By Camera", DesiredMode.DRIVE_BY_CAMERA);
        mModeChooser.addOption("Drive Straight", DesiredMode.STRAIGHT_PATH);
        mModeChooser.addOption("Drive Spline", DesiredMode.SPLINE_PATH);
        mModeChooser.addOption("Turn 90 Degrees", DesiredMode.TURN_90_DEG);
        SmartDashboard.putData("Auto mode", mModeChooser);
    }

    public void updateModeCreator() {
        DesiredMode desiredMode = mModeChooser.getSelected();
        if (mCachedDesiredMode != desiredMode) {
            System.out.println("Auto selection changed, updating creator: desiredMode->" + desiredMode.name());
            mAutoMode = getAutoModeForParams(desiredMode);
        }
        mCachedDesiredMode = desiredMode;
    }

    private AutoModeBase getAutoModeForParams(DesiredMode mode) {
        switch (mode) {
            case DO_NOTHING:
                return new DoNothingMode();
            case DRIVE_BY_CAMERA:
                return new DriveByCameraMode();
            case CARGO_SHIP_LEFT_HAB1:
                return new LeftCargoShipMode(false);
            case CARGO_SHIP_LEFT_HAB2:
                return new LeftCargoShipMode(true);
            case CARGO_SHIP_RIGHT_HAB1:
                return new RightCargoShipMode(false);
            case CARGO_SHIP_RIGHT_HAB2:
                return new RightCargoShipMode(true);
            case STRAIGHT_PATH:
                return new StraightPathMode();
            case SPLINE_PATH:
                return new SplinePathMode();
            case TURN_90_DEG:
                return new Turn90DegMode();
            default:
                return null;
        }

    }

    public void reset() {
        mAutoMode = null;
        mCachedDesiredMode = null;
    }

    public void outputToSmartDashboard() {
        SmartDashboard.putString("AutoModeSelected", mCachedDesiredMode.name());
    }

    public AutoModeBase getAutoMode() {
        return mAutoMode;
    }

    public boolean isDriveByCamera() {
        return mCachedDesiredMode == DesiredMode.DRIVE_BY_CAMERA;
    }
}

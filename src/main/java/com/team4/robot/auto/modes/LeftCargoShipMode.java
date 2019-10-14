package com.team4.robot.auto.modes;

import java.util.Arrays;

import com.team195.lib.motion.PathContainer;
import com.team4.robot.actions.ArmPositionAction;
import com.team4.robot.actions.DriveOpenLoopAction;
import com.team4.robot.actions.DrivePathAction;
import com.team4.robot.actions.DriveVisionAction;
import com.team4.robot.actions.IntakeAction;
import com.team4.robot.actions.OuttakeAction;
import com.team4.robot.actions.ResetPoseFromPathAction;
import com.team4.robot.actions.TurnToHeadingAction;
import com.team4.robot.actions.framework.ParallelAction;
import com.team4.robot.actions.framework.SeriesAction;
import com.team4.robot.actions.framework.WaitAction;
import com.team4.robot.auto.framework.AutoModeBase;
import com.team4.robot.auto.framework.AutoModeEndedException;
import com.team4.robot.auto.paths.left.BallFeederToCargoShip1Path;
import com.team4.robot.auto.paths.left.CargoShip2ToBallFeederPath;
import com.team4.robot.auto.paths.left.Hab2ToCargoShip2Path;
import com.team4.robot.constants.ArmConstants;

public class LeftCargoShipMode extends AutoModeBase{
    boolean mIsHab2;

    public LeftCargoShipMode(boolean isHab2){
        mIsHab2 = isHab2;
    }

    
    @Override
    protected void routine() throws AutoModeEndedException {
        PathContainer path1 = new Hab2ToCargoShip2Path();
        PathContainer path3 = new CargoShip2ToBallFeederPath();
        PathContainer path4 = new BallFeederToCargoShip1Path();


        runAction(new ResetPoseFromPathAction(path1));
        runAction(new DrivePathAction(path1));
        runAction(new ParallelAction(Arrays.asList(new ArmPositionAction(ArmConstants.shootSetpoint, 1), 
                    new DriveVisionAction(-.25, .4), 
                    new SeriesAction(Arrays.asList(new WaitAction(.85), 
                    new OuttakeAction(1))))));
        runAction(new DriveOpenLoopAction(.3, .3, .3));
        runAction(new ResetPoseFromPathAction(path3));
        runAction(new DrivePathAction(path3));
        runAction(new ParallelAction(Arrays.asList(new ArmPositionAction(ArmConstants.floorSetpoint, .4), 
                    new SeriesAction(Arrays.asList(new WaitAction(.2),
                    new ParallelAction(Arrays.asList(new DriveOpenLoopAction(.3,.3,.5),
                    new SeriesAction(Arrays.asList(new WaitAction(.1),
                    new ParallelAction(Arrays.asList(new IntakeAction(2),
                    new SeriesAction(Arrays.asList(new WaitAction(1),
                    new ParallelAction(Arrays.asList(new ArmPositionAction(ArmConstants.shootSetpoint,.5),
                    new DriveOpenLoopAction(-.3, -.3, .5))))))))))))))));
        runAction(new TurnToHeadingAction(180));
        runAction(new ResetPoseFromPathAction(path4));
        runAction(new DrivePathAction(path4));
        runAction(new ParallelAction(Arrays.asList(new ArmPositionAction(ArmConstants.shootSetpoint, 1), 
                    new DriveVisionAction(-.3, .5), 
                    new SeriesAction(Arrays.asList(new WaitAction(.85), 
                    new OuttakeAction(1))))));
    }
}
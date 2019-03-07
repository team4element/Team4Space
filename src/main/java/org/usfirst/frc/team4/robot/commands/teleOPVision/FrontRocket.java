package org.usfirst.frc.team4.robot.commands.teleOPVision;

import org.usfirst.frc.team4.robot.commands.PID.VisionDistancePID;
import org.usfirst.frc.team4.robot.constants.LimelightConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FrontRocket extends CommandGroup {

  public FrontRocket() {
    addSequential(new VisionDistancePID(LimelightConstants.eFloorToTarget.FRONT_ROCKET.val));
  }
}

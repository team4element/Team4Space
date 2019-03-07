/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4.robot.commands.teleOPVision;

import org.usfirst.frc.team4.robot.commands.PID.VisionDistancePID;
import org.usfirst.frc.team4.robot.constants.LimelightConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SideRocket extends CommandGroup {
  /**
   * Add your docs here.
   */
  public SideRocket() {
    addSequential(new VisionDistancePID(LimelightConstants.eFloorToTarget.SIDE_ROCKET.val));
  }
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4.robot.commands.automodes;

import org.usfirst.frc.team4.robot.commands.PID.DriveTrajectory;
import org.usfirst.frc.team4.robot.constants.AutoConstants;
import org.usfirst.frc.team4.robot.constants.trajectories.TestSpline;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestSplineProfile extends CommandGroup {
  public TestSplineProfile() {
    addSequential(new DriveTrajectory(TestSpline.testSpline, 5, true, AutoConstants.kMotionP, AutoConstants.kMotionD, AutoConstants.kMotionV, AutoConstants.kMotionA));
  }
}

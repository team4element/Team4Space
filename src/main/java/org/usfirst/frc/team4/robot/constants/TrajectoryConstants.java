/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4.robot.constants;

/**
 * Add your docs here.
 */
public class TrajectoryConstants {
    public static final double kP = 1;
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kV = 9550;
    public static final double kA = 112;

    // public static double kTurn = 4;
    public static double kFeedF = 1/kV;

}

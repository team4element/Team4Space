/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4.robot.constants;

import org.usfirst.frc.team4.robot.utilities.ElementMath;

/**
 * Add your docs here.
 */
public class TrajectoryConstants {
    public static final double kP = 0; //one code 4 kristian
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kV = ChassisConstants.talonVoltage/(ElementMath.rpmToInchesPerSecond(ChassisConstants.motorRPM, ChassisConstants.kWheelDiameter/2)/ChassisConstants.gearReduction);//((5840*Math.PI*ChassisConstants.kWheelDiameter)/(50/32));
    public static final double kA = 0;// 12.475/((2*6*1.41*ChassisConstants.gearRatio)/(ChassisConstants.kWheelDiameter*38.55535));

    // public static double kTurn = 4;
    public static double kFeedF = 1/kV;

}

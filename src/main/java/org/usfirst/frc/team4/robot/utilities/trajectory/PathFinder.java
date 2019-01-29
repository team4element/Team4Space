/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4.robot.utilities.trajectory;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.constants.ChassisConstants;
import org.usfirst.frc.team4.robot.constants.TrajectoryConstants;

import edu.wpi.first.wpilibj.SPI;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * Add your docs here.
 */
public class PathFinder {
    Trajectory.Config config;
    Waypoint[] points;
    TankModifier modifier;

    public EncoderFollower right;
    public EncoderFollower left;



    public double lPower;
    public double rPower;

    public double angleDifference;
    public double turn;

    public double output;

    public double gyro_heading;

    public double desired_heading;

    public AHRS gyro;

    public PathFinder() {
        gyro = new AHRS(SPI.Port.kMXP);
        points = new Waypoint[]{
            new Waypoint(1500, 0, 0),
            new Waypoint(30, 60, 0),
            new Waypoint(0,0,0)
        };
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 9550, 112, 0);
            
        Trajectory trajectory = Pathfinder.generate(points, config);

        modifier = new TankModifier(trajectory).modify(ChassisConstants.wheelbase_width);
        left = new EncoderFollower(modifier.getLeftTrajectory());
        right = new EncoderFollower(modifier.getRightTrajectory());

       

        gyro_heading = gyro.getAngle();
        desired_heading = Pathfinder.r2d(left.getHeading());  

        angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
        turn = 0.8 * (-1.0/80.0) * angleDifference;
    }
    
    public void configEncoder(){
        left.configureEncoder(Robot.m_chassis.leftMiddleMotor.getSelectedSensorPosition(0),ChassisConstants.ticksPerRevolution , ChassisConstants.kWheelDiameter);
        right.configureEncoder(Robot.m_chassis.leftMiddleMotor.getSelectedSensorPosition(0),ChassisConstants.ticksPerRevolution , ChassisConstants.kWheelDiameter);
    }
    
    public void configPIDVA(){
        left.configurePIDVA(TrajectoryConstants.kP, TrajectoryConstants.kI, TrajectoryConstants.kD, TrajectoryConstants.kFeedF, TrajectoryConstants.kA);
        right.configurePIDVA(TrajectoryConstants.kP, TrajectoryConstants.kI, TrajectoryConstants.kD, TrajectoryConstants.kFeedF, TrajectoryConstants.kA);
    }
 
        
    

    

    // public double getOutput(){
    //     return output = (calculateLeftOutput() + calculateRightOutput()) / 2;
    // }
  


    public double leftPower(){
        return lPower + turn;
    }
    public double rightPower(){
        return  - turn;
    }
    public void setOutput(){
        Robot.m_chassis.setPower(leftPower(), rightPower());
    }

}

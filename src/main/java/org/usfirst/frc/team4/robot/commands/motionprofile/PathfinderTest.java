// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package org.usfirst.frc.team4.robot.commands.motionprofile;

// import org.usfirst.frc.team4.robot.Robot;
// import org.usfirst.frc.team4.robot.constants.ChassisConstants;
// import org.usfirst.frc.team4.robot.constants.TrajectoryConstants;

// import edu.wpi.first.wpilibj.command.Command;
// import jaci.pathfinder.Pathfinder;
// import jaci.pathfinder.Trajectory;
// import jaci.pathfinder.Waypoint;
// import jaci.pathfinder.followers.EncoderFollower;
// import jaci.pathfinder.modifiers.TankModifier;

// public class PathfinderTest extends Command {
//   Waypoint[] points;
//   Trajectory.Config config;
  

//   TankModifier modifier;
//   EncoderFollower left;
//   EncoderFollower right;
  
// double gyro_heading, desired_heading, angleDifference, turn;

//   public PathfinderTest() {
   

//       left.configureEncoder(Robot.m_chassis.leftMiddleMotor.getSelectedSensorPosition(0),ChassisConstants.ticksPerRevolution , ChassisConstants.kWheelDiameter);
//       right.configureEncoder(Robot.m_chassis.leftMiddleMotor.getSelectedSensorPosition(0),ChassisConstants.ticksPerRevolution , ChassisConstants.kWheelDiameter);

//       left.configurePIDVA(TrajectoryConstants.kP, TrajectoryConstants.kI, TrajectoryConstants.kD, TrajectoryConstants.kFeedF, TrajectoryConstants.kA);
//       right.configurePIDVA(TrajectoryConstants.kP, TrajectoryConstants.kI, TrajectoryConstants.kD, TrajectoryConstants.kV, TrajectoryConstants.kA);

//       gyro_heading = Robot.m_chassis.getGyro();
//       desired_heading = Pathfinder.r2d(left.getHeading());  

//       angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
     


//     requires(Robot.m_chassis);
//   }

//   // Called just before this Command runs the first time
//   @Override
//   protected void initialize() {
    
//   }

//   // Called repeatedly when this Command is scheduled to run
//   @Override
//   protected void execute() {
//     double lPower = left.calculate(Robot.m_chassis.getRawLeftEncoder());
//      double rPower= right.calculate(Robot.m_chassis.getRawRightEncoder());
//      turn = 0.8 * (-1.0/80.0) * angleDifference;
//     // Robot.m_chassis.setPower(lPower + turn , rPower - turn);
  
//     System.out.println("Right Power is: "+ rPower);
//     System.out.println("Left Power is: " +lPower);
//   }

//   // Make this return true when this Command no longer needs to run execute()
//   // master pawgrammer
//   @Override
//   protected boolean isFinished() {
//     return false;
//   }

//   // Called once after isFinished returns true
//   @Override
//   protected void end() {
//     Robot.m_chassis.Brake();
//   }
//   @Override
//   protected void interrupted() {
//     end();
//   }
// }

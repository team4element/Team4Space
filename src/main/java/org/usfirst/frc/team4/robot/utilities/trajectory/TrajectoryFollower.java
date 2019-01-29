package org.usfirst.frc.team4.robot.utilities.trajectory;



import org.usfirst.frc.team4.robot.utilities.LightningMath;

import jaci.pathfinder.Trajectory;

/**
 * PID + Feedforward controller for following a Trajectory.
 *
 * @author Jared341
 */
public class TrajectoryFollower {

  private double kp_;
  // private double ki_;  // Not currently used, but might be in the future.
  private double kd_;
  private double kv_;
  private double ka_;
  private double last_error_;
  private double current_heading = 0;
  private int current_segment;
  private Trajectory profile_;

  public TrajectoryFollower() {

  }

  public void configure(double kp, double ki, double kd, double kv, double ka) {
    kp_ = kp;
    // ki_ = ki;
    kd_ = kd;
    kv_ = kv;
    ka_ = ka;
  }

  public void reset() {
    last_error_ = 0.0;
    current_segment = 0;
  }

  public void setTrajectory(Trajectory profile) {
    profile_ = profile;
  }

  public Trajectory.Segment getSegment() {
      return profile_.get(current_segment);
  }
  
  public double calculate(double distance_so_far) {
    if (current_segment < profile_.length()) {
      Trajectory.Segment segment = profile_.get(current_segment++);
      Trajectory.Segment next = profile_.get(current_segment);
      double error = segment.position - distance_so_far;
      double output = kp_ * error + kd_ * ((error - last_error_)
              / segment.dt - segment.velocity) + (kv_ * next.velocity
              + ka_ * next.acceleration);

      last_error_ = error;
      current_heading = segment.heading;
//      current_segment++;
      //System.out.println("so far: " + distance_so_far + "; output: " + output);
      return output;
    } else {
      return 0;
    }
  }

  public double getHeading() {
    return current_heading;
  }

  public double deltaHeading() {
    return LightningMath.boundThetaNegPiToPi(profile_.get(current_segment).heading - current_heading);
  }

  public boolean isFinishedTrajectory() {
    return current_segment >= profile_.length();
  }
}
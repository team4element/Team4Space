/*==================================================*/
/* Adapted from nerdherd's 2019 code                */
/* Follows Waypoint trajectory                      */
/*==================================================*/

package org.usfirst.frc.team4.robot.utilities;

import java.util.Arrays;
import java.util.List;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;

public class TrajectoryFollower {

    private Trajectory m_trajectory;
    private Segment m_robotSegment, m_lookaheadSegment;
    private Boolean m_goingForwards;

    private double m_robotX, m_robotY, m_targetAngle, m_velocity,
            m_error, m_leftDesiredVel, m_rightDesiredVel, m_kP, m_kD, m_kV, m_kA, m_turn, m_dT, m_lastError;
    private int m_lookaheadIndex, m_robotIndex, m_lookahead;
    private List<Segment> m_trajectoryList;



    public TrajectoryFollower(Trajectory traj, int lookahead, boolean goingForwards, double kP, double kD, double kV, double kA) {
        m_trajectory = traj;
        m_lookahead = lookahead;
        m_goingForwards = goingForwards;
        m_robotIndex = 0;
        m_kP = kP;
        m_kD = kD;
        m_kV = kV;
        m_kA = kA;
        m_trajectoryList = Arrays.asList(m_trajectory.segments);
        m_lookaheadIndex = 0;
        m_lastError = 0;

    }

    //calculates left and right velocity
    public void calculate(double robotX, double robotY, double robotTheta, double dT) {
        m_robotX = robotX;
        m_robotY = robotY;
        m_dT = dT;
        // Get point closest to the robot, based off the last point to the robot was closest to
        m_robotSegment = getClosestSegment(m_robotX, m_robotY, m_trajectory, m_robotIndex, 5);
        m_robotIndex = m_trajectoryList.indexOf(m_robotSegment);
        m_lookaheadIndex = m_robotIndex + m_lookahead;
        if (m_lookaheadIndex > m_trajectory.length() - 1) {
            m_lookaheadIndex = m_trajectory.length() - 1;
        }
        m_velocity = m_kP * m_error +                                               // Proportional
                    m_kD * ((m_error - m_lastError) / m_robotSegment.dt) +          // Derivative
                    (m_kV * m_robotSegment.velocity + m_kA * m_robotSegment.acceleration);
        m_lookaheadSegment = m_trajectory.get(m_lookaheadIndex);
        m_targetAngle = Math.toDegrees(m_lookaheadSegment.heading);
        if (!m_goingForwards) {
            robotTheta += 180;
            m_velocity = -m_velocity; 
        }
        m_error = Pathfinder.boundHalfDegrees(m_targetAngle - robotTheta);
        m_turn = m_error * m_kP + (m_error - m_lastError)/m_dT * m_kD;
        m_leftDesiredVel = m_velocity - m_turn;
        m_rightDesiredVel = m_velocity + m_turn;
    }

    //Checks if the trajectory has finished
    public boolean isFinished() {
        return m_lookaheadIndex >= m_trajectory.length()-1;
    }

    //Gets left velocity
    public double getLeftVelocity() {
        return m_leftDesiredVel;
    }

    //Gets right velocity 
    public double getRightVelocity() {
        return m_rightDesiredVel;
    }

    // gets closest segment to current segmente
    public static Trajectory.Segment getClosestSegment(double x, double y, Trajectory trajectory, int index, int range) {
        double min = 1000000;
        int segIndex = 0;
        int counter = index - range;
        int max = index + range;
        if (max > trajectory.length() - 1) {
            max = trajectory.length() - 1;
        }
        Trajectory.Segment seg;
        Trajectory.Segment closestSeg = trajectory.get(0);
        double dist;
        while (counter != max) {
            if (counter < 0) {
                counter = 0;
            }
            seg = trajectory.get(counter);
            dist = ElementMath.distanceFormula(x, y, seg.x, seg.y);
            if (dist < min) {
                min = dist;
                closestSeg = seg;
            }
            counter += 1;
        }
        return closestSeg;
    }
}
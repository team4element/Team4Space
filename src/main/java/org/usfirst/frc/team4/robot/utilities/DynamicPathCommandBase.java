/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4.robot.utilities;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4.robot.utilities.trajectory.*;

/**
 * Add your docs here.
 */
    public class DynamicPathCommandBase extends Command {

        public DynamicPathCommandBase() {
            super();
        }
    
        public DynamicPathCommandBase(String name) {
            super(name);
        }
    
        public Path getPath() { return null; }
    
        public double duration() {
            Trajectory left = getPath().getLeftWheelTrajectory();
            Trajectory.Segment point = left.getSegment(left.getNumSegments() - 1);
            return point.dt * left.getNumSegments();
        }
        
        @Override
        protected boolean isFinished() {
            return true;
        }
    
        public boolean isReversed() {
            return false;
        }
}

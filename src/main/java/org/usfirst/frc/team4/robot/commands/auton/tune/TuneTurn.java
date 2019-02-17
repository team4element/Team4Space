package org.usfirst.frc.team4.robot.commands.auton.tune;

import org.usfirst.frc.team4.robot.commands.PID.AnglePID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The auto we use to tune angle
 * Requires Gyro 
 */
public class TuneTurn extends CommandGroup {

    public TuneTurn() {
         addSequential(new AnglePID(90));
    }
}

package org.usfirst.frc.team4.robot.commands.automodes;

import org.usfirst.frc.team4.robot.commands.PID.AnglePID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The auton we use to tune angle
 * Requires Gyro 
 */
public class TuneTurn extends CommandGroup {

    public TuneTurn() {
         addSequential(new AnglePID(90));
    }
}

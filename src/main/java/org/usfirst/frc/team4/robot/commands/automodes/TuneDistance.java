package org.usfirst.frc.team4.robot.commands.automodes;

import org.usfirst.frc.team4.robot.commands.PID.DrivePID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The auton command we use to tune distance PID controller
 * Make sure to tune Angle First
 * Requries Gyro and Encoders
 */
public class TuneDistance extends CommandGroup {


	public TuneDistance() {

		addSequential(new DrivePID(300, 0), 5);

	}
}

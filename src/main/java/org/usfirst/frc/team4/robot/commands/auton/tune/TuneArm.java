package org.usfirst.frc.team4.robot.commands.auton.tune;

import org.usfirst.frc.team4.robot.commands.PID.ArmPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TuneArm extends CommandGroup {
  /**
   * Use this to tune the arm
   * Requires the pot on arm
   */
  public TuneArm() {
    addSequential(new ArmPID(0));
  }
}

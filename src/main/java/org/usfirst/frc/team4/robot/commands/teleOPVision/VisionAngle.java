package org.usfirst.frc.team4.robot.commands.teleOPVision;

import org.usfirst.frc.team4.robot.commands.PID.VisionAnglePID;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class VisionAngle extends CommandGroup {
  public VisionAngle() {
    addSequential(new VisionAnglePID());
  }
}

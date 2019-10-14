package com.team4.robot.subsystems;

import com.team4.robot.loops.Looper;

public interface Subsystem {
	void init();
	void subsystemHome();
	void registerEnabledLoops(Looper in);
}
package com.team4.robot.actions.framework;

import com.team195.lib.reporters.ConsoleReporter;

/**
 * Runnable class with reports all uncaught throws to CrashTracker
 */
public abstract class CrashTrackingRunnable implements Runnable {

    @Override
    public final void run() {
        try {
            runCrashTracked();
        } catch (Throwable t) {
            ConsoleReporter.report(t);
        }
    }

    public abstract void runCrashTracked();
}

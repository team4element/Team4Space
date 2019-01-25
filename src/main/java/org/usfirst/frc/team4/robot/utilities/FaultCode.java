package org.usfirst.frc.team4.robot.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FaultCode {
    public enum Codes {
        LEFT_ENCODER_NOT_FOUND, RIGHT_ENCODER_NOT_FOUND,
        LOW_MAIN_VOLTAGE, SLOW_LOOPER, MISMATCHED_MOTION_PROFILES, 
        NAVX_ERROR, INTERNAL_ERROR
    }

    private static HashSet<Codes> faults = new HashSet<>();
    private static boolean first_time = true;
    private static boolean dummy_light = false;

    private static Path getFaultPath() {
        return Paths.get("/home/lvuser/faults.log");
    }

    public static void write(Codes code) {
        write(code, "");
    }
    
    public static void update() {
        for (Codes c : Codes.values()) {
            SmartDashboard.putBoolean("FAULT_" + c.toString(), !faults.contains(c));
        }        
    }
    
    public static void write(Codes code, String msg) {
        dummy_light = true;
        try {
            if (first_time) {
                for (Codes c : Codes.values()) {
                    SmartDashboard.putBoolean("FAULT_" + c.toString(), true);
                }
                Files.write(getFaultPath(), ("######### RESTART #########\n").getBytes(), StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
                first_time = false;
            }
            
            if (!faults.contains(code)) {
                faults.add(code);
                Files.write(Paths.get("/home/lvuser/faults.log"),
                        ("FAULT Detected: " + code.toString() + " " + msg + "\n").getBytes(), StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
                System.out.println("FAULT: " + code + " " + msg);
                SmartDashboard.putBoolean("FAULT_" + code.toString(), false);
            }
        } catch (IOException e) {
            System.out.println("Unable to write fault code " + code);
            e.printStackTrace();
        }
    }
    
    public boolean dummyLightOn() { return dummy_light; }
}
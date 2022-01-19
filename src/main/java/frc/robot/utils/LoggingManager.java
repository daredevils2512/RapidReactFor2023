// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import edu.wpi.first.wpilibj.Filesystem;

/** Add your docs here. */
public class LoggingManager {

    private OutputStream logStream;
    public Handler handler;
    public Logger robotLogger;

    public LoggingManager() {

        try {
            logStream = new FileOutputStream(Filesystem.getOperatingDirectory() + "/log.log");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        handler = new StreamHandler(logStream, new SimpleFormatter());
        robotLogger = Logger.getGlobal();
        robotLogger.addHandler(handler);
        // robotLogger.addHandler(new StreamHandler(System.out, new SimpleFormatter()));

    }


}

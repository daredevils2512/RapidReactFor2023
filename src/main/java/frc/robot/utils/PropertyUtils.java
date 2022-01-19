// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import edu.wpi.first.wpilibj.Filesystem;

/** Add your docs here. */
public class PropertyUtils {

    public static Properties loadProperties(String name) throws IOException {
        Properties properties = new Properties();
        String path = getPath(name);
        InputStream in = new FileInputStream(path);
        properties.load(in);
        return properties;
    }

    public static void saveProperties(Properties properties, String name, String comments) throws IOException {
        String path = getPath(name);
        OutputStream os = new FileOutputStream(path);
        properties.store(os, comments);
    }

    public static String getPath(String name) {
        return Filesystem.getDeployDirectory().getPath() + '/' + name + ".properties";
    }
}

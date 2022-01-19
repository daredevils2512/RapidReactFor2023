// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.io;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

/** Add your docs here. */
public class NTButton {

    public static Thread ntButtonsManager = new Thread();
    public static List<Runnable> registry = new ArrayList<>();
    private static boolean continueHandling = true;

    private NetworkTableEntry m_entry;
    private Runnable m_action;

    public NTButton(Runnable toRun, String name, NetworkTable table) {
        m_entry = table.getEntry(name);
        m_action = toRun;

        m_entry.setBoolean(false);
    }

    public void update() {
        if (m_entry.getBoolean(false)) {
            m_action.run();
            m_entry.setBoolean(false);
        }
    }

    public void runConcurrently() {
        registry.add(this::update);
    }

    public static void handleConcurrently() {
        while (continueHandling) {
            long time = System.currentTimeMillis();
            long endtime = time + 50;
            for (Runnable action : registry) {
                action.run();
            }
            while (System.currentTimeMillis() < endtime) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    // shouldn't happen
                    e.printStackTrace();
                }
            }
        }
    }

    public static void startConcurrentHandling() {
        ntButtonsManager = new Thread(() -> handleConcurrently());
        continueHandling = true;
        ntButtonsManager.start();
    }

    public static void stopConcurrentHandling() {
        continueHandling = false;
    }
}

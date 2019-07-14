package com.View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrameDrawer extends JFrame {
    public MainFrameDrawer(String file, String title) {
        this.setTitle(title);
        if (file.equals("dfa")) {
            this.setLocation(700, 80);
        } else {
            this.setLocation(200, 80);
        }

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        add(new DrawingPanel(file));
        setSize(DataCalculator.getDimensions());
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrameDrawer("input", "demo");
    }
}

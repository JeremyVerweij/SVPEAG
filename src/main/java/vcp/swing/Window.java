package vcp.swing;

import javax.swing.*;

public class Window extends JFrame {
    public Window(String name, int width, int height){
        super(name);

        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setVisible(true);
    }

    public Window(String name){
        this(name, 800, 800);
    }
}

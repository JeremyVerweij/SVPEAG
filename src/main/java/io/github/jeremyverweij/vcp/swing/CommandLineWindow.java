package io.github.jeremyverweij.vcp.swing;

import javax.swing.*;
import java.awt.*;

public class CommandLineWindow extends Window{
    private final JPanel main;

    public CommandLineWindow() {
        super("Command Line", 240, 240);

        this.main = new JPanel();
        this.main.setLayout(new BoxLayout(this.main, BoxLayout.Y_AXIS));
        this.main.setBackground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(this.main);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        this.getContentPane().add(scrollPane);
    }

    private void addMessage(String message, String prefix, Color color){
        if (message.contains("\n")){
            for (String subMessage : message.split("\n")) {
                addMessage(subMessage, prefix, color);
            }

            return;
        }

        SwingUtilities.invokeLater(() -> {
            JLabel label = new JLabel("[" + prefix + "] " + message);
            label.setForeground(color);

            main.add(label);
            this.revalidate();
            this.repaint();
        });
    }

    public void log(String message){
        addMessage(message, "LOG", Color.WHITE);
    }

    public void warn(String message){
        addMessage(message, "WARN", Color.ORANGE);
    }

    public void error(String message){
        addMessage(message, "ERROR", Color.RED);
    }
}

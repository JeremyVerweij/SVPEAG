package io.github.jeremyverweij.vcp.swing;

import io.github.jeremyverweij.vcp.VcpApp;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

public class VcpAppWindow extends JFrame {
    private final VcpApp app;

    public VcpAppWindow(VcpApp app, int width, int height){
        super(app.getName());
        this.app = app;
        this.app.setOwner(this);

        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationByPlatform(true);
    }

    public VcpAppWindow(VcpApp app){
        this(app, 800, 800);
    }

    /**
     * Create a callback and sets cleans up everything
     * @param callback callback for when window is closed
     */
    public void addCloseCallback(Consumer<VcpAppWindow> callback){
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                callback.accept(VcpAppWindow.this);
                VcpAppWindow.this.close();
            }
        });
    }

    /**
     * Create a callback when window is closed/set to tray
     * @param callback callback for when window is closed
     */
    public void addHideCloseCallback(Consumer<VcpAppWindow> callback){
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                callback.accept(VcpAppWindow.this);
            }
        });
    }

    public void close(){
        this.app.getPlayGround().getAllComponents().clear();
        this.dispose();
    }

    public VcpApp getApp() {
        return app;
    }
}

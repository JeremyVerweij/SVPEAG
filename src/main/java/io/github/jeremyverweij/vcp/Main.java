package io.github.jeremyverweij.vcp;

import io.github.jeremyverweij.vcp.swing.VcpAppWindow;

public class Main {
    public static VcpApp vcpApp;

    public static void main(String[] args) {
        vcpApp = new VcpApp("vcp");
        vcpApp.addDefaultNodes();

        new VcpAppWindow(vcpApp);

        vcpApp.start();
    }
}
package io.github.jeremyverweij.vcp;

public class Main {
    public static App app;

    public static void main(String[] args) {
        app = new App("vcp");
        app.addDefaultNodes();
        app.start();
    }
}
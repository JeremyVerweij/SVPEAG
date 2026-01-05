package vcp;

import vcp.components.*;
import vcp.swing.CommandLineWindow;
import vcp.swing.PlayGround;
import vcp.swing.Window;

import java.awt.*;

public class App {
    public static final int NO_OPTIONS = 0;
    public static final int SHOW_COMMAND_LINE = 0b1;

    private static boolean checkOption(int options, int option){
        return (options & option) != 0;
    }

    private final CommandLineWindow commandLineWindow;
    private final Window mainWindow;
    private final PlayGround playGround;

    public App(String name, int options){
        this.commandLineWindow = new CommandLineWindow();
        this.mainWindow = new Window(name);
        this.playGround = new PlayGround();

        this.commandLineWindow.setVisible(checkOption(options, SHOW_COMMAND_LINE));

        ClickComponent clickComponent = new ClickComponent(this, 0, 0);
        PrintValueComponent printValueComponent = new PrintValueComponent(this, 1000, 200);
        WaitComponent waitComponent = new WaitComponent(this, 200, 400);
        PrintValueComponent printValueComponent1 = new PrintValueComponent(this, 400, 200);
        AndComponent andComponent = new AndComponent(this, 600, 200);

        StringComponent stringComponent = new StringComponent(this, 800, 250);
        StringComponent stringComponent1 = new StringComponent(this, 200, 200);

        clickComponent.addConnection(stringComponent1, 0);
        clickComponent.addConnection(waitComponent, 0);
        printValueComponent1.addConnection(andComponent, 0);
        waitComponent.addConnection(andComponent, 1);
        andComponent.addConnection(stringComponent, 0);
        andComponent.addConnection(printValueComponent, 0);
        stringComponent.addConnection(printValueComponent, 1);
        clickComponent.addConnection(printValueComponent1, 0);
        stringComponent1.addConnection(printValueComponent1, 1);

        this.playGround.addComponent(clickComponent);
        this.playGround.addComponent(printValueComponent);
        this.playGround.addComponent(printValueComponent1);
        this.playGround.addComponent(waitComponent);
        this.playGround.addComponent(andComponent);
        this.playGround.addComponent(stringComponent);
        this.playGround.addComponent(stringComponent1);

        this.mainWindow.getContentPane().setLayout(new BorderLayout());
        this.mainWindow.getContentPane().add(this.playGround);
    }

    public CommandLineWindow getCommandLineWindow() {
        return commandLineWindow;
    }

    public Window getMainWindow() {
        return mainWindow;
    }

    public PlayGround getPlayGround() {
        return playGround;
    }
}

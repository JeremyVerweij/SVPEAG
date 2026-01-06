package vcp;

import vcp.components.*;
import vcp.swing.CommandLineWindow;
import vcp.swing.PlayGround;
import vcp.swing.Window;
import vcp.walker.DataType;
import vcp.walker.NodeColors;
import vcp.walker.nodes.PrintNode;
import vcp.walker.nodes.RunNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static final int NO_OPTIONS = 0;
    public static final int SHOW_COMMAND_LINE = 0b1;

    private static boolean checkOption(int options, int option){
        return (options & option) != 0;
    }

    private final CommandLineWindow commandLineWindow;
    private final Window mainWindow;
    private final PlayGround playGround;
    private final NodeColors nodeColors;
    private final ContextMenuComponent contextMenu;
    private final Map<DataType, List<String>> variables;

    public App(String name, int options){
        this.commandLineWindow = new CommandLineWindow();
        this.mainWindow = new Window(name);
        this.contextMenu = new ContextMenuComponent(this, 0 , 0);
        this.playGround = new PlayGround(this.contextMenu);
        this.nodeColors = new NodeColors();
        this.variables = new HashMap<>();

        this.commandLineWindow.setVisible(checkOption(options, SHOW_COMMAND_LINE));

        this.mainWindow.getContentPane().setLayout(new BorderLayout());
        this.mainWindow.getContentPane().add(this.playGround);

        this.initNodeColors();
        this.testPlayground();
    }

    private void initNodeColors(){
        this.nodeColors.addColor(RunNode.class, Color.GREEN);
        this.nodeColors.addColor(PrintNode.class, Color.RED);
    }

    private void testPlayground(){
        createVar(new DataType.StringType(), "testString");

        this.playGround.addComponent(new NodeComponent(this, 0, 0, new RunNode()));
        this.playGround.addComponent(new NodeComponent(this, 100, 0, new PrintNode()));
    }

    public void requestContextMenu(NodeComponent nodeComponent){
        this.contextMenu.show(nodeComponent);
    }

    public void createVar(DataType dataType, String name){
        if (!this.variables.containsKey(dataType))
            this.variables.put(dataType, new ArrayList<>());

        this.variables.get(dataType).add(name);
    }

    public boolean existVar(DataType dataType, String name){
        if (!this.variables.containsKey(dataType)) return false;
        return this.variables.get(dataType).contains(name);
    }

    public List<String> getVarsForType(DataType dataType){
        if (!this.variables.containsKey(dataType))
            this.variables.put(dataType, new ArrayList<>());

        return this.variables.get(dataType);
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

    public NodeColors getNodeColors() {
        return nodeColors;
    }
}

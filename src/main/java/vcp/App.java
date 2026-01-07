package vcp;

import vcp.components.*;
import vcp.swing.CommandLineWindow;
import vcp.swing.PlayGround;
import vcp.swing.SideBar;
import vcp.swing.Window;
import vcp.walker.CodeNode;
import vcp.walker.DataType;
import vcp.walker.NodeColors;
import vcp.walker.executor.CodeWalker;

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
    private final SideBar sideBar;
    private final NodeColors nodeColors;
    private final ContextMenuComponent contextMenu;
    private final Map<String, DataType> variables;

    private CodeWalker codeWalker;

    public App(String name, int options){
        this.commandLineWindow = new CommandLineWindow();
        this.mainWindow = new Window(name);
        this.contextMenu = new ContextMenuComponent(this, 0 , 0);
        this.playGround = new PlayGround(this.contextMenu);
        this.sideBar = new SideBar(this);
        this.nodeColors = new NodeColors();
        this.variables = new HashMap<>();

        this.codeWalker = new CodeWalker((a, b, c) -> {}, this);

        this.commandLineWindow.setVisible(checkOption(options, SHOW_COMMAND_LINE));

        this.mainWindow.getContentPane().setLayout(new BorderLayout());
        this.mainWindow.getContentPane().add(this.sideBar, BorderLayout.WEST);
        this.mainWindow.getContentPane().add(this.playGround, BorderLayout.CENTER);
    }

    public void setCodeWalker(CodeWalker.INodeEvaluator nodeEvaluator) {
        this.codeWalker = new CodeWalker(nodeEvaluator, this);
    }

    public void requestContextMenu(NodeComponent nodeComponent){
        this.contextMenu.show(nodeComponent);
    }

    public void addNode(Class<? extends CodeNode> node, Color color){
        this.nodeColors.addColor(node, color);
    }

    public void addCategory(Color color, String name){
        this.sideBar.getColorCategories().put(color, name);
    }

    public void createVar(DataType dataType, String name){
        this.variables.put(name, dataType);
    }

    public boolean existVar(DataType dataType, String name){
        if (!this.variables.containsKey(name)) return false;
        return this.variables.get(name).equals(dataType);
    }

    public String[] getVarsForType(DataType dataType){
        return this.variables.keySet().stream().filter((e) -> existVar(dataType, e)).toArray(String[]::new);
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

    public CodeWalker getCodeWalker() {
        return codeWalker;
    }

    public void start(){
        this.sideBar.reloadComponents();
        this.playGround.repaint();
    }

    public void removeVar(String removeVar) {
        this.variables.remove(removeVar);
    }
}

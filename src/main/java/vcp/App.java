package vcp;

import vcp.components.ContextMenuComponent;
import vcp.components.NodeComponent;
import vcp.swing.CommandLineWindow;
import vcp.swing.PlayGround;
import vcp.swing.SideBar;
import vcp.swing.Window;
import vcp.walker.CodeNode;
import vcp.walker.DataType;
import vcp.walker.LoadAndSaveState;
import vcp.walker.NodeColors;
import vcp.walker.executor.CodeWalker;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static final int NO_OPTIONS = 0;

    private static boolean checkOption(int options, int option){
        return (options & option) != 0;
    }

    private final Window mainWindow;
    private final PlayGround playGround;
    private final SideBar sideBar;
    private NodeColors nodeColors;
    private final ContextMenuComponent contextMenu;
    private final Map<String, DataType> variables;
    private final LoadAndSaveState loadAndSaveState;
    private final List<NodeComponent> startPoints;
    private final int options;

    private CodeWalker codeWalker;

    public App(String name, int options){
        this.options = options;
        this.mainWindow = new Window(name);
        this.contextMenu = new ContextMenuComponent(this, 0 , 0);
        this.playGround = new PlayGround(this.contextMenu, this);
        this.sideBar = new SideBar(this);
        this.nodeColors = new NodeColors();
        this.variables = new HashMap<>();
        this.loadAndSaveState = new LoadAndSaveState(this);
        this.startPoints = new ArrayList<>();

        this.codeWalker = new CodeWalker((a, b, c, d) -> {}, this);

        this.mainWindow.getContentPane().setLayout(new BorderLayout());
        this.mainWindow.getContentPane().add(this.sideBar, BorderLayout.WEST);
        this.mainWindow.getContentPane().add(this.playGround, BorderLayout.CENTER);
    }

    public App(String name, App app){
        this(name, app.options);
        this.codeWalker = app.codeWalker;
        this.nodeColors = app.nodeColors;
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

    public String[] getVarsForType(DataType dataType, boolean allowSuperTypes){
        return this.variables.keySet().stream().filter((e) -> existVar(dataType, e)).filter((e) -> {
            if (allowSuperTypes) return true;
            return dataType.getClass() == this.variables.get(e).getClass();
        }).toArray(String[]::new);
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
        this.mainWindow.setVisible(true);
    }

    public void removeVar(String removeVar) {
        this.variables.remove(removeVar);
    }

    public String generate(){
        StringBuilder builder = new StringBuilder();
        for (NodeComponent startPoint : this.startPoints) {
            builder.append(this.codeWalker.eval(startPoint.getCodeNode())).append("\n");
        }

        return builder.toString();
    }

    public void save(String path){
        this.loadAndSaveState.save(path);
    }

    public void load(String path){
        this.loadAndSaveState.load(path);
    }

    public List<NodeComponent> getStartPoints() {
        return startPoints;
    }

    public Map<String, DataType> getVars() {
        return this.variables;
    }
}

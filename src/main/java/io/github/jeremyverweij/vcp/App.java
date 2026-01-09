package io.github.jeremyverweij.vcp;

import io.github.jeremyverweij.vcp.components.ContextMenuComponent;
import io.github.jeremyverweij.vcp.components.NodeComponent;
import io.github.jeremyverweij.vcp.swing.PlayGround;
import io.github.jeremyverweij.vcp.swing.SideBar;
import io.github.jeremyverweij.vcp.swing.Window;
import io.github.jeremyverweij.vcp.walker.CodeNode;
import io.github.jeremyverweij.vcp.walker.DataType;
import io.github.jeremyverweij.vcp.walker.LoadAndSaveState;
import io.github.jeremyverweij.vcp.walker.NodeColors;
import io.github.jeremyverweij.vcp.walker.executor.CodeWalker;
import io.github.jeremyverweij.vcp.walker.nodes.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    private final Window mainWindow;
    private final PlayGround playGround;
    private final SideBar sideBar;
    private NodeColors nodeColors;
    private final ContextMenuComponent contextMenu;
    private final Map<String, DataType> variables;
    private final LoadAndSaveState loadAndSaveState;
    private final List<NodeComponent> startPoints;
    private final List<String> constants;

    private CodeWalker codeWalker;

    public App(String name){
        this.mainWindow = new Window(name);
        this.contextMenu = new ContextMenuComponent(this, 0 , 0);
        this.playGround = new PlayGround(this.contextMenu, this);
        this.variables = new HashMap<>();
        this.sideBar = new SideBar(this);
        this.nodeColors = new NodeColors();
        this.loadAndSaveState = new LoadAndSaveState(this);
        this.startPoints = new ArrayList<>();
        this.constants = new ArrayList<>();

        this.codeWalker = new CodeWalker((a, b, c, d) -> {}, e -> e, this);

        this.mainWindow.getContentPane().setLayout(new BorderLayout());
        this.mainWindow.getContentPane().add(this.sideBar, BorderLayout.WEST);
        this.mainWindow.getContentPane().add(this.playGround, BorderLayout.CENTER);
    }

    public App(String name, App app){
        this(name);
        this.codeWalker = app.codeWalker;
        this.nodeColors = app.nodeColors;
    }

    public void setCodeWalker(CodeWalker.INodeEvaluator nodeEvaluator) {
        setCodeWalker(nodeEvaluator, e -> e);
    }

    public void setCodeWalker(CodeWalker.INodeEvaluator nodeEvaluator, CodeWalker.INodePostProcessor nodePostProcessor) {
        this.codeWalker = new CodeWalker(nodeEvaluator, nodePostProcessor, this);
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

        if (!name.startsWith("${"))
            this.sideBar.addVar(name);
    }

    public void removeVar(String removeVar) {
        this.variables.remove(removeVar);
        this.sideBar.removeVar(removeVar);
    }

    public boolean existVar(String name){
        return this.variables.containsKey(name);
    }

    public String[] getVarsForType(DataType dataType, boolean allowSuperTypes, boolean allowDirect){
        return this.variables.keySet()
                .stream()
                .filter(this::existVar)
                .filter(e -> allowDirect == e.startsWith("${"))
                .filter((e) -> {
                    if (allowSuperTypes) return dataType.getClass().isAssignableFrom(this.variables.get(e).getClass());
                    return dataType.getClass() == this.variables.get(e).getClass();
                })
                .toArray(String[]::new);
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
        this.playGround.getAllComponents().clear();
        this.loadAndSaveState.load(path);
    }

    public List<NodeComponent> getStartPoints() {
        return startPoints;
    }

    public Map<String, DataType> getVars() {
        return this.variables;
    }

    public void addConstant(String name, DataType type){
        this.variables.put("${" + name + "}", type);
        this.constants.add(name);
    }

    public void addDefaultNodes(){
        addNode(RunNode.class, Color.GREEN);

        addNode(MathNodes.AddNode.class, Color.MAGENTA);
        addNode(MathNodes.MinusNode.class, Color.MAGENTA);
        addNode(MathNodes.MultiplyNode.class, Color.MAGENTA);
        addNode(MathNodes.DivideNode.class, Color.MAGENTA);

        addNode(VarNodes.Set_ByteNode.class, Color.ORANGE);
        addNode(VarNodes.Set_ShortNode.class, Color.ORANGE);
        addNode(VarNodes.Set_IntNode.class, Color.ORANGE);
        addNode(VarNodes.Set_LongNode.class, Color.ORANGE);
        addNode(VarNodes.Set_FloatNode.class, Color.ORANGE);
        addNode(VarNodes.Set_DoubleNode.class, Color.ORANGE);
        addNode(VarNodes.Set_BooleanNode.class, Color.ORANGE);
        addNode(VarNodes.Set_CharNode.class, Color.ORANGE);
        addNode(VarNodes.Set_StringNode.class, Color.ORANGE);

        addNode(ConditionalNotes.IfNode.class, Color.YELLOW);
        addNode(ConditionalNotes.If_ElseNode.class, Color.YELLOW);
        addNode(ConditionalNotes.WhileNode.class, Color.YELLOW);
        addNode(ConditionalNotes.ForNode.class, Color.YELLOW);

        addNode(LogicNodes.EqualsNode.class, Color.CYAN);
        addNode(LogicNodes.Not_EqualsNode.class, Color.CYAN);
        addNode(LogicNodes.Greater_ThanNode.class, Color.CYAN);
        addNode(LogicNodes.Greater_Than_EqualsNode.class, Color.CYAN);
        addNode(LogicNodes.Smaller_ThanNode.class, Color.CYAN);
        addNode(LogicNodes.Smaller_Than_EqualsNode.class, Color.CYAN);
        addNode(LogicNodes.OrNode.class, Color.CYAN);
        addNode(LogicNodes.AndNode.class, Color.CYAN);
        addNode(LogicNodes.NorNode.class, Color.CYAN);
        addNode(LogicNodes.NandNode.class, Color.CYAN);
        addNode(LogicNodes.NotNode.class, Color.CYAN);
        addNode(LogicNodes.XnorNode.class, Color.CYAN);
        addNode(LogicNodes.XorNode.class, Color.CYAN);

        addCategory(Color.GREEN, "Start");
        addCategory(Color.MAGENTA, "Math");
        addCategory(Color.YELLOW, "Flow");
        addCategory(Color.ORANGE, "Variables");
        addCategory(Color.CYAN, "Logic");
    }
}

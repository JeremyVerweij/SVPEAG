package io.github.jeremyverweij.vcp;

import io.github.jeremyverweij.vcp.components.ContextMenuComponent;
import io.github.jeremyverweij.vcp.components.NodeComponent;
import io.github.jeremyverweij.vcp.swing.PlayGround;
import io.github.jeremyverweij.vcp.swing.SideBar;
import io.github.jeremyverweij.vcp.swing.VcpAppWindow;
import io.github.jeremyverweij.vcp.walker.CodeNode;
import io.github.jeremyverweij.vcp.walker.DataType;
import io.github.jeremyverweij.vcp.walker.LoadAndSaveState;
import io.github.jeremyverweij.vcp.walker.NodeColors;
import io.github.jeremyverweij.vcp.walker.executor.CodeWalker;
import io.github.jeremyverweij.vcp.walker.nodes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class VcpApp {
    private final String name;
    private final PlayGround playGround;
    private final SideBar sideBar;
    private final NodeColors nodeColors;
    private final ContextMenuComponent contextMenu;
    private final Map<String, DataType> variables;
    private final LoadAndSaveState loadAndSaveState;
    private final List<NodeComponent> startPoints;
    private final List<String> constants;
    private Container owner;

    private CodeWalker codeWalker;

    /**
     * Creates an app window and creates all the necessary components
     * <br>
     * Does not show the window yet, use {@link VcpApp#start()} to start and display the window
     * @param name The name of the window
     */
    public VcpApp(String name){
        this.name = name;
        this.contextMenu = new ContextMenuComponent(this, 0 , 0);
        this.playGround = new PlayGround(this.contextMenu, this);
        this.variables = new HashMap<>();
        this.sideBar = new SideBar(this);
        this.nodeColors = new NodeColors();
        this.loadAndSaveState = new LoadAndSaveState(this);
        this.startPoints = new ArrayList<>();
        this.constants = new ArrayList<>();

        setCodeWalker((a, b, c, d) -> {});
    }

    public void setOwner(Container owner){
        this.owner = owner;

        this.owner.setLayout(new BorderLayout());
        this.owner.add(this.sideBar, BorderLayout.WEST);
        this.owner.add(this.playGround, BorderLayout.CENTER);
    }

    /**
     * Starts the app and displays the window
     */
    public void start(){
        this.sideBar.reloadComponents();
        this.playGround.repaint();
        this.owner.setVisible(true);
    }

    /**
     * Generates the built code using the flow control system
     * @return A string containing the generated code
     */
    public String generate(){
        StringBuilder builder = new StringBuilder();
        for (NodeComponent startPoint : this.startPoints) {
            builder.append(this.codeWalker.eval(startPoint.getCodeNode())).append("\n");
        }

        return builder.toString();
    }

    /**
     * Save the flow control pattern to a file
     * @param path file to be saved to
     */
    public void save(String path){
        this.loadAndSaveState.save(path);
    }

    /**
     * Load the flow control pattern from a file
     * @param path file to load from
     */
    public void load(String path){
        this.playGround.getAllComponents().clear();
        this.loadAndSaveState.load(path);
    }

    /**
     * @see VcpApp#setCodeWalker(CodeWalker.INodeEvaluator, CodeWalker.INodePostProcessor)
     */
    public void setCodeWalker(CodeWalker.INodeEvaluator nodeEvaluator) {
        setCodeWalker(nodeEvaluator, (a, b) -> a);
    }

    /**
     * Set the node walker, the node walker is responsible for generating the source code build with the flow control app
     * @param nodeEvaluator evaluates each node and tells what to do next, should also call the function on the next nodes
     * @param nodePostProcessor post node processing uses the generated string to apply tweaks afterward, also gets the constats
     */
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

    public void addConstant(String name, DataType type){
        this.variables.put("${" + name + "}", type);
        this.constants.add(name);
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
                .filter(e -> allowDirect == e.startsWith("${") || allowDirect)
                .filter((e) -> {
                    if (allowSuperTypes) return dataType.getClass().isAssignableFrom(this.variables.get(e).getClass());
                    return dataType.getClass() == this.variables.get(e).getClass();
                })
                .toArray(String[]::new);
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

    public List<NodeComponent> getStartPoints() {
        return startPoints;
    }

    public Map<String, DataType> getVars() {
        return this.variables;
    }

    public List<String> getConstants() {
        return this.constants;
    }

    public String getName() {
        return name;
    }

    public void addDefaultNodes(){
        addNode(FunctionNodes.MainNode.class, Color.GREEN);
        addNode(FunctionNodes.FunctionNode.class, Color.GREEN);
        addNode(FunctionNodes.ReturnNode.class, Color.GREEN);
        addNode(FunctionNodes.Call_FunctionNode.class, Color.GREEN);
        addNode(FunctionNodes.Set_ParamNode.class, Color.GREEN);
        addNode(FunctionNodes.Get_ParamNode.class, Color.GREEN);

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

        addCategory(Color.GREEN, "Function");
        addCategory(Color.MAGENTA, "Math");
        addCategory(Color.YELLOW, "Flow");
        addCategory(Color.ORANGE, "Variables");
        addCategory(Color.CYAN, "Logic");

        addConstant("INDEX", new DataType.IntType());
    }
}

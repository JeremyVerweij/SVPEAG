package io.github.jeremyverweij.vcp;

import io.github.jeremyverweij.vcp.components.ComponentStyler;
import io.github.jeremyverweij.vcp.components.NodeComponent;
import io.github.jeremyverweij.vcp.swing.PlayGround;
import io.github.jeremyverweij.vcp.swing.SideBar;
import io.github.jeremyverweij.vcp.data.DataTypeRegistry;
import io.github.jeremyverweij.vcp.walker.NodeColors;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VcpApp {
    private final String name;
    private final PlayGround playGround;
    private final DataTypeRegistry dataTypeRegistry;
    private final SideBar sideBar;
    private final NodeColors nodeColors;
    private ComponentStyler<NodeComponent> nodeComponentComponentStyler;
//    private final ContextMenuComponent contextMenu;
//    private final LoadAndSaveState loadAndSaveState;
    private final List<NodeComponent> startPoints;
    private Container owner;

    public VcpApp(String name){
        this.name = name;
//        this.contextMenu = new ContextMenuComponent(this, 0 , 0);
        this.playGround = new PlayGround(this);
        this.sideBar = new SideBar(this);
        this.nodeColors = new NodeColors();
//        this.loadAndSaveState = new LoadAndSaveState(this);
        this.startPoints = new ArrayList<>();
        this.dataTypeRegistry = new DataTypeRegistry();
        this.nodeComponentComponentStyler = null;
    }

    public VcpApp setOwner(Container owner){
        this.owner = owner;

        this.owner.setLayout(new BorderLayout());
        this.owner.add(this.sideBar, BorderLayout.WEST);
        this.owner.add(this.playGround, BorderLayout.CENTER);

        return this;
    }

    public VcpApp setNodeComponentPainter(ComponentStyler<NodeComponent> nodeComponentComponentStyler){
        this.nodeComponentComponentStyler = nodeComponentComponentStyler;

        return this;
    }

    /**
     * Starts the app and displays the container
     */
    public VcpApp start(){
//        this.sideBar.reloadComponents();
        this.playGround.repaint();
        this.owner.setVisible(true);

        return this;
    }

//    /**
//     * Generates the built code using the flow control system
//     * @return A string containing the generated code
//     */
//    public String generate(){
//        StringBuilder builder = new StringBuilder();
//        for (NodeComponent startPoint : this.startPoints) {
//            builder.append(this.codeWalker.eval(startPoint.getCodeNode())).append("\n");
//        }
//
//        return builder.toString();
//    }

//    /**
//     * Save the flow control pattern to a file
//     * @param path file to be saved to
//     */
//    public void save(String path){
//        this.loadAndSaveState.save(path);
//    }

//    /**
//     * Load the flow control pattern from a file
//     * @param path file to load from
//     */
//    public void load(String path){
//        this.playGround.getAllComponents().clear();
//        this.loadAndSaveState.load(path);
//    }

    public PlayGround getPlayGround() {
        return playGround;
    }

    public NodeColors getNodeColors() {
        return nodeColors;
    }

    public List<NodeComponent> getStartPoints() {
        return startPoints;
    }

    public DataTypeRegistry getDataTypeRegistry() {
        return dataTypeRegistry;
    }

    public ComponentStyler<NodeComponent> getNodeComponentComponentStyler() {
        return nodeComponentComponentStyler;
    }

    public String getName() {
        return name;
    }

//    public void addDefaultNodes(){
//        addNode(FunctionNodes.MainNode.class, Color.GREEN);
//        addNode(FunctionNodes.FunctionNode.class, Color.GREEN);
//        addNode(FunctionNodes.ReturnNode.class, Color.GREEN);
//        addNode(FunctionNodes.Call_FunctionNode.class, Color.GREEN);
//        addNode(FunctionNodes.Set_ParamNode.class, Color.GREEN);
//        addNode(FunctionNodes.Get_ParamNode.class, Color.GREEN);
//
//        addNode(MathNodes.AddNode.class, Color.MAGENTA);
//        addNode(MathNodes.MinusNode.class, Color.MAGENTA);
//        addNode(MathNodes.MultiplyNode.class, Color.MAGENTA);
//        addNode(MathNodes.DivideNode.class, Color.MAGENTA);
//
//        addNode(VarNodes.Set_ByteNode.class, Color.ORANGE);
//        addNode(VarNodes.Set_ShortNode.class, Color.ORANGE);
//        addNode(VarNodes.Set_IntNode.class, Color.ORANGE);
//        addNode(VarNodes.Set_LongNode.class, Color.ORANGE);
//        addNode(VarNodes.Set_FloatNode.class, Color.ORANGE);
//        addNode(VarNodes.Set_DoubleNode.class, Color.ORANGE);
//        addNode(VarNodes.Set_BooleanNode.class, Color.ORANGE);
//        addNode(VarNodes.Set_CharNode.class, Color.ORANGE);
//        addNode(VarNodes.Set_StringNode.class, Color.ORANGE);
//
//        addNode(ConditionalNotes.IfNode.class, Color.YELLOW);
//        addNode(ConditionalNotes.If_ElseNode.class, Color.YELLOW);
//        addNode(ConditionalNotes.WhileNode.class, Color.YELLOW);
//        addNode(ConditionalNotes.ForNode.class, Color.YELLOW);
//
//        addNode(LogicNodes.EqualsNode.class, Color.CYAN);
//        addNode(LogicNodes.Not_EqualsNode.class, Color.CYAN);
//        addNode(LogicNodes.Greater_ThanNode.class, Color.CYAN);
//        addNode(LogicNodes.Greater_Than_EqualsNode.class, Color.CYAN);
//        addNode(LogicNodes.Smaller_ThanNode.class, Color.CYAN);
//        addNode(LogicNodes.Smaller_Than_EqualsNode.class, Color.CYAN);
//        addNode(LogicNodes.OrNode.class, Color.CYAN);
//        addNode(LogicNodes.AndNode.class, Color.CYAN);
//        addNode(LogicNodes.NorNode.class, Color.CYAN);
//        addNode(LogicNodes.NandNode.class, Color.CYAN);
//        addNode(LogicNodes.NotNode.class, Color.CYAN);
//        addNode(LogicNodes.XnorNode.class, Color.CYAN);
//        addNode(LogicNodes.XorNode.class, Color.CYAN);
//
//        addCategory(Color.GREEN, "Function");
//        addCategory(Color.MAGENTA, "Math");
//        addCategory(Color.YELLOW, "Flow");
//        addCategory(Color.ORANGE, "Variables");
//        addCategory(Color.CYAN, "Logic");
//
//        addConstant("INDEX", new DataType.IntType());
//    }
}

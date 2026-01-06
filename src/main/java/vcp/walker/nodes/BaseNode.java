package vcp.walker.nodes;

import vcp.walker.CodeNode;
import vcp.walker.DataType;

public class BaseNode implements CodeNode {
    private final CodeNode[] outNodes;
    private final String[] directInVal;
    private final String[] inVarNames;
    private final String[] outVarNames;
    private CodeNode inNode;

    public BaseNode(){
        this.outNodes = new CodeNode[getOutConnections()];
        this.inNode = null;

        this.inVarNames = new String[this.dataInputs()];
        this.directInVal = new String[this.dataInputs()];

        this.outVarNames = new String[this.dataOutputs()];
    }

    @Override
    public CodeNode[] outNodes() {
        return this.outNodes;
    }

    @Override
    public CodeNode inNode() {
        if (!hasInConnection()) return null;

        return this.inNode;
    }

    @Override
    public String getLabelForOut(int i) {
        return "OUT_" + i;
    }

    public void addInNode(CodeNode codeNode){
        this.inNode = codeNode;
    }

    public void addOutNode(CodeNode codeNode, int conn){
        this.outNodes[conn] = codeNode;
    }

    @Override
    public int dataInputs() {
        return getDataInTypes().length;
    }

    @Override
    public int dataOutputs() {
        return getDataOutTypes().length;
    }

    @Override
    public String getLabelForData(int i, boolean out) {
        return "DATA_" + (out ? "OUT" : "IN") + "_" + i;
    }

    @Override
    public DataType[] getDataInTypes() {
        return new DataType[0];
    }

    @Override
    public DataType[] getDataOutTypes() {
        return new DataType[0];
    }

    @Override
    public String[] directInVal() {
        return this.directInVal;
    }

    @Override
    public String[] inVarName() {
        return this.inVarNames;
    }

    @Override
    public String[] outVarName() {
        return this.outVarNames;
    }

    /**
     * Should only ever be one, unless you want an if-else statement or a switch case
     */
    @Override
    public int getOutConnections() {
        return 1;
    }

    @Override
    public boolean hasInConnection() {
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName().toLowerCase().replace("node", "");
    }
}

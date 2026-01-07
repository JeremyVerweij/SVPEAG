package vcp.walker.nodes;

import vcp.walker.CodeNode;
import vcp.walker.DataType;

public class BaseNode implements CodeNode {
    protected DataType[] dataTypesIn;
    protected CodeNode[] outNodes;
    protected String[] directInVal;
    protected String[] inVarNames;
    protected String outVarName;

    public BaseNode(){
        this.outNodes = new CodeNode[getOutConnections()];

        this.inVarNames = new String[this.dataInputs()];
        this.directInVal = new String[this.dataInputs()];
        this.dataTypesIn = new DataType[this.dataInputs()];

        this.outVarName = null;
    }

    @Override
    public CodeNode[] outNodes() {
        return this.outNodes;
    }

    @Override
    public String getLabelForOut(int i) {
        return "Then ";
    }

    public void addOutNode(CodeNode codeNode, int conn){
        this.outNodes[conn] = codeNode;
    }

    @Override
    public int dataInputs() {
        return 0;
    }

    @Override
    public boolean hasDataOutput() {
        return getDataOutTypes() != null;
    }

    @Override
    public String getLabelForData(int i, boolean out) {
        return "DATA_" + (out ? "OUT" : "IN") + "_" + i;
    }

    @Override
    public DataType[] getDataInTypes() {
        return this.dataTypesIn;
    }

    @Override
    public DataType getDataOutTypes() {
        return null;
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
    public String outVarName() {
        return this.outVarName;
    }

    @Override
    public void setOutVarName(String outVarName) {
        this.outVarName = outVarName;
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
    public String inValue(int i) {
        if (this.inVarNames[i] != null) return this.inVarNames[i];
        return this.directInVal[i];
    }

    @Override
    public String outValue() {
        if (this.outVarName != null) return this.outVarName;
        return "";
    }

    @Override
    public String toString() {
        return getClass().getSimpleName().toLowerCase().replace("node", "");
    }

    @Override
    public boolean canUseSuperTypeClass() {
        return false;
    }
}

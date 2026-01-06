package vcp.walker;

public interface CodeNode {
    int getOutConnections();
    boolean hasInConnection();

    CodeNode[] outNodes();
    CodeNode inNode();

    String getLabelForOut(int i);

    int dataInputs();
    int dataOutputs();

    String getLabelForData(int i, boolean out);

    DataType[] getDataInTypes();
    DataType[] getDataOutTypes();

    String[] directInVal();
    String[] inVarName();
    String[] outVarName();
}

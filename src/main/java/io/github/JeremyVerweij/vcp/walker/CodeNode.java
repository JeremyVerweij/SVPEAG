package io.github.JeremyVerweij.vcp.walker;

public interface CodeNode {
    int getOutConnections();
    boolean hasInConnection();

    CodeNode[] outNodes();

    String getLabelForOut(int i);

    int dataInputs();
    boolean hasDataOutput();

    String getLabelForData(int i, boolean out);

    DataType[] getDataInTypes();
    DataType getDataOutTypes();

    String[] directInVal();
    String[] inVarName();
    String outVarName();
    void setOutVarName(String name);

    String inValue(int i);
    String outValue();

    boolean canUseSuperTypeClass();
}

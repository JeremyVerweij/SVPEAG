package io.github.jeremyverweij.vcp.data.node;

import io.github.jeremyverweij.vcp.data.DataType;

public class BaseNode implements CodeNode {
    private final boolean canUseSuperTypes;
    private final int outConnections;
    private final boolean inConnection;
    private final DataType[] ioDataTypes;
    private final String name;

    public BaseNode(String name, boolean canUseSuperTypes, int outConnections, boolean inConnection, DataType[] ioDataTypes){
        this.name = name;
        this.canUseSuperTypes = canUseSuperTypes;
        this.outConnections = outConnections;
        this.inConnection = inConnection;
        this.ioDataTypes = ioDataTypes;
    }

    @Override
    public boolean canUseSuperTypeClass() {
        return this.canUseSuperTypes;
    }

    @Override
    public int getOutConnections() {
        return this.outConnections;
    }

    @Override
    public boolean hasInConnection() {
        return this.inConnection;
    }

    @Override
    public int dataIOAmount() {
        return this.ioDataTypes.length;
    }

    @Override
    public boolean canUseDirectValuesOrConstantsOnIO(int ioIndex) {
        return false;
    }

    @Override
    public DataType getDataTypeForIO(int ioIndex) {
        return this.ioDataTypes[ioIndex];
    }

    @Override
    public String getNameForIO(int ioIndex) {
        return "io_" + ioIndex;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

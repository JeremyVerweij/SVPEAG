package vcp.walker.nodes;

import vcp.walker.DataType;

public class PrintNode extends BaseNode{
    private static final DataType[] dataIn = new DataType[]{new DataType.StringType()};

    @Override
    public DataType[] getDataInTypes() {
        return dataIn;
    }
}

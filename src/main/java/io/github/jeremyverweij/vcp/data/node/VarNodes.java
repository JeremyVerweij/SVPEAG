package io.github.jeremyverweij.vcp.data.node;

import io.github.jeremyverweij.vcp.VcpApp;
import io.github.jeremyverweij.vcp.data.DataType;

public class VarNodes {
    private static abstract class VarNode extends BaseNode{
        public VarNode(String name, DataType type) {
            super(name, false, 1, true, new DataType[]{type, type});
        }
    }

    public static class IntNode extends VarNode{
        public IntNode(VcpApp app) {
            super("Set Integer", app.getDataTypeRegistry().get("int"));
        }
    }
}

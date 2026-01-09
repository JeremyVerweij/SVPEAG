package io.github.jeremyverweij.vcp.walker.nodes;

import io.github.jeremyverweij.vcp.walker.DataType;

public class VarNodes {
    private static abstract class VarNode extends BaseNode{
        private final DataType dataOut;

        public VarNode(){
            super();
            this.dataTypesIn = new DataType[]{new DataType.NumberType()};
            this.dataOut = getType();
        }

        @Override
        public int dataInputs() {
            return 1;
        }

        public DataType getDataOutTypes() {
            return dataOut;
        }

        protected abstract DataType getType();
    }

    public static class Set_FloatNode extends VarNode{
        @Override
        protected DataType getType() {
            return new DataType.FloatType();
        }
    }

    public static class Set_IntNode extends VarNode{
        @Override
        protected DataType getType() {
            return new DataType.IntType();
        }
    }
}

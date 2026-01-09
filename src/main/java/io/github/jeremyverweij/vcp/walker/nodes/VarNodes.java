package io.github.jeremyverweij.vcp.walker.nodes;

import io.github.jeremyverweij.vcp.walker.DataType;

public class VarNodes {
    private static abstract class VarNode extends BaseNode{
        private final DataType dataOut;

        public VarNode(){
            super();
            this.dataTypesIn = new DataType[]{getType()};
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

    public static class Set_ByteNode extends VarNode{
        @Override
        protected DataType getType() {
            return new DataType.ByteType();
        }
    }

    public static class Set_ShortNode extends VarNode{
        @Override
        protected DataType getType() {
            return new DataType.ShortType();
        }
    }

    public static class Set_IntNode extends VarNode{
        @Override
        protected DataType getType() {
            return new DataType.IntType();
        }
    }

    public static class Set_LongNode extends VarNode{
        @Override
        protected DataType getType() {
            return new DataType.LongType();
        }
    }

    public static class Set_FloatNode extends VarNode{
        @Override
        protected DataType getType() {
            return new DataType.FloatType();
        }
    }

    public static class Set_DoubleNode extends VarNode{
        @Override
        protected DataType getType() {
            return new DataType.DoubleType();
        }
    }

    public static class Set_BooleanNode extends VarNode{
        @Override
        protected DataType getType() {
            return new DataType.BooleanType();
        }
    }

    public static class Set_CharNode extends VarNode{
        @Override
        protected DataType getType() {
            return new DataType.CharType();
        }
    }

    public static class Set_StringNode extends VarNode{
        @Override
        protected DataType getType() {
            return new DataType.StringType();
        }
    }
}

package io.github.jeremy.vcp.walker.nodes;

import io.github.jeremy.vcp.walker.DataType;

public class MathNodes {
    private static class MathNode extends BaseNode{
        private final DataType dataOut = new DataType.NumberType();

        public MathNode(){
            super();
            this.dataTypesIn = new DataType[]{new DataType.NumberType(), new DataType.NumberType()};
        }

        @Override
        public DataType getDataOutTypes() {
            return dataOut;
        }

        @Override
        public int dataInputs() {
            return 2;
        }

        @Override
        public boolean canUseSuperTypeClass() {
            return true;
        }
    }

    public static class AddNode extends MathNode{}
    public static class MinusNode extends MathNode{}
    public static class MultiplyNode extends MathNode{}
    public static class DivideNode extends MathNode{}
}

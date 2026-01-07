package vcp.walker.nodes;

import vcp.walker.DataType;

public class MathNodes {
    private static class MathNode extends BaseNode{
        private final DataType dataOut = mathOp();

        public MathNode(){
            super();
            this.dataTypesIn = new DataType[]{mathOp(), mathOp()};
        }

        @Override
        public DataType getDataOutTypes() {
            return dataOut;
        }

        @Override
        public int dataInputs() {
            return 2;
        }

        protected DataType mathOp(){
            return new DataType.IntType();
        }
    }

    private static class FloatMathNode extends MathNode{
        protected DataType mathOp(){
            return new DataType.FloatType();
        }
    }

    public static class AddNode_Int extends MathNode{}
    public static class MinusNode_Int extends MathNode{}
    public static class MultiplyNode_Int extends MathNode{}
    public static class DivideNode_Int extends MathNode{}

    public static class AddNode_Float extends FloatMathNode{}
    public static class MinusNode_Float extends FloatMathNode{}
    public static class MultiplyNode_Float extends FloatMathNode{}
    public static class DivideNode_Float extends FloatMathNode{}
}

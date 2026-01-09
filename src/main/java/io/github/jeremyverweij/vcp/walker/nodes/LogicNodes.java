package io.github.jeremyverweij.vcp.walker.nodes;

import io.github.jeremyverweij.vcp.walker.DataType;

public class LogicNodes {
    private static abstract class LogicNode extends BaseNode{
        private final DataType dataOut = new DataType.BooleanType();

        public LogicNode(){
            super();
            this.dataTypesIn = new DataType[inAmount()];

            for (int i = 0; i < inAmount(); i++) {
                this.dataTypesIn[i] = new DataType.BooleanType();
            }
        }

        public abstract int inAmount();

        public int dataInputs() {
            return inAmount();
        }

        @Override
        public boolean hasDataOutput() {
            return super.hasDataOutput();
        }

        @Override
        public DataType getDataOutTypes() {
            return dataOut;
        }
    }
    private static class NumberComparisonNode extends BaseNode{
        private final DataType dataOut = new DataType.BooleanType();

        public NumberComparisonNode(){
            super();
            this.dataTypesIn = new DataType[]{new DataType.NumberType()};
        }

        public int dataInputs() {
            return 2;
        }

        @Override
        public boolean hasDataOutput() {
            return super.hasDataOutput();
        }

        @Override
        public DataType getDataOutTypes() {
            return dataOut;
        }

        @Override
        public boolean canUseSuperTypeClass() {
            return true;
        }
    }

    public static class AndNode extends LogicNode{
        @Override
        public int inAmount() {
            return 2;
        }
    }

    public static class OrNode extends LogicNode{
        @Override
        public int inAmount() {
            return 2;
        }
    }

    public static class XorNode extends LogicNode{
        @Override
        public int inAmount() {
            return 2;
        }
    }

    public static class XnorNode extends LogicNode{
        @Override
        public int inAmount() {
            return 2;
        }
    }

    public static class NorNode extends LogicNode{
        @Override
        public int inAmount() {
            return 2;
        }
    }

    public static class NandNode extends LogicNode{
        @Override
        public int inAmount() {
            return 2;
        }
    }

    public static class NotNode extends LogicNode{
        @Override
        public int inAmount() {
            return 1;
        }
    }

    public static class EqualsNode extends BaseNode{
        private final DataType dataOut = new DataType.BooleanType();

        public EqualsNode(){
            super();
            this.dataTypesIn = new DataType[]{new DataType()};
        }

        public int dataInputs() {
            return 2;
        }

        @Override
        public boolean hasDataOutput() {
            return super.hasDataOutput();
        }

        @Override
        public DataType getDataOutTypes() {
            return dataOut;
        }

        @Override
        public boolean canUseSuperTypeClass() {
            return true;
        }
    }

    public static class Not_EqualsNode extends EqualsNode{}

    public static class Greater_ThanNode extends NumberComparisonNode{}

    public static class Greater_Than_EqualsNode extends NumberComparisonNode{}

    public static class Smaller_ThanNode extends NumberComparisonNode{}

    public static class Smaller_Than_EqualsNode extends NumberComparisonNode{}
}

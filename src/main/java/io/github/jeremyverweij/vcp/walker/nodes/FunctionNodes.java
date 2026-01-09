package io.github.jeremyverweij.vcp.walker.nodes;

import io.github.jeremyverweij.vcp.walker.DataType;

public class FunctionNodes {
    public static class MainNode extends BaseNode {
        @Override
        public boolean hasInConnection() {
            return false;
        }
    }

    public static class FunctionNode extends BaseNode {
        public FunctionNode() {
            super();
            this.dataTypesIn = new DataType[]{new DataType.StringType()};
        }

        @Override
        public int dataInputs() {
            return 1;
        }

        @Override
        public boolean hasInConnection() {
            return false;
        }

        @Override
        public String getLabelForData(int i, boolean out) {
            return "NAME";
        }
    }

    public static class ReturnNode extends BaseNode {
        public ReturnNode(){
            super();
            this.dataTypesIn = new DataType[]{new DataType()};
        }

        @Override
        public int dataInputs() {
            return 1;
        }

        @Override
        public int getOutConnections() {
            return 0;
        }

        public boolean canUseSuperTypeClass() {
            return true;
        }
    }

    public static class Call_FunctionNode extends BaseNode {
        public Call_FunctionNode() {
            super();
            this.dataTypesIn = new DataType[]{new DataType.StringType()};
        }

        @Override
        public int dataInputs() {
            return 1;
        }
    }

    public static class Set_ParamNode extends BaseNode {
        public Set_ParamNode() {
            super();
            this.dataTypesIn = new DataType[]{new DataType.StringType(), new DataType()};
        }

        @Override
        public int dataInputs() {
            return 2;
        }

        public boolean canUseSuperTypeClass() {
            return true;
        }
    }

    public static class Get_ParamNode extends BaseNode {
        private final DataType dataOut = new DataType();

        public Get_ParamNode() {
            super();
            this.dataTypesIn = new DataType[]{new DataType.StringType()};
        }

        @Override
        public int dataInputs() {
            return 1;
        }

        @Override
        public boolean hasDataOutput() {
            return true;
        }

        @Override
        public DataType getDataOutTypes() {
            return dataOut;
        }

        public boolean canUseSuperTypeClass() {
            return true;
        }
    }

}

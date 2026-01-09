package io.github.jeremyverweij.vcp.walker.nodes;

import io.github.jeremyverweij.vcp.walker.DataType;

public class ConditionalNotes {
    public static class IfNode extends BaseNode{
        public IfNode(){
            super();
            this.dataTypesIn = new DataType[]{new DataType.BooleanType()};
        }

        public int dataInputs() {
            return 1;
        }

        @Override
        public int getOutConnections() {
            return 2;
        }

        @Override
        public String getLabelForOut(int i) {
            if (i == 1)
                return "IF";
            return super.getLabelForOut(i);
        }
    }

    public static class If_ElseNode extends BaseNode{
        public If_ElseNode(){
            super();
            this.dataTypesIn = new DataType[]{new DataType.BooleanType()};
        }

        public int dataInputs() {
            return 1;
        }

        @Override
        public int getOutConnections() {
            return 3;
        }

        @Override
        public String getLabelForOut(int i) {
            if (i == 1)
                return "IF";
            if (i == 2)
                return "ELSE";
            return super.getLabelForOut(i);
        }
    }

    public static class WhileNode extends BaseNode{
        public WhileNode(){
            super();
            this.dataTypesIn = new DataType[]{new DataType.BooleanType()};
        }

        public int dataInputs() {
            return 1;
        }

        @Override
        public int getOutConnections() {
            return 2;
        }

        @Override
        public String getLabelForOut(int i) {
            if (i == 1)
                return "WHILE";
            return super.getLabelForOut(i);
        }
    }

    public static class ForNode extends BaseNode{
        public ForNode(){
            super();
            this.dataTypesIn = new DataType[]{new DataType.IntLikeType()};
        }

        public int dataInputs() {
            return 1;
        }

        @Override
        public int getOutConnections() {
            return 2;
        }

        @Override
        public String getLabelForOut(int i) {
            if (i == 1)
                return "FOR";
            return super.getLabelForOut(i);
        }

        @Override
        public boolean canUseSuperTypeClass() {
            return true;
        }
    }
}

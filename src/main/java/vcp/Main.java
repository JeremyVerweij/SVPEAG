package vcp;

import vcp.walker.CodeNode;
import vcp.walker.nodes.MathNodes;
import vcp.walker.nodes.RunNode;

import java.awt.*;
import java.util.Objects;

public class Main {
    public static App app;

    public static void main(String[] args) {
        app = new App("vcp", App.NO_OPTIONS);
        app.setCodeWalker(Main::walkNode);

        app.addNode(RunNode.class, Color.GREEN);
        app.addNode(MathNodes.AddNode_Int.class, Color.CYAN);
        app.addNode(MathNodes.MinusNode_Int.class, Color.CYAN);
        app.addNode(MathNodes.MultiplyNode_Int.class, Color.CYAN);
        app.addNode(MathNodes.DivideNode_Int.class, Color.CYAN);
        app.addNode(MathNodes.AddNode_Float.class, Color.CYAN);
        app.addNode(MathNodes.MinusNode_Float.class, Color.CYAN);
        app.addNode(MathNodes.MultiplyNode_Float.class, Color.CYAN);
        app.addNode(MathNodes.DivideNode_Float.class, Color.CYAN);

        app.addCategory(Color.GREEN, "Start");
        app.addCategory(Color.CYAN, "Math");

        app.start();
    }

    private static void runOutNodeIfPresent(StringBuilder output, int indent, CodeNode node, int out){
        if (node.outNodes()[out] != null){
            walkNode(output, indent, node.outNodes()[out]);
        }
    }

    private static void walkNode(StringBuilder output, int indent, CodeNode node){
        if (Objects.requireNonNull(node) instanceof RunNode) {
            output.repeat("\t", indent).append("void main(){\n");
            runOutNodeIfPresent(output, indent + 1, node, 0);
            output.repeat("\t", indent).append("}");
        } else if (node instanceof MathNodes.AddNode_Int || node instanceof MathNodes.AddNode_Float) {
            output.repeat("\t", indent)
                    .append(node.outValue())
                    .append(!Objects.equals(node.outValue(), "") ? "=" : "")
                    .append(node.inValue(0))
                    .append("+")
                    .append(node.inValue(1))
                    .append(";\n");
            runOutNodeIfPresent(output, indent, node, 0);
        } else {
            output.repeat("\t", indent).append(node).append("();\n");
            runOutNodeIfPresent(output, indent, node, 0);
        }
    }
}
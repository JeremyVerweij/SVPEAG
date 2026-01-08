package vcp;

import vcp.walker.CodeNode;
import vcp.walker.LoadAndSaveState;
import vcp.walker.nodes.MathNodes;
import vcp.walker.nodes.RunNode;
import vcp.walker.nodes.VarNodes;

import java.awt.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Main {
    public static App app;

    public static void main(String[] args) {
        app = new App("vcp", App.NO_OPTIONS);
        app.setCodeWalker(Main::walkNode);

        app.addNode(RunNode.class, Color.GREEN);
        app.addNode(MathNodes.AddNode.class, Color.CYAN);
        app.addNode(MathNodes.MinusNode.class, Color.CYAN);
        app.addNode(MathNodes.MultiplyNode.class, Color.CYAN);
        app.addNode(MathNodes.DivideNode.class, Color.CYAN);
        app.addNode(VarNodes.Set_IntNode.class, Color.ORANGE);
        app.addNode(VarNodes.Set_FloatNode.class, Color.ORANGE);


        app.addCategory(Color.GREEN, "Start");
        app.addCategory(Color.CYAN, "Math");
        app.addCategory(Color.ORANGE, "Variables");

        app.start();
    }

    private static void runOutNodeIfPresent(StringBuilder output, int indent, CodeNode node, Set<String> definedVars, int out){
        if (node.outNodes()[out] != null){
            walkNode(output, indent, node.outNodes()[out], definedVars);
        }
    }

    private static String addOptionalEqualsWithCast(CodeNode node){
        return !Objects.equals(node.outValue(), "") ? " = (" + node.getDataOutTypes().toString() + ") " : "";
    }

    private static void walkNode(StringBuilder output, int indent, CodeNode node, Set<String> definedVars){
        switch (node) {
            case RunNode ignore -> {
                output.repeat("\t", indent).append("void main(){\n");
                runOutNodeIfPresent(output, indent + 1, node, definedVars, 0);
                output.repeat("\t", indent).append("}");
            }
            case MathNodes.AddNode ignore -> {
                output.repeat("\t", indent)
                        .append(node.outValue())
                        .append(addOptionalEqualsWithCast(node))
                        .append(node.inValue(0))
                        .append(" + ")
                        .append(node.inValue(1))
                        .append(";\n");
                runOutNodeIfPresent(output, indent, node, definedVars, 0);
            }
            case MathNodes.MinusNode ignore -> {
                output.repeat("\t", indent)
                        .append(node.outValue())
                        .append(addOptionalEqualsWithCast(node))
                        .append(node.inValue(0))
                        .append(" - ")
                        .append(node.inValue(1))
                        .append(";\n");
                runOutNodeIfPresent(output, indent, node, definedVars, 0);
            }
            case MathNodes.MultiplyNode ignore -> {
                output.repeat("\t", indent)
                        .append(node.outValue())
                        .append(addOptionalEqualsWithCast(node))
                        .append(node.inValue(0))
                        .append(" * ")
                        .append(node.inValue(1))
                        .append(";\n");
                runOutNodeIfPresent(output, indent, node, definedVars, 0);
            }
            case MathNodes.DivideNode ignore -> {
                output.repeat("\t", indent)
                        .append(node.outValue())
                        .append(addOptionalEqualsWithCast(node))
                        .append(node.inValue(0))
                        .append(" / ")
                        .append(node.inValue(1))
                        .append(";\n");
                runOutNodeIfPresent(output, indent, node, definedVars, 0);
            }
            case VarNodes.Set_FloatNode ignore -> {
                output.repeat("\t", indent)
                        .append(definedVars.contains(node.outValue()) ? "" : "float ")
                        .append(node.outValue())
                        .append(!Objects.equals(node.outValue(), "") ? " = (float) " : "")
                        .append(node.inValue(0))
                        .append(";\n");
                definedVars.add(node.outValue());
                runOutNodeIfPresent(output, indent, node, definedVars, 0);
            }
            case VarNodes.Set_IntNode ignore -> {
                output.repeat("\t", indent)
                        .append(definedVars.contains(node.outValue()) ? "" : "int ")
                        .append(node.outValue())
                        .append(!Objects.equals(node.outValue(), "") ? " = (int) " : "")
                        .append(node.inValue(0))
                        .append(";\n");
                definedVars.add(node.outValue());
                runOutNodeIfPresent(output, indent, node, definedVars, 0);
            }
            case null -> {}
            default -> {
                output.repeat("\t", indent).append(node).append("();\n");
                runOutNodeIfPresent(output, indent, node, definedVars, 0);
            }
        }
    }
}
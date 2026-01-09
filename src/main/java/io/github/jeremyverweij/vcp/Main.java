package io.github.jeremyverweij.vcp;

import io.github.jeremyverweij.vcp.walker.CodeNode;
import io.github.jeremyverweij.vcp.walker.nodes.MathNodes;
import io.github.jeremyverweij.vcp.walker.nodes.RunNode;
import io.github.jeremyverweij.vcp.walker.nodes.VarNodes;

import java.awt.*;
import java.util.Objects;
import java.util.Set;

public class Main {
    public static App app;

    public static void main(String[] args) {
        app = new App("io/github/jeremyverweij/vcp");
        app.setCodeWalker(Main::walkNode);

        app.addDefaultNodes();

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
package vcp.walker;

import vcp.walker.nodes.PrintNode;
import vcp.walker.nodes.RunNode;

public class NodeTree {
    public void eval(CodeNode codeNode){
        StringBuilder builder = new StringBuilder();

        CodeNode current = codeNode;
        int indent = 0;
        while (current.outNodes()[0] != null){
            indent = nodeSpecific(current, builder, indent);
            current = current.outNodes()[0];
            builder.append("\n");
        }

        nodeSpecific(current, builder, indent);

        System.out.println(builder);
    }

    private int nodeSpecific(CodeNode codeNode, StringBuilder builder, int indent){
        builder.repeat("\t", indent);

        return switch (codeNode) {
            case RunNode ignore -> {
                builder.append("run() ->");
                yield indent + 1;
            }
            case PrintNode ignore -> {
                builder.append("print(");

                if (codeNode.inVarName()[0] != null){
                    builder.append(codeNode.inVarName()[0]);
                }else{
                    builder.append("\"");
                    builder.append(codeNode.directInVal()[0]);
                    builder.append("\"");
                }

                builder.append(")");
                yield indent;
            }
            default -> indent;
        };
    }
}

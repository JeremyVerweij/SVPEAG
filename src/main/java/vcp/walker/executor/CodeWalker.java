package vcp.walker.executor;

import vcp.App;
import vcp.walker.CodeNode;

import java.util.HashSet;
import java.util.Set;

public class CodeWalker {
    private final INodeEvaluator nodeEvaluator;
    private final App app;

    public CodeWalker(INodeEvaluator nodeEvaluator, App app){
        this.nodeEvaluator = nodeEvaluator;
        this.app = app;
    }

    public String eval(CodeNode codeNode){
        StringBuilder builder = new StringBuilder();

        HashSet<String> set = new HashSet<>();
        set.add("");
        this.nodeEvaluator.eval(builder, 0, codeNode, set);

        return (builder.toString());
    }

    public interface INodeEvaluator{
        /**
         * @param output write generated code to this
         * @param indentation current indent
         * @param node current node
         */
        void eval(StringBuilder output, int indentation, CodeNode node, Set<String> definedVars);
    }
}

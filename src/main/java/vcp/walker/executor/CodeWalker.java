package vcp.walker.executor;

import vcp.App;
import vcp.walker.CodeNode;

public class CodeWalker {
    private final INodeEvaluator nodeEvaluator;
    private final App app;

    public CodeWalker(INodeEvaluator nodeEvaluator, App app){
        this.nodeEvaluator = nodeEvaluator;
        this.app = app;
    }

    public void eval(CodeNode codeNode){
        StringBuilder builder = new StringBuilder();

        this.nodeEvaluator.eval(builder, 0, codeNode);

        System.out.println(builder.toString());
    }

    public interface INodeEvaluator{
        /**
         * @param output write generated code to this
         * @param indentation current indent
         * @param node current node
         */
        void eval(StringBuilder output, int indentation, CodeNode node);
    }
}

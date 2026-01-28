package io.github.jeremyverweij.vcp.walker.executor;

import io.github.jeremyverweij.vcp.VcpApp;
import io.github.jeremyverweij.vcp.walker.CodeNode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CodeWalker {
    private final INodeEvaluator nodeEvaluator;
    private final INodePostProcessor nodePostProcessor;
    private final VcpApp vcpApp;

    public CodeWalker(INodeEvaluator nodeEvaluator, INodePostProcessor nodePostProcessor, VcpApp vcpApp){
        this.nodeEvaluator = nodeEvaluator;
        this.nodePostProcessor = nodePostProcessor;
        this.vcpApp = vcpApp;
    }

    public String eval(CodeNode codeNode){
        StringBuilder builder = new StringBuilder();

        HashSet<String> set = new HashSet<>();
        set.add("");
        this.nodeEvaluator.eval(builder, 0, codeNode, set);

        return nodePostProcessor.process(builder.toString(), vcpApp.getConstants());
    }

    public interface INodeEvaluator{
        /**
         * @param output write generated code to this
         * @param indentation current indent
         * @param node current node
         */
        void eval(StringBuilder output, int indentation, CodeNode node, Set<String> definedVars);
    }

    /*
    * This is handy to use when setting CONSTANTS to their correct value
     */
    public interface INodePostProcessor{
        String process(String in, List<String> constants);
    }
}

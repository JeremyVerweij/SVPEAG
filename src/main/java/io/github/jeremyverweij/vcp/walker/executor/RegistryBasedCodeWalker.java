package io.github.jeremyverweij.vcp.walker.executor;

import io.github.jeremyverweij.vcp.walker.CodeNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class RegistryBasedCodeWalker implements CodeWalker.INodeEvaluator{
    private final Map<Class<? extends CodeNode>, CodeWalker.INodeEvaluator> perNodeEvaluator;

    public RegistryBasedCodeWalker(){
        this.perNodeEvaluator = new HashMap<>();
    }

    public void register(Class<? extends CodeNode> node, CodeWalker.INodeEvaluator evaluator){
        this.perNodeEvaluator.put(node, evaluator);
    }

    @Override
    public void eval(StringBuilder output, int indentation, CodeNode node, Set<String> definedVars) {
        this.perNodeEvaluator.get(node.getClass()).eval(output, indentation, node, definedVars);
    }

    public static void runOutNodeIfPresent(RegistryBasedCodeWalker walker, StringBuilder output, int indent, CodeNode node, Set<String> definedVars, int out){
        if (node.outNodes()[out] != null){
            walker.eval(output, indent, node.outNodes()[out], definedVars);
        }
    }

    public static String addOptionalEqualsWithCast(CodeNode node){
        return !Objects.equals(node.outValue(), "") ? " = (" + node.getDataOutTypes().toString() + ") " : "";
    }
}

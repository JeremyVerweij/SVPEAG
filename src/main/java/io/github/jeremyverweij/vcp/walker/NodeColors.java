package io.github.jeremyverweij.vcp.walker;

import io.github.jeremyverweij.vcp.data.node.CodeNode;

import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NodeColors {
    private final Map<Class<? extends CodeNode>, Color> colorMap;

    public NodeColors(){
        this.colorMap = new HashMap<>();
    }

    public void addColor(Class<? extends CodeNode> codeNodeClass, Color color){
        this.colorMap.put(codeNodeClass, color);
    }

    public Color getColor(Class<? extends CodeNode> codeNodeClass){
        if (this.colorMap.containsKey(codeNodeClass)){
            return this.colorMap.get(codeNodeClass);
        }

        return Color.WHITE;
    }

    public Class<? extends CodeNode>[] getNodesFromColor(Color color){
        //noinspection unchecked
        return this.colorMap
                .keySet()
                .stream()
                .filter((e) -> this.colorMap.get(e).equals(color))
                .sorted(Comparator.comparing(Class::getSimpleName))
                .toArray(Class[]::new);
    }

    public Set<Class<? extends CodeNode>> getAllNodes(){
        return this.colorMap.keySet();
    }
}

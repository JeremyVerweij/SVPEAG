package io.github.jeremyverweij.vcp.data.node;

import java.util.HashMap;
import java.util.Map;

public class NodeRegistry {
    private final Map<String, CodeNode> registry;

    public NodeRegistry(){
        this.registry = new HashMap<>();
    }
}

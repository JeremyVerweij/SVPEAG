package vcp.walker;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class NodeColors {
    private final Map<String, Color> colorMap;

    public NodeColors(){
        this.colorMap = new HashMap<>();
    }

    public void addColor(Class<? extends CodeNode> codeNodeClass, Color color){
        this.colorMap.put(codeNodeClass.getName(), color);
    }

    public Color getColor(Class<? extends CodeNode> codeNodeClass){
        if (this.colorMap.containsKey(codeNodeClass.getName())){
            return this.colorMap.get(codeNodeClass.getName());
        }

        return Color.WHITE;
    }
}

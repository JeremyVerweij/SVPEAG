package vcp.walker;

import vcp.App;
import vcp.components.Component;
import vcp.components.NodeComponent;

import java.util.HashMap;
import java.util.Map;

public class LoadAndSaveState {
    private final App app;

    public LoadAndSaveState(App app) {
        this.app = app;
    }

    public void save(String path){
        StringBuilder builder = new StringBuilder();
        builder.append("[VARS]\n");

        for (Map.Entry<String, DataType> var : app.getVars().entrySet()) {
            builder.append(var.getValue().getClass());
            builder.append("|");
            builder.append(var.getKey());
        }

        builder.append("[COMP]\n");

        Map<CodeNode, Integer> nodeMapper = new HashMap<>();

        int index = 0;
        for (Component allComponent : app.getPlayGround().getAllComponents()) {
            if (allComponent instanceof NodeComponent component){
                builder.append(index);
                builder.append("|");
                builder.append(component.getX());
                builder.append("|");
                builder.append(component.getY());
                builder.append("|");
                builder.append(component.getCodeNode().getClass().getName());
                builder.append("|");

                for (int i = 0; i < component.getCodeNode().inVarName().length; i++) {
                    builder.append(component.getCodeNode().inVarName()[i] == null);
                    builder.append("~");
                    builder.append(component.getCodeNode().inVarName()[i] == null ? component.getCodeNode().directInVal()[i] :
                            component.getCodeNode().inVarName()[i]);
                    builder.append("{}");
                }

                builder.append("|");
                builder.append(component.getCodeNode().outVarName());
                builder.append("\n");

                nodeMapper.put(component.getCodeNode(), index);
                index += 1;
            }
        }

        builder.append("[CONN]\n");
        for (Component allComponent : app.getPlayGround().getAllComponents()) {
            if (allComponent instanceof NodeComponent component){
                int self = nodeMapper.get(component.getCodeNode());

                for (CodeNode codeNode : component.getCodeNode().outNodes()) {
                    if (codeNode == null) continue;

                    int conn = nodeMapper.get(codeNode);
                    builder.append(self);
                    builder.append("|");
                    builder.append(conn);
                    builder.append("\n");
                }
            }
        }

        System.out.println(builder);
    }
}

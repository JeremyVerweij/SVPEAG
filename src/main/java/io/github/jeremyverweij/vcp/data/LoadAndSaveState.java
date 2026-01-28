package io.github.jeremyverweij.vcp.data;

public class LoadAndSaveState {
//    private final VcpApp vcpApp;
//
//    public LoadAndSaveState(VcpApp vcpApp) {
//        this.vcpApp = vcpApp;
//    }
//
//    public void save(String path){
//        StringBuilder builder = new StringBuilder();
//        builder.append("[VARS]\n");
//
//        for (Map.Entry<String, DataType> var : vcpApp.getVars().entrySet()) {
//            builder.append(var.getValue().getClass().getName());
//            builder.append("|");
//            builder.append(var.getKey());
//            builder.append("\n");
//        }
//
//        builder.append("[COMP]\n");
//
//        Map<CodeNode, Integer> nodeMapper = new HashMap<>();
//
//        int index = 0;
//        for (Component allComponent : vcpApp.getPlayGround().getAllComponents()) {
//            if (allComponent instanceof NodeComponent component){
//                builder.append(index);
//                builder.append("|");
//                builder.append(component.getX());
//                builder.append("|");
//                builder.append(component.getY());
//                builder.append("|");
//                builder.append(component.getCodeNode().getClass().getName());
//                builder.append("|");
//
//                for (int i = 0; i < component.getCodeNode().inVarName().length; i++) {
//                    builder.append(component.getCodeNode().inVarName()[i] == null);
//                    builder.append("~");
//                    builder.append(component.getCodeNode().inVarName()[i] == null ? component.getCodeNode().directInVal()[i] :
//                            component.getCodeNode().inVarName()[i]);
//                    builder.append("{}");
//                }
//
//                builder.append("|");
//                if(component.getCodeNode().hasDataOutput()) builder.append(component.getCodeNode().outVarName());
//                builder.append("\n");
//
//                nodeMapper.put(component.getCodeNode(), index);
//                index += 1;
//            }
//        }
//
//        builder.append("[CONN]\n");
//        for (Component allComponent : vcpApp.getPlayGround().getAllComponents()) {
//            if (allComponent instanceof NodeComponent component){
//                int self = nodeMapper.get(component.getCodeNode());
//
//                for (int i = 0; i < component.getCodeNode().outNodes().length; i++) {
//                    CodeNode codeNode = component.getCodeNode().outNodes()[i];
//                    if (codeNode == null) continue;
//
//                    int conn = nodeMapper.get(codeNode);
//                    builder.append(self);
//                    builder.append("|");
//                    builder.append(conn);
//                    builder.append("|");
//                    builder.append(i);
//                    builder.append("\n");
//                }
//            }
//        }
//
//        try {
//            Files.writeString(Path.of(path), builder.toString());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void load(String path){
//        try {
//            String loadFile = Files.readString(Path.of(path));
//
//            String[] slices = loadFile.split("\n");
//
//            List<String> vars = new ArrayList<>();
//            List<String> comps = new ArrayList<>();
//            final List<String> conns = getList(slices, vars, comps);
//
//            for (String var : vars) {
//                String[] splitVar = var.split("\\|");
//                String typeString = splitVar[0];
//                String nameString = splitVar[1];
//
//                DataType dataType = null;
//                for (Class<? extends DataType> allType : DataType.allTypes) {
//                    if (allType.getName().equals(typeString)){
//                        dataType = allType.getConstructor().newInstance();
//                    }
//                }
//
//                if (dataType != null)
//                    vcpApp.createVar(dataType, nameString);
//            }
//
//            NodeComponent[] components = new NodeComponent[comps.size()];
//            for (String comp : comps) {
//                String[] splitComp = comp.split("\\|");
//                String indexString = splitComp[0];
//                String xString = splitComp[1];
//                String yString = splitComp[2];
//                String nodeString = splitComp[3];
//                String inVarsString = splitComp[4];
//                String outVarString = splitComp[5];
//
//                boolean hasIn = !inVarsString.isBlank();
//                boolean hasOut = !outVarString.isBlank();
//
//                int index = Integer.parseInt(indexString);
//                int x = Integer.parseInt(xString);
//                int y = Integer.parseInt(yString);
//
//                Class<? extends CodeNode> nodeClass = null;
//                for (Class<? extends CodeNode> allNode : vcpApp.getNodeColors().getAllNodes()) {
//                    if (allNode.getName().equals(nodeString)){
//                        nodeClass = allNode;
//                        break;
//                    }
//                }
//
//                if (nodeClass == null) throw new NullPointerException();
//
//                CodeNode node = nodeClass.getConstructor().newInstance();
//
//                NodeComponent component = new NodeComponent(this.vcpApp, x, y, node);
//
//                if (hasOut && !outVarString.equals("null")){
//                    component.getDataOutDisplay().setVarName(outVarString);
//                }
//
//                if (hasIn){
//                    String[] inSplitString = inVarsString.split("\\{}");
//                    for (int i = 0; i < inSplitString.length; i++) {
//                        String inVar = inSplitString[i];
//                        String[] inVarSplitString = inVar.split("~");
//                        boolean isDirect = Objects.equals(inVarSplitString[0], "true");
//                        String varName = inVarSplitString[1];
//
//                        if (varName.equals("null")) continue;
//
//                        if (isDirect)
//                            component.getDataInDisplays()[i].setDirectValue(varName);
//                        else
//                            component.getDataInDisplays()[i].setVarName(varName);
//                    }
//                }
//
//                this.vcpApp.getPlayGround().addComponent(component);
//                components[index] = component;
//            }
//
//            for (String conn : conns) {
//                String[] connSplitString = conn.split("\\|");
//                String index1String = connSplitString[0];
//                String index2String = connSplitString[1];
//                String connectionString = connSplitString[2];
//                int index1 = Integer.parseInt(index1String);
//                int index2 = Integer.parseInt(index2String);
//                int connection = Integer.parseInt(connectionString);
//
//                components[index1].getConnectors()[connection].setConnected(components[index2]);
//            }
//        } catch (IOException | InvocationTargetException | InstantiationException | IllegalAccessException |
//                 NoSuchMethodException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static List<String> getList(String[] slices, List<String> vars, List<String> comps) {
//        List<String> conns = new ArrayList<>();
//
//        List<String> current = null;
//        for (String slice : slices) {
//            if (Objects.equals(slice, "[VARS]")){
//                current = vars;
//                continue;
//            } else if (Objects.equals(slice, "[COMP]")){
//                current = comps;
//                continue;
//            } else if (Objects.equals(slice, "[CONN]")){
//                current = conns;
//                continue;
//            }
//
//            if (current != null)
//                current.add(slice);
//        }
//        return conns;
//    }
}

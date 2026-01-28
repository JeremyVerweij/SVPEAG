package io.github.jeremyverweij.vcp.components;

public class ContextMenuComponent{
//    private boolean visible = false;
//    private NodeComponent component = null;
//
//    public ContextMenuComponent(VcpApp vcpApp, int x, int y) {
//        super(vcpApp, x, y, Color.LIGHT_GRAY);
//        this.width = 250;
//        this.height = 100;
//    }
//
//    @Override
//    public void draw(Graphics2D g2) {
//        if(visible && component != null){
//            int inItems = component.codeNode.dataInputs();
//            int outItems = component.codeNode.hasDataOutput() ? 1 : 0;
//
//            height = (inItems + outItems + 3) * 20;
//
//            super.draw(g2);
//
//            int y = this.y + 20;
//
//            g2.setColor(Color.RED);
//            g2.drawString("Delete", x + 2, y - 6);
//            g2.setColor(foreground);
//            g2.drawLine(x, y, x + width, y);
//            y += 20;
//
//            g2.setColor(Color.BLUE);
//            g2.drawString("Data IN", x + 2, y - 6);
//            g2.setColor(foreground);
//            g2.drawLine(x, y, x + width, y);
//            y += 20;
//
//            for (int i = 0; i < inItems; i++) {
//                g2.drawString("Edit '" + component.codeNode.getLabelForData(i, false) + "'", x + 2, y - 6);
//                g2.drawLine(x, y, x + width, y);
//
//                y += 20;
//            }
//
//            g2.setColor(Color.ORANGE);
//            g2.drawString("Data OUT", x + 2, y - 6);
//            g2.setColor(foreground);
//            g2.drawLine(x, y, x + width, y);
//            y += 20;
//
//            if (component.codeNode.hasDataOutput()){
//                g2.drawString("Edit '" + component.codeNode.getLabelForData(0, true) + "'", x + 2, y - 6);
//                g2.drawLine(x, y, x + width, y);
//            }
//
//            y += 20;
//        }
//    }
//
//    @Override
//    public void onClick(MouseEvent event, Point mouse) {
//        int y = mouse.y - this.y;
//        int index = y / 20;
//
//        if (index == 0){
//            if(JOptionPane.showConfirmDialog(this.getPlayground(), "Are you sure you want to delete?") == 0){
//                this.component.delete();
//                this.getPlayground().removeComponent(this.component);
//                hide();
//            }
//        } else if(index ==1 || index == component.codeNode.dataInputs() + 2){
//
//        } else if (index < component.codeNode.dataInputs() + 2){
//            //inputs
//            int i = index - 2;
//
//            Map.Entry<String, Boolean> popup = createPopup(component.getDataInDisplays()[i].getDirectValue(),
//                    component.getDataInDisplays()[i].getVarName(),
//                    component.codeNode.getDataInTypes()[i],
//                    component.codeNode.getLabelForData(i, false),
//                    true,
//                    component.codeNode.canUseSuperTypeClass(),
//                    !component.codeNode.hasInConnection());
//
//            if (popup == null) return;
//
//            if (!popup.getValue()){
//                if (component.getDataInDisplays()[i].getVarName() != null && !Objects.equals(component.getDataInDisplays()[i].getVarName(), popup.getKey())) {
//                    component.removeVar(component.getDataInDisplays()[i].getVarName());
//                }
//
//                component.getDataInDisplays()[i].setVarName(popup.getKey());
//            }else{
//                if (component.getDataInDisplays()[i].getVarName() != null) {
//                    component.removeVar(component.getDataInDisplays()[i].getVarName());
//                }
//
//                component.getDataInDisplays()[i].setVarName(null);
//                component.getDataInDisplays()[i].setDirectValue(popup.getKey());
//            }
//        } else if (component.codeNode.hasDataOutput()){
//            //output
//            Map.Entry<String, Boolean> popup = createPopup(null,
//                    component.getDataOutDisplay().getVarName(),
//                    component.codeNode.getDataOutTypes(),
//                    component.codeNode.getLabelForData(0 , true),
//                    false,
//                    component.codeNode.canUseSuperTypeClass(),
//                    !component.codeNode.hasInConnection());
//
//            if (popup == null) return;
//
//            if (component.getDataOutDisplay().getVarName() != null && !Objects.equals(component.getDataOutDisplay().getVarName(), popup.getKey())) {
//                component.removeVar(component.getDataOutDisplay().getVarName());
//            }
//
//            component.getDataOutDisplay().setVarName(popup.getKey());
//        }
//    }
//
//    private Map.Entry<String, Boolean> createPopup(String initialValue, String varName, DataType dataType, String label, boolean allowDirect, boolean allowSuperTypes, boolean isStartingPoint){
//        JOptionPane pane = new JOptionPane();
//
//        pane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
//
//        if (allowDirect){
//            pane.setWantsInput(true);
//            pane.setInitialSelectionValue(initialValue);
//        }
//
//        JComboBox<String> comboBox = new JComboBox<>(vcpApp.getVarsForType(dataType, allowSuperTypes, allowDirect));
//        if(varName != null) if(vcpApp.existVar(varName)) comboBox.setSelectedItem(varName);
//        JCheckBox checkBox = new JCheckBox("Use var instead of direct value: ", varName != null);
//
//        if(!isStartingPoint) pane.add(comboBox, 1);
//
//        if (allowDirect && !isStartingPoint)
//            pane.add(checkBox, 2);
//
//        JDialog dialog = pane.createDialog(label);
//        dialog.show();
//        dialog.dispose();
//
//        boolean direct = allowDirect && !checkBox.isSelected();
//        String rString = (String) (direct ? pane.getInputValue() : comboBox.getSelectedItem());
//
//        Object selectedValue = pane.getValue();
//
//        if(selectedValue == null)
//            return null;
//
//        if(selectedValue instanceof Integer){
//            int i = (Integer) selectedValue;
//            if (i != 0) return null;
//        }else return null;
//
//        return new AbstractMap.SimpleEntry<>(rString, direct);
//    }
//
//    @Override
//    public void onDrag(int dx, int dy) {
//
//    }
//
//    public void show(NodeComponent nodeComponent){
//        this.visible = true;
//        this.x = getPlayground().getMousePos().x;
//        this.y = getPlayground().getMousePos().y;
//
//        this.component = nodeComponent;
//
//        repaint();
//    }
//
//    public void hide(){
//        this.visible = false;
//        this.component = null;
//
//        repaint();
//    }
//
//    public boolean isVisible() {
//        return visible;
//    }
}

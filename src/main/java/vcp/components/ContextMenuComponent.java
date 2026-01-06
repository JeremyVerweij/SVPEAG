package vcp.components;

import vcp.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ContextMenuComponent extends Component{
    private boolean visible = false;
    private NodeComponent component = null;

    public ContextMenuComponent(App app, int x, int y) {
        super(app, x, y, Color.LIGHT_GRAY);
        this.width = 250;
        this.height = 100;
    }

    @Override
    public void draw(Graphics2D g2) {
        if(visible && component != null){
            int inItems = component.codeNode.dataInputs();
            int outItems = component.codeNode.dataOutputs();

            height = (inItems + outItems + 2) * 20;

            super.draw(g2);

            int y = this.y + 20;

            g2.setColor(Color.BLUE);
            g2.drawString("Data IN", x + 2, y - 6);
            g2.setColor(foreground);
            g2.drawLine(x, y, x + width, y);
            y += 20;

            for (int i = 0; i < inItems; i++) {
                g2.drawString("Edit '" + component.codeNode.getLabelForData(i, false) + "'", x + 2, y - 6);
                g2.drawLine(x, y, x + width, y);

                y += 20;
            }

            g2.setColor(Color.ORANGE);
            g2.drawString("Data OUT", x + 2, y - 6);
            g2.setColor(foreground);
            g2.drawLine(x, y, x + width, y);
            y += 20;

        }
    }

    @Override
    public void onClick(MouseEvent event, Point mouse) {
        int y = mouse.y - this.y;
        int index = y / 20;

        if (index == 0 || index == component.codeNode.dataInputs() + 1)
            return;

        if (index <= component.codeNode.dataInputs()){
            //inputs
            int i = index - 1;

            JOptionPane pane = new JOptionPane();

            String var = component.getDataInDisplays()[i].getVarName();
            pane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
            pane.setWantsInput(true);
            pane.setInitialSelectionValue(component.getDataInDisplays()[i].getDirectValue());

            JComboBox<Object> comboBox = new JComboBox<>(app.getVarsForType(component.codeNode.getDataInTypes()[i]).toArray());
            if(var != null) if(app.existVar(component.codeNode.getDataInTypes()[i], var)) comboBox.setSelectedItem(var);
            JCheckBox checkBox = new JCheckBox("Use var instead of direct value: ", var != null);

            pane.add(comboBox, 1);
            pane.add(checkBox, 2);

            pane.createDialog(component.codeNode.getLabelForData(i, false)).setVisible(true);

            if (checkBox.isSelected()){
                component.getDataInDisplays()[i].setVarName((String) comboBox.getSelectedItem());
            }else{
                component.getDataInDisplays()[i].setVarName(null);
                component.getDataInDisplays()[i].setDirectValue((String) pane.getInputValue());
            }
        }else{
            //outputs
            int i = index - 2 - component.codeNode.dataInputs();

        }
    }

    @Override
    public void onDrag(int dx, int dy) {

    }

    public void show(NodeComponent nodeComponent){
        this.visible = true;
        this.x = getPlayground().getMousePos().x;
        this.y = getPlayground().getMousePos().y;

        this.component = nodeComponent;

        repaint();
    }

    public void hide(){
        this.visible = false;
        this.component = null;

        repaint();
    }

    public boolean isVisible() {
        return visible;
    }
}

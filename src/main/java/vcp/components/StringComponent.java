package vcp.components;

import vcp.App;
import vcp.Value;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class StringComponent extends Component implements OutPuttingComponent<String>{
    private String value = "Hello World!";

    public StringComponent(App app, int x, int y) {
        super(app, x, y, Color.ORANGE);
    }

    @Override
    public int getConnectionsInAmount() {
        return 1;
    }

    @Override
    public boolean run() {
        return false;
    }

    @Override
    public String getDrawString() {
        return value;
    }

    @Override
    public Value<String> getOutValue() {
        if (!connectionsIn[0].state()) return null;

        return new Value<>(value);
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public void onClick(MouseEvent event) {
        String input = JOptionPane.showInputDialog("Set the String(Current: '" + value + "')");

        if (input != null){
            this.value = input;
            this.repaint();
        }
    }
}

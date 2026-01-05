package vcp.components;

import vcp.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class WaitComponent extends Component{
    private float waitSeconds = 3f;

    public WaitComponent(App app, int x, int y) {
        super(app, x, y, 100, 100, Color.BLACK, Color.YELLOW);
    }

    @Override
    public int getConnectionsInAmount() {
        return 1;
    }

    @Override
    public void runSelf() {
        app.getPlayGround().addInvokeLater(super::runSelf, (long) (1000 * this.waitSeconds));
    }

    @Override
    public boolean run() {
        return true;
    }

    @Override
    public String getDrawString() {
        return "Wait(" + this.waitSeconds + "s)";
    }

    @Override
    public void onClick(MouseEvent event) {
        String input = JOptionPane.showInputDialog("Set the wait time(Current: " + waitSeconds + " s)");

        if (input != null){
            try {
                this.waitSeconds = Float.parseFloat(input);
                this.repaint();
            }catch (Exception ignored){}
        }
    }
}

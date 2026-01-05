package vcp.components;

import vcp.App;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ClickComponent extends Component{
    public ClickComponent(App app, int x, int y) {
        super(app, x, y, Color.GREEN);
    }

    @Override
    public int getConnectionsInAmount() {
        return 0;
    }

    @Override
    public boolean run() {
        return true;
    }

    @Override
    public String getDrawString() {
        return "Run on Click";
    }

    @Override
    public void onClick(MouseEvent event) {
        app.getPlayGround().resetStates();
        this.runSelf();
        app.getPlayGround().repaint();
    }
}

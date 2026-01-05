package vcp.components;

import vcp.App;

import java.awt.*;

public class AndComponent extends Component{
    public AndComponent(App app, int x, int y) {
        super(app, x, y, Color.CYAN);
    }

    @Override
    public int getConnectionsInAmount() {
        return 2;
    }

    @Override
    public String getDrawString() {
        return "&";
    }

    @Override
    public boolean run() {
        if (this.connectionsIn[1] == null || this.connectionsIn[0] == null) return false;

        return this.connectionsIn[0].state() && this.connectionsIn[1].state();
    }
}

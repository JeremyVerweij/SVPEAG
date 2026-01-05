package vcp.components;

import vcp.App;

import java.awt.*;

public class PrintValueComponent extends Component{
    public PrintValueComponent(App app, int x, int y) {
        super(app, x, y, 100, 100, Color.BLACK, Color.RED);
    }

    @Override
    public int getConnectionsInAmount() {
        return 2;
    }

    @Override
    public String getDrawString() {
        return "Print Value";
    }

    @Override
    public String getCharForInput(int input) {
        if (input == 1){
            return "S";
        }

        return super.getCharForInput(input);
    }

    @Override
    public boolean acceptInput(Component component, int connection) {
        if (connection == 0) return true;

        if (component instanceof OutPuttingComponent<?> component1){
            return component1.getType() == String.class;
        }

        return false;
    }

    @Override
    public boolean run() {
        if (this.connectionsIn[1] == null || this.connectionsIn[0] == null) return false;
        if (!this.connectionsIn[0].state()) return false;

        if (this.connectionsIn[1].value() != null){
            this.app.getCommandLineWindow().log((String) this.connectionsIn[1].value().getValue());

            return true;
        }

        return false;
    }
}

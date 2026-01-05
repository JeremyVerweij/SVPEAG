package vcp.components;

import vcp.Value;

public class Connection {
    private final Component component;
    private final int connection;
    private boolean state;
    private Value<?> value;

    public Connection(Component component, int connection){
        this.component = component;
        this.connection = connection;
        this.state = false;
        this.value = null;
    }

    public boolean isValid(){
        return component.getConnectionsInAmount() > connection && connection >= 0;
    }

    public void setState(boolean state){
        this.state = state;
    }

    public void setValue(Value<?> value){
        this.value = value;
    }

    public Component component() {
        return component;
    }

    public int connection() {
        return connection;
    }

    public boolean state() {
        return state;
    }

    public Value<?> value(){
        return this.value;
    }
}

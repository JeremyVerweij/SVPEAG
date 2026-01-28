package io.github.jeremyverweij.vcp.components;

import io.github.jeremyverweij.vcp.VcpApp;
import io.github.jeremyverweij.vcp.swing.PlayGround;

import java.awt.*;
import java.awt.event.MouseEvent;

@SuppressWarnings("unchecked")
public abstract class Component<T extends Component<T>> {
    protected VcpApp vcpApp;
    protected int x, y;

    private final ComponentStyler<T> painter;

    public Component(VcpApp vcpApp, int x, int y, ComponentStyler<T> painter) {
        this.vcpApp = vcpApp;
        this.x = x;
        this.y = y;
        this.painter = painter;
    }

    public abstract void onClick(MouseEvent event, Point mouse);

    public void draw(Graphics2D g2) {
        if (this.painter == null) throw new RuntimeException("Can't draw component without a painter");

        this.painter.draw((T) this, g2);
    }

    public boolean inBounds(Point point) {
        return point.x >= x && point.x <= x + this.painter.getWidth((T) this) &&
                point.y >= y && point.y <= y + this.painter.getHeight((T) this);
    }

    public void onDrag(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void repaint(){
        vcpApp.getPlayGround().repaint();
    }

    public PlayGround getPlayground(){
        return vcpApp.getPlayGround();
    }
}

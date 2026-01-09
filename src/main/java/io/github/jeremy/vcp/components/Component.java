package io.github.jeremy.vcp.components;

import io.github.jeremy.vcp.App;
import io.github.jeremy.vcp.swing.PlayGround;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class Component {
    public static final boolean DRAW_DEBUG_CORDS = false;
    public static final int COMPONENT_WIDTH = 0;
    public static final int COMPONENT_HEIGHT = 0;

    protected App app;

    protected int x, y;
    protected int width, height;

    protected Color foreground;
    protected Color background;

    public Component(App app, int x, int y, Color background) {
        this(app, x, y, COMPONENT_WIDTH, COMPONENT_HEIGHT, Color.BLACK, background);
    }

    public Component(App app, int x, int y, int width, int height, Color foreground, Color background) {
        this.app = app;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.foreground = foreground;
        this.background = background;
    }

    public abstract void onClick(MouseEvent event, Point mouse);

    public void draw(Graphics2D g2) {
        g2.setColor(this.background);
        g2.fillRect(this.x, this.y, this.width, this.height);

        g2.setColor(this.foreground);
        g2.drawRect(this.x, this.y, this.width, this.height);

        if (DRAW_DEBUG_CORDS){
            g2.drawString("(" + this.x + ", " + this.y + ")", this.x, this.y - 2);
        }
    }

    public boolean inBounds(Point point) {
        return point.x >= x && point.x <= x + width && point.y >= y && point.y <= y + height;
    }

    public void onDrag(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void repaint(){
        app.getPlayGround().repaint();
    }

    public PlayGround getPlayground(){
        return app.getPlayGround();
    }
}

package vcp.components;

import vcp.App;
import vcp.Value;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Component {
    public static final Color CONNECTION_COLOR = Color.GRAY;
    public static final Color CONNECTION_COLOR_ACTIVE = Color.PINK;

    protected App app;

    protected int x, y;
    protected int width, height;

    protected Color foreground;
    protected Color background;

    private final List<Connection> connectionsOut;
    protected final Connection[] connectionsIn;

    public Component(App app, int x, int y) {
        this(app, x, y, 100, 100);
    }

    public Component(App app, int x, int y, Color background) {
        this(app, x, y, 100, 100, Color.BLACK, background);
    }

    public Component(App app, int x, int y, int width, int height) {
        this(app, x, y, width, height, Color.BLACK, Color.WHITE);
    }

    public Component(App app, int x, int y, int width, int height, Color foreground, Color background) {
        this.connectionsIn = new Connection[getConnectionsInAmount()];

        this.app = app;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.foreground = foreground;
        this.background = background;
        this.connectionsOut = new ArrayList<>();
    }

    public abstract int getConnectionsInAmount();
    public abstract boolean run();

    public void runSelf(){
        boolean canRun = this.run();
        Value<?> outValue = null;

        if (this instanceof OutPuttingComponent outPuttingComponent){
            outValue = outPuttingComponent.getOutValue();
        }

        this.connectionsOut.sort(Comparator.comparingInt(a -> a.component().y));

        for (Connection connection : this.connectionsOut) {
            connection.setState(canRun);
            connection.setValue(outValue);
            connection.component().runSelf();
        }
    }

    public int adjustForHeight(int connection){
        int w = this.height / (getConnectionsInAmount() + 1);

        return w * (connection + 1);
    }

    public String getDrawString(){
        return null;
    }

    public String getCharForInput(int input){
        return String.valueOf(input);
    }

    public void draw(Graphics2D g2) {
        Stroke stroke = g2.getStroke();
        g2.setStroke(new BasicStroke(3));

        for (Connection connection : this.connectionsOut) {
            if (connection.state() || connection.value() != null)
                g2.setColor(CONNECTION_COLOR_ACTIVE);
            else
                g2.setColor(CONNECTION_COLOR);

            int fx = this.x + this.width;
            int fy = this.y + (this.width / 2);
            int sx = connection.component().x;
            int sy = connection.component().y + connection.component().adjustForHeight(connection.connection());
            int qx = Math.max(10, Math.abs(fx - sx) / 2 - 100);

            g2.drawLine(fx, fy, fx + qx, fy);
            g2.drawLine(fx + qx, fy, sx - qx, sy);
            g2.drawLine(sx - qx, sy, sx, sy);

            g2.drawLine(sx, sy, sx - 5, sy - 5);
            g2.drawLine(sx, sy, sx - 5, sy + 5);

            int x = - 10;
            g2.setColor(connection.component().foreground);

            g2.drawString(connection.component().getCharForInput(connection.connection()),
                    x + connection.component().x, sy);
        }

        g2.setStroke(stroke);

        g2.setColor(this.background);
        g2.fillRect(this.x + 1, this.y + 1, this.width - 1, this.height - 1);

        g2.setColor(this.foreground);
        g2.drawRect(this.x + 1, this.y + 1, this.width - 1, this.height - 1);

        for (int i = 0; i < connectionsIn.length; i++) {
            int h = adjustForHeight(i);
            int x = - 10;

            g2.drawString(getCharForInput(i), x + this.x, this.y + h);
        }

        String t = getDrawString();
        if (t != null){
            int tWidth = g2.getFontMetrics().stringWidth(t);

            g2.drawString(t, this.x + ((width - tWidth) / 2),
                    this.y + g2.getFontMetrics().getHeight() + ((height - g2.getFontMetrics().getHeight()) / 2));
        }
    }

    public void addConnection(Component component, int connection){
        Connection con = new Connection(component, connection);
        if (!con.isValid() && component.acceptInput(this, connection)) return;

        this.connectionsOut.add(con);

        component.connectionsIn[connection] = con;
    }

    public boolean acceptInput(Component component, int connection){
        return true;
    }

    public void removeConnection(Component component){
        int index = -1;

        for (int i = 0; i < this.connectionsOut.size(); i++) {
            if (this.connectionsOut.get(i).component() == component){
                index = i;
                break;
            }
        }

        if (index == -1) return;

        component.connectionsIn[index] = null;
        this.connectionsOut.remove(index);
    }

    public void removeAll() {
        for (Connection connection : this.connectionsOut) {
            connection.component().connectionsIn[connection.connection()] = null;
        }

        this.connectionsOut.clear();

        for (Connection connection : this.connectionsIn) {
            connection.component().connectionsOut.remove(connection);
        }
    }

    public void resetState() {
        for (Connection connection : this.connectionsOut) {
            connection.setState(false);
            connection.setValue(null);
        }
    }

    public void repaint(){
        app.getPlayGround().repaint();
    }

    public boolean inBounds(Point point) {
        return point.x >= x && point.x <= x + width && point.y >= y && point.y <= y + height;
    }

    public void onClick(MouseEvent event) {

    }

    public void onDrag(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
}

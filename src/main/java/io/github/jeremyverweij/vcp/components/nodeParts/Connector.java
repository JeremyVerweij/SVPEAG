package io.github.jeremyverweij.vcp.components.nodeParts;

import io.github.jeremyverweij.vcp.components.NodeComponent;
import io.github.jeremyverweij.vcp.data.node.BaseNode;

import java.awt.*;

public class Connector {
//    public static final Color CONNECTION_COLOR = Color.GRAY;
//    private static final int WIDTH = 10, HEIGHT = 10;
//    private static final Stroke STROKE = new BasicStroke(4);
//
//    private final int xOffset, yOffset;
//    private final String label;
//    private final NodeComponent owner;
//    private final int connection;
//
//    private int textWidth = -1;
//    private NodeComponent connected;
//
//    public Connector(int conn, NodeComponent owner, int xOffset, int yOffset){
//        this.connection = conn;
//        this.owner = owner;
//        this.xOffset = xOffset;
//        this.yOffset = yOffset;
//        this.label = owner.getCodeNode().getLabelForOut(conn);
//
//        this.connected = null;
//    }
//
//    private void calcTextWidth(FontMetrics fontMetrics){
//        if (textWidth == -1){
//            this.textWidth = fontMetrics.stringWidth(this.label);
//        }
//    }
//
//    public void draw(Graphics2D g2){
//        calcTextWidth(g2.getFontMetrics());
//
//        int x = owner.getX();
//        int y = owner.getY();
//
//        g2.setColor(CONNECTION_COLOR);
//
//        g2.fillRect(x + xOffset, y + yOffset, WIDTH, HEIGHT);
//
//        g2.drawString(this.label, x + xOffset - this.textWidth - 2, y + yOffset + 8);
//
//        if (this.connected != null){
//            Stroke stroke = g2.getStroke();
//            g2.setStroke(STROKE);
//
//            int xO = xOffset + owner.getX() + WIDTH;
//            int yO = yOffset + owner.getY() + (HEIGHT / 2);
//            int xO2 = this.connected.getX();
//            int yO2 = this.connected.getY() + (this.connected.getHeight() / 2);
//
//            if (xO2 < xO){
//                boolean ownUp = yO2 < yO;
//
//                int uO = ownUp ? owner.getY() - 10 : owner.getY() + owner.getHeight() + 10;
//                int uO2 = !ownUp ? connected.getY() - 10 : connected.getY() + connected.getHeight() + 10;
//
//                g2.drawLine(xO, yO, xO + WIDTH, yO);
//                g2.drawLine(xO + WIDTH, yO, xO + WIDTH, uO);
//                g2.drawLine(xO + WIDTH, uO, xO2 - WIDTH, uO2);
//                g2.drawLine(xO2, yO2, xO2 - WIDTH, yO2);
//                g2.drawLine(xO2 - WIDTH, yO2, xO2 - WIDTH, uO2);
//            }else{
//                g2.drawLine(xO, yO, xO + WIDTH, yO);
//                g2.drawLine(xO + WIDTH, yO, xO2 - WIDTH, yO2);
//                g2.drawLine(xO2, yO2, xO2 - WIDTH, yO2);
//            }
//
//            g2.setStroke(stroke);
//        }
//    }
//
//    public void drawToMouse(Graphics2D g2, int mouseX, int mouseY){
//        Stroke stroke = g2.getStroke();
//        g2.setStroke(STROKE);
//
//        int xO = xOffset + owner.getX() + WIDTH;
//        int yO = yOffset + owner.getY() + (HEIGHT / 2);
//
//        g2.drawLine(xO, yO, mouseX, mouseY);
//
//        g2.setStroke(stroke);
//    }
//
//    public boolean isMouseOver(Point point, int x, int y){
//        return point.x >= x + xOffset && point.x <= x + xOffset + WIDTH &&
//                point.y >= y + yOffset && point.y <= y + yOffset + HEIGHT;
//    }
//
//    public void setConnected(NodeComponent connected) {
//        ((BaseNode) this.owner.getCodeNode()).addOutNode(null, this.connection);
//
//        this.connected = connected;
//
//        if (connected != null) {
//            ((BaseNode) this.owner.getCodeNode()).addOutNode(connected.getCodeNode(), this.connection);
//        }
//    }
//
//    public NodeComponent getConnected() {
//        return connected;
//    }
}

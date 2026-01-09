package io.github.jeremyverweij.vcp.swing;

import io.github.jeremyverweij.vcp.App;
import io.github.jeremyverweij.vcp.components.Component;
import io.github.jeremyverweij.vcp.components.ContextMenuComponent;
import io.github.jeremyverweij.vcp.components.NodeComponent;
import io.github.jeremyverweij.vcp.components.nodeParts.Connector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayGround extends JComponent {
    private static final double minScale = 0.5, maxScale = 3.0;

    private final App app;

    private double scale = 1.0; // zoom
    private double offsetX = 0, offsetY = 0; //pan

    private Point dragStart = null;
    private Component draggingComponent = null;
    private Connector currentlyConnecting = null;
    private Point mousePos = new Point();

    private final ContextMenuComponent contextMenu;
    private final List<Component> components;

    public PlayGround(ContextMenuComponent contextMenu, App app){
        this.components = new ArrayList<>();
        this.contextMenu = contextMenu;
        this.app = app;

        setLayout(null);
        setOpaque(true);
        setBackground(Color.WHITE);
        setSize(300, 300);

        MouseAdapter mouseAdapter = new MouseHandler();

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
    }

    private void adjustPoint(Point p){
        p.x -= (int) offsetX;
        p.y -= (int) offsetY;
        p.x = (int) (p.x / scale);
        p.y = (int) (p.y / scale);
    }

    public void setScale(double newScale, Point pivot) {
        double oldScale = scale;
        scale = Math.max(minScale, Math.min(maxScale, newScale));

        if (pivot != null) {
            // Adjust offset so pivot stays under mouse
            offsetX = pivot.x - ((pivot.x - offsetX) / oldScale) * scale;
            offsetY = pivot.y - ((pivot.y - offsetY) / oldScale) * scale;
        }

        revalidate();
        repaint();
    }

    public void setScale(double newScale) {
        setScale(newScale, null);
    }

    public void setOffset(int x, int y) {
        this.offsetX = x;
        this.offsetY = y;
        repaint();
    }

    public void addComponent(Component component){
        this.components.add(component);

        if (component instanceof NodeComponent nodeComponent){
            if (!nodeComponent.getCodeNode().hasInConnection())
                app.getStartPoints().add(nodeComponent);
        }
    }

    public void removeComponent(Component component){
        this.components.remove(component);

        if (component instanceof NodeComponent nodeComponent){
            if (!nodeComponent.getCodeNode().hasInConnection())
                app.getStartPoints().remove(nodeComponent);
        }
    }

    public List<Component> getAllComponents() {
        return components;
    }

    public Connector getCurrentlyConnecting() {
        return currentlyConnecting;
    }

    public void setCurrentlyConnecting(Connector currentlyConnecting) {
        this.currentlyConnecting = currentlyConnecting;
    }

    public Point getMousePos() {
        return mousePos;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.clearRect(0, 0, this.getWidth(), this.getHeight());

        g2.translate(offsetX, offsetY);
        g2.scale(scale, scale);

        for (Component component : this.components) {
            component.draw(g2);
        }

        if (this.getCurrentlyConnecting() != null){
            this.currentlyConnecting.drawToMouse(g2, this.mousePos.x, this.mousePos.y);
        }

        this.contextMenu.draw(g2);

        g2.dispose();
    }

    public Point getWorldCenter(){
        Point center = new Point(this.getWidth() / 2, this.getHeight() / 2);
        adjustPoint(center);
        return center;
    }

    private class MouseHandler extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                dragStart = e.getPoint();

                Point m = new Point(e.getPoint());
                adjustPoint(m);

                for (Component component : components) {
                    if (component.inBounds(m)){
                        draggingComponent = component;
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            dragStart = null;
            draggingComponent = null;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (dragStart != null) {
                Point dragEnd = e.getPoint();

                if (draggingComponent != null){
                    Point de = new Point(dragEnd);
                    adjustPoint(de);
                    adjustPoint(dragStart);

                    draggingComponent.onDrag(de.x - dragStart.x, de.y - dragStart.y);
                }else{
                    offsetX += dragEnd.x - dragStart.x / Math.min(scale * 2, 1);
                    offsetY += dragEnd.y - dragStart.y / Math.min(scale * 2, 1);
                }

                dragStart = dragEnd;
                repaint();
            }else{
                mouseMoved(e);
                mouseClicked(e);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Point m = new Point(e.getPoint());
            adjustPoint(m);

            if (contextMenu.isVisible()){
                if (contextMenu.inBounds(m)){
                    contextMenu.onClick(e, m);
                }else{
                    contextMenu.hide();
                }

                repaint();
                return;
            }

            boolean anyComponentInBound = false;

            for (Component component : components) {
                if (component.inBounds(m)){
                    component.onClick(e, m);
                    anyComponentInBound = true;
                    break;
                }
            }

            if (!anyComponentInBound){
                setCurrentlyConnecting(null);
                repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if(getCurrentlyConnecting() != null) repaint();

            mousePos = e.getPoint();
            adjustPoint(mousePos);
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            // Optional zoom with wheel
            double factor = 1.1;
            if (e.getPreciseWheelRotation() > 0) factor = 1 / factor;
            setScale(scale * factor, e.getPoint());
        }
    }
}

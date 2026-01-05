package vcp.swing;

import vcp.components.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayGround extends JComponent {
    private static final double minScale = 0.5, maxScale = 3.0;

    private double scale = 1.0; // zoom
    private double offsetX = 0, offsetY = 0; //pan

    private Point dragStart = null;
    private Component draggingComponent = null;

    private final List<Component> components;
    private final List<LaterInvokers> invokeLater;

    public PlayGround(){
        this.components = new ArrayList<>();
        this.invokeLater = new ArrayList<>();

        setLayout(null);
        setOpaque(true);
        setBackground(Color.WHITE);
        setSize(300, 300);

        MouseAdapter mouseAdapter = new MouseAdapter() {
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
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Point m = new Point(e.getPoint());
                adjustPoint(m);

                for (Component component : components) {
                    if (component.inBounds(m)){
                        component.onClick(e);
                    }
                }
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                // Optional zoom with wheel
                double factor = 1.1;
                if (e.getPreciseWheelRotation() > 0) factor = 1 / factor;
                setScale(scale * factor, e.getPoint());
            }
        };

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
    }

    public void removeComponent(Component component){
        this.components.remove(component);
        component.removeAll();
    }

    public void addInvokeLater(Runnable runnable, long millis){
        LaterInvokers laterInvokers = new LaterInvokers(runnable, true, millis);
        this.invokeLater.add(laterInvokers);

        new Thread(laterInvokers).start();
    }

    public void resetStates(){
        for (Component component : this.components) {
            component.resetState();
        }

        for (LaterInvokers laterInvokers : this.invokeLater) {
            laterInvokers.canStillRun = false;
        }

        repaint();
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

        g2.dispose();
    }

    private class LaterInvokers implements Runnable{
        private final Runnable runnable;
        private boolean canStillRun;
        long millisToWait;

        public LaterInvokers(Runnable runnable, boolean canStillRun, long millisToWait) {
            this.runnable = runnable;
            this.canStillRun = canStillRun;
            this.millisToWait = millisToWait;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(millisToWait);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (canStillRun){
                runnable.run();
            }

            PlayGround.this.repaint();
        }
    }
}

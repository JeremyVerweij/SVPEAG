package io.github.jeremyverweij.vcp.components;

import java.awt.*;

public abstract class ComponentStyler<T extends Component<T>> {
    public abstract void draw(T component, Graphics2D g2);

    public abstract int getWidth(T component);
    public abstract int getHeight(T component);
}

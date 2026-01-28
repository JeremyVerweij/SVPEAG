package io.github.jeremyverweij.vcp.components;

import io.github.jeremyverweij.vcp.VcpApp;

import java.awt.*;
import java.awt.event.MouseEvent;

public class NodeComponent extends Component<NodeComponent>{
    public NodeComponent(VcpApp vcpApp, int x, int y) {
        super(vcpApp, x, y, vcpApp.getNodeComponentComponentStyler());
    }

    @Override
    public void onClick(MouseEvent event, Point mouse) {

    }
}

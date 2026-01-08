package vcp.components;

import vcp.App;
import vcp.components.nodeParts.Connector;
import vcp.components.nodeParts.DataInDisplay;
import vcp.components.nodeParts.DataOutDisplay;
import vcp.walker.CodeNode;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class NodeComponent extends Component{
    public static final int DATA_IN_AND_OUT_WIDTH = 160;

    private final DataInDisplay[] dataInDisplays;
    private DataOutDisplay dataOutDisplay;
    private final Connector[] connectors;
    protected final CodeNode codeNode;

    public NodeComponent(App app, int x, int y, CodeNode codeNode) {
        super(app, x, y, app.getNodeColors().getColor(codeNode.getClass()));

        this.codeNode = codeNode;
        this.dataInDisplays = new DataInDisplay[this.codeNode.dataInputs()];
        this.connectors = new Connector[this.codeNode.getOutConnections()];

        calcHeight();
        calcWidth();
        initDataInDisplays();
        initDataOutDisplay();
        initConnectors();
    }

    private void calcHeight(){
        int maxValues = Math.max(this.codeNode.getOutConnections(),
                this.codeNode.dataInputs() + (this.codeNode.hasDataOutput() ? 1 : 0));

        int valuesHeight = maxValues * 20;

        if (valuesHeight > this.height){
            this.height = valuesHeight + 10;
        }
    }

    private void calcWidth(){
        int valuesWidth = (this.codeNode.hasInConnection() ? 100 : 0) +
                (this.codeNode.getOutConnections() > 0 ? 100 : 0) +
                (this.codeNode.dataInputs() > 0 || this.codeNode.hasDataOutput() ? DATA_IN_AND_OUT_WIDTH : 0);

        if (valuesWidth > this.width){
            this.width = valuesWidth;
        }
    }

    private void initDataInDisplays(){
        int x = this.codeNode.hasInConnection() ? 100 : 0;

        for (int i = 0; i < this.dataInDisplays.length; i++) {
            this.dataInDisplays[i] = new DataInDisplay(i, this, x,
                    (i + (this.codeNode.hasDataOutput() ? 1 : 0)) * 20 + 5);
        }
    }

    private void initDataOutDisplay(){
        int x = this.codeNode.hasInConnection() ? 100 : 0;

        if (codeNode.hasDataOutput())
            this.dataOutDisplay = new DataOutDisplay(this, x, 5);
        else this.dataOutDisplay = null;
    }

    private void initConnectors(){
        int m = this.height / (this.codeNode.getOutConnections() + 1);

        for (int i = 0; i < this.connectors.length; i++) {
            this.connectors[i] = new Connector(i, this, this.width - 12, (i + 1) * m - 5);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);

        int nameX = this.x + 3;

        if (!this.codeNode.hasInConnection()){
            g2.setColor(this.background);
            g2.fillArc(this.x - 14, this.y, 30, this.height, 90, 180);

            g2.setColor(this.foreground);
            g2.drawArc(this.x - 14, this.y, 30, this.height, 90, 180);

            nameX -= 14;
        }

        g2.drawString(this.codeNode.toString(), nameX, this.y + this.height / 2 + 2);

        for (DataInDisplay dataInDisplay : this.dataInDisplays) {
            dataInDisplay.draw(g2);
        }

        if (this.dataOutDisplay != null){
            this.dataOutDisplay.draw(g2);
        }

        for (Connector connector : this.connectors) {
            connector.draw(g2);
        }
    }

    public void delete() {
        for (Connector connector : this.connectors) {
            connector.setConnected(null);
        }

        Set<String> removeVars = new HashSet<>();

        if(this.codeNode.inVarName() != null){
            for (String invar : this.codeNode.inVarName()) {
                if(invar != null) removeVars.add(invar);
            }
        }

        if (this.codeNode.outVarName() != null)
            removeVars.add(this.codeNode.outVarName());

        for (Component component : getPlayground().getAllComponents()) {
            if (component instanceof NodeComponent nodeComponent){
                for (String invar : nodeComponent.codeNode.inVarName()) {
                    removeVars.remove(invar);
                }

                removeVars.remove(nodeComponent.codeNode.outVarName());

                for (Connector connector : nodeComponent.connectors) {
                    if (connector.getConnected() == this){
                        connector.setConnected(null);
                        break;
                    }
                }
            }
        }

        for (String removeVar : removeVars) {
            this.app.removeVar(removeVar);
        }
    }

    public void removeVar(String name){
        for (Component component : getPlayground().getAllComponents()) {
            if (component instanceof NodeComponent nodeComponent) {
                for (String invar : nodeComponent.codeNode.inVarName()) {
                    if (Objects.equals(invar, name)) return;
                }

                if (Objects.equals(nodeComponent.codeNode.outVarName(), name)) return;
            }
        }

        this.app.removeVar(name);
    }

    @Override
    public void onClick(MouseEvent event, Point mouse) {
        if (event.getButton() == MouseEvent.BUTTON1){
            if (getPlayground().getCurrentlyConnecting() != null){
                boolean isSelf = false;

                for (Connector connector : this.connectors) {
                    if (connector == getPlayground().getCurrentlyConnecting()){
                        isSelf = true;
                        break;
                    }
                }

                if (!isSelf && this.codeNode.hasInConnection()){
                    getPlayground().getCurrentlyConnecting().setConnected(this);
                }

                getPlayground().setCurrentlyConnecting(null);
                repaint();
                return;
            }

            for (Connector connector : this.connectors) {
                if (connector.isMouseOver(mouse, this.x, this.y)){
                    getPlayground().setCurrentlyConnecting(connector);
                    connector.setConnected(null);
                    return;
                }
            }
        } else if (event.getButton() == MouseEvent.BUTTON3) {
            if(getPlayground().getCurrentlyConnecting() != null){
                getPlayground().setCurrentlyConnecting(null);
                repaint();
                return;
            }

            for (Connector connector : this.connectors) {
                if (connector.isMouseOver(mouse, this.x, this.y)){
                    connector.setConnected(null);
                    repaint();
                    return;
                }
            }

            app.requestContextMenu(this);
        }
    }

    public CodeNode getCodeNode() {
        return codeNode;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public Connector[] getConnectors() {
        return connectors;
    }

    public DataInDisplay[] getDataInDisplays() {
        return dataInDisplays;
    }

    public DataOutDisplay getDataOutDisplay() {
        return dataOutDisplay;
    }
}

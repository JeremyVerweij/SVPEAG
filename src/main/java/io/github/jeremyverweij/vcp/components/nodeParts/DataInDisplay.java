package io.github.jeremyverweij.vcp.components.nodeParts;

import io.github.jeremyverweij.vcp.components.NodeComponent;

import java.awt.*;

public class DataInDisplay {
//    private final int xOffset, yOffset;
//    private final String label;
//    private final NodeComponent owner;
//    private final int dataIn;
//    private String directValue = "null";
//    private String varName = null;
//
//    private int textWidth = -1;
//
//    public DataInDisplay(int dataIn, NodeComponent owner, int xOffset, int yOffset) {
//        this.dataIn = dataIn;
//        this.owner = owner;
//        this.xOffset = xOffset;
//        this.yOffset = yOffset;
//        this.label = owner.getCodeNode().getLabelForData(dataIn, false) + ":";
//    }
//
//    private void calcTextWidth(FontMetrics fontMetrics){
//        if (textWidth == -1){
//            this.textWidth = fontMetrics.stringWidth(this.label);
//        }
//    }
//
//    private String getValue(){
//        if (varName != null) return varName;
//
//        return directValue;
//    }
//
//    private void setColor(Graphics2D g2, boolean text){
//        if (varName != null){
//            g2.setColor(text ? Color.WHITE : Color.ORANGE);
//        }else{
//            g2.setColor(text ? Color.BLACK : Color.WHITE);
//        }
//    }
//
//    public void draw(Graphics2D g2){
//        calcTextWidth(g2.getFontMetrics());
//
//        int x = owner.getX();
//        int y = owner.getY();
//
//        g2.setColor(Color.BLACK);
//        g2.drawString(this.label, x + xOffset, y + yOffset + 12);
//
//        String value = getValue();
//        int w = g2.getFontMetrics().stringWidth(value) + 20;
//        w = Math.min(w, DATA_IN_AND_OUT_WIDTH - 2 - textWidth);
//
//        setColor(g2, false);
//        g2.fillRoundRect(x + xOffset + 2 + textWidth, y + yOffset - 1, w, 18, 10, 10);
//
//        setColor(g2, true);
//        g2.setClip(x + xOffset + 2 + textWidth + 10, y + yOffset, w - 10, 20);
//        g2.drawString(value, x + xOffset + 2 + textWidth + 10, y + yOffset + 12);
//        g2.setClip(null);
//    }
//
//    public void setVarName(String varName) {
//        this.owner.getCodeNode().inVarName()[this.dataIn] = this.varName = varName;
//    }
//
//    public void setDirectValue(String directValue) {
//        this.owner.getCodeNode().directInVal()[this.dataIn] = this.directValue = directValue;
//    }
//
//    public String getDirectValue() {
//        return directValue;
//    }
//
//    public String getVarName() {
//        return varName;
//    }
}

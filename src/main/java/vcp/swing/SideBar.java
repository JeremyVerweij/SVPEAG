package vcp.swing;

import vcp.App;
import vcp.components.NodeComponent;
import vcp.walker.CodeNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class SideBar extends JScrollPane {
    private static final JPanel content = new JPanel();
    private final App app;
    private final Map<Color, String> colorCategories;

    public SideBar(App app){
        super(content);

        this.app = app;
        this.colorCategories = new HashMap<>();

        setPreferredSize(new Dimension(300, 0));
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        reloadComponents();
    }

    public void reloadComponents(){
        content.removeAll();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(generateButton());

        content.add(Box.createVerticalStrut(10));

        for (Map.Entry<Color, String> category : this.colorCategories.entrySet()) {
            createCategory(category);
        }

        this.repaint();
    }

    private JButton generateButton(){
        JButton button = new JButton("Generate!");

        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.addActionListener(this::onGenerateClick);

        return button;
    }

    private void createCategory(Map.Entry<Color, String> category){
        JLabel label = new JLabel(category.getValue());
        label.setBackground(category.getKey());
        label.setOpaque(true);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setPreferredSize(new Dimension(300, 20));
        label.setMaximumSize(label.getPreferredSize());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        content.add(label);

        for (Class<? extends CodeNode> node : this.app.getNodeColors().getNodesFromColor(category.getKey())) {
            content.add(Box.createVerticalStrut(2));
            createNodeCreator(node, category.getKey());
        }

        content.add(Box.createVerticalStrut(10));
    }

    private void createNodeCreator(Class<? extends CodeNode> node, Color color){
        JButton button = new JButton(getNameFromNode(node));
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setBackground(color);

        button.addActionListener((e) -> {
            Point center = this.app.getPlayGround().getWorldCenter();

            this.app.getPlayGround().addComponent(new NodeComponent(this.app, center.x, center.y, getCodeNode(node)));
            this.app.getPlayGround().repaint();
        });

        content.add(button);
    }

    private String getNameFromNode(Class<? extends CodeNode> node){
        return getCodeNode(node).toString();
    }

    private CodeNode getCodeNode(Class<? extends CodeNode> node){
        try {
            return node.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private void onGenerateClick(ActionEvent actionEvent) {
        for (vcp.components.Component component : this.app.getPlayGround().getAllComponents()) {
            if (component instanceof NodeComponent nodeComponent){
                if(!nodeComponent.getCodeNode().hasInConnection()){
                    this.app.getCodeWalker().eval(nodeComponent.getCodeNode());
                }
            }
        }
    }

    public Map<Color, String> getColorCategories() {
        return colorCategories;
    }
}

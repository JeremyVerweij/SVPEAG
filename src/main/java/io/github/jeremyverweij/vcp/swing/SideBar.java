package io.github.jeremyverweij.vcp.swing;

import io.github.jeremyverweij.vcp.App;
import io.github.jeremyverweij.vcp.components.NodeComponent;
import io.github.jeremyverweij.vcp.walker.CodeNode;
import io.github.jeremyverweij.vcp.walker.DataType;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class SideBar extends JScrollPane {
    private final JPanel content;
    private final App app;
    private final Map<Color, String> colorCategories;

    private JComboBox<String> existingVars;

    public SideBar(App app){
        this.content = new JPanel();

        setupScrollBar();

        this.app = app;
        this.colorCategories = new HashMap<>();

        setPreferredSize(new Dimension(300, 0));

        reloadComponents();
    }

    private void setupScrollBar(){
        setLayout(new ScrollPaneLayout.UIResource());
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setViewport(createViewport());
        setVerticalScrollBar(createVerticalScrollBar());
        setHorizontalScrollBar(createHorizontalScrollBar());
        setViewportView(content);
        setOpaque(true);
        updateUI();

        if (!this.getComponentOrientation().isLeftToRight()) {
            viewport.setViewPosition(new Point(Integer.MAX_VALUE, 0));
        }
    }

    public void reloadComponents(){
        content.removeAll();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        createVarCreatorButton();

        content.add(Box.createVerticalStrut(10));

        for (Map.Entry<Color, String> category : this.colorCategories.entrySet()) {
            createCategory(category);
        }

        this.repaint();
    }

    private void createVarCreatorButton() {
        JTextField addVarName = new JTextField("var_name");
        addVarName.setMaximumSize(new Dimension(3000, 30));

        JComboBox<String> addVarType = new JComboBox<>(DataType.allTypes.stream()
                .map(Class::getSimpleName)
                .toArray(String[]::new));
        addVarType.setMaximumSize(new Dimension(3000, 30));
        addVarType.setFocusable(false);

        final JButton addVarButton = getAddVarButton(addVarName, addVarType);

        content.add(addVarName);
        content.add(addVarType);
        content.add(addVarButton);

        content.add(Box.createVerticalStrut(10));

        existingVars = new JComboBox<>(app.getVars()
                .keySet()
                .stream()
                .filter((e) -> !e.startsWith("${"))
                .toArray(String[]::new));
        existingVars.setMaximumSize(new Dimension(3000, 30));
        existingVars.setFocusable(false);

        JButton existingVarDeleteButton = new JButton("Delete Var");
        existingVarDeleteButton.setAlignmentX(CENTER_ALIGNMENT);
        existingVarDeleteButton.setFocusable(false);
        existingVarDeleteButton.addActionListener((ignore) -> {
            String varName = ((String) existingVars.getSelectedItem());

            if (varName != null){
                app.removeVar(varName);
            }
        });

        content.add(existingVars);
        content.add(existingVarDeleteButton);
    }

    private JButton getAddVarButton(JTextField addVarName, JComboBox<String> addVarType) {
        JButton addVarButton = new JButton("Add variable");
        addVarButton.setAlignmentX(CENTER_ALIGNMENT);
        addVarButton.setFocusable(false);
        addVarButton.addActionListener((ignore) -> {
            String name = addVarName.getText();

            if (name.matches("[A-Za-z][A-Za-z0-9_]*") && !app.existVar(name)){
                DataType dataType = DataType.getDataType(((String) addVarType.getSelectedItem()));
                app.createVar(dataType, name);
            }
        });
        return addVarButton;
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

    public void addVar(String name){
        this.existingVars.addItem(name);
    }

    public void removeVar(String name){
        this.existingVars.removeItem(name);
    }

    public Map<Color, String> getColorCategories() {
        return colorCategories;
    }
}

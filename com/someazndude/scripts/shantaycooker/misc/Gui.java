package com.someazndude.scripts.shantaycooker.misc;

import com.someazndude.scripts.api.methods.actions.Inventory;
import com.someazndude.scripts.shantaycooker.ShantayCooker;
import com.someazndude.scripts.shantaycooker.loops.ItemCheck;
import com.someazndude.scripts.shantaycooker.nodes.Banking;
import com.someazndude.scripts.shantaycooker.nodes.Cook;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.methods.Environment;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Gui extends JDialog {
    private final JTextField textField;

    public Gui() {
        setTitle("Some Shantay Bonfire Item User");
        setAlwaysOnTop(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 152, 133);
        getContentPane().setLayout(new BorderLayout());
        final JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        final JLabel lblInputItemId = new JLabel("Input Item Id:");
        textField = new JTextField();
        textField.setColumns(10);
        final Properties properties = new Properties();
        try {
            properties.load(new FileReader(new File(Environment.getStorageDirectory(), "settings.ini")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        textField.setText(String.valueOf(properties.get("id")));

        GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
        gl_contentPanel.setHorizontalGroup(gl_contentPanel
                .createParallelGroup(Alignment.LEADING)
                .addGroup(
                        gl_contentPanel.createSequentialGroup().addGap(33)
                                .addComponent(lblInputItemId))
                .addGroup(
                        gl_contentPanel
                                .createSequentialGroup()
                                .addGap(7)
                                .addComponent(textField,
                                        GroupLayout.PREFERRED_SIZE, 120,
                                        GroupLayout.PREFERRED_SIZE)));
        gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(
                Alignment.LEADING).addGroup(
                gl_contentPanel
                        .createSequentialGroup()
                        .addGap(10)
                        .addComponent(lblInputItemId)
                        .addGap(9)
                        .addComponent(textField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)));
        contentPanel.setLayout(gl_contentPanel);
        JPanel buttonPane = new JPanel();
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        final JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        getRootPane().setDefaultButton(okButton);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField.getText().matches("[^0-9]") || textField.getText().isEmpty()) {
                    return;
                }

                GlobalVariables.itemId = Integer.parseInt(textField.getText());
                ItemCheck.itemCheckTab = Inventory.getCount(GlobalVariables.itemId);
                ShantayCooker.putProperties("id", String.valueOf(GlobalVariables.itemId));
                ShantayCooker.storeProperties();

                ShantayCooker.setJobContainer(new Tree(new Node[]{new Cook(), new Banking()}));
                dispose();
            }
        });
        GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
        gl_buttonPane.setHorizontalGroup(gl_buttonPane.createParallelGroup(
                Alignment.LEADING).addGroup(
                gl_buttonPane.createSequentialGroup().addGap(48)
                        .addComponent(okButton)
                        .addContainerGap(47, Short.MAX_VALUE)));
        gl_buttonPane.setVerticalGroup(gl_buttonPane.createParallelGroup(
                Alignment.LEADING).addGroup(
                gl_buttonPane
                        .createSequentialGroup()
                        .addComponent(okButton)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)));
        buttonPane.setLayout(gl_buttonPane);
    }
}

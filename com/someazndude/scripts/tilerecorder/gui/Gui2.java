package com.someazndude.scripts.tilerecorder.gui;

import com.someazndude.scripts.tilerecorder.Variables;
import org.powerbot.game.api.methods.Environment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Gui2 extends JDialog {
    private final JTextField textField;
    private final DefaultTableModel tableModel;

    public Gui2() {
        setBounds(100, 100, 256, 111);
        getContentPane().setLayout(new BorderLayout());
        {
            JPanel buttonPane = new JPanel();
            getContentPane().add(buttonPane, BorderLayout.CENTER);
            final JLabel lblName;
            {
                lblName = new JLabel("Name :");
            }
            {
                textField = new JTextField();
                textField.setHorizontalAlignment(SwingConstants.CENTER);
                textField.setColumns(10);
            }
            final JButton okButton;
            {

                tableModel = (DefaultTableModel) Variables.gui.getTable();
                okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                getRootPane().setDefaultButton(okButton);
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {

                        File path = new File(Environment.getStorageDirectory(), textField.getText() + ".txt");
                        if (path.exists()) {
                            path.delete();
                        }

                        try {
                            path.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {

                            BufferedWriter writer = new BufferedWriter(new FileWriter(path));

                            for (int i = 0; i < tableModel.getColumnCount(); i++) {

                                if (i == 0) {
                                    writer.write("Tiles : new Tile[] {");
                                } else {
                                    if (i == 1) {
                                        writer.write("X Derive : ");
                                    } else if (i == 2) {
                                        writer.write("Y Derive : ");
                                    } else writer.write("Plane : ");

                                    writer.write("new int[] {");
                                }

                                for (int z = 0; z < tableModel.getRowCount(); z++) {

                                    if (i == 0) {
                                        writer.write("new Tile" + tableModel.getValueAt(z, i));
                                    } else writer.write(String.valueOf(tableModel.getValueAt(z, i)));

                                    if (z != tableModel.getRowCount() - 1) {
                                        writer.write(", ");
                                    }
                                }

                                writer.write("};");
                                writer.newLine();
                                writer.newLine();
                            }

                            writer.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        dispose();
                    }
                });
            }
            final JButton cancelButton;
            {
                cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        dispose();
                    }
                });
            }
            GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
            gl_buttonPane.setHorizontalGroup(gl_buttonPane
                    .createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(
                            gl_buttonPane.createSequentialGroup().addGap(107)
                                    .addComponent(lblName))
                    .addGroup(
                            gl_buttonPane
                                    .createSequentialGroup()
                                    .addGap(7)
                                    .addComponent(textField,
                                            GroupLayout.PREFERRED_SIZE, 234,
                                            GroupLayout.PREFERRED_SIZE))
                    .addGroup(
                            gl_buttonPane.createSequentialGroup().addGap(64)
                                    .addComponent(okButton).addGap(4)
                                    .addComponent(cancelButton)));
            gl_buttonPane.setVerticalGroup(gl_buttonPane.createParallelGroup(
                    GroupLayout.Alignment.LEADING).addGroup(
                    gl_buttonPane
                            .createSequentialGroup()
                            .addGap(7)
                            .addComponent(lblName)
                            .addGap(4)
                            .addComponent(textField,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE)
                            .addGap(12)
                            .addGroup(
                                    gl_buttonPane
                                            .createParallelGroup(
                                                    GroupLayout.Alignment.LEADING)
                                            .addComponent(okButton)
                                            .addComponent(cancelButton))));
            buttonPane.setLayout(gl_buttonPane);
        }
    }
}
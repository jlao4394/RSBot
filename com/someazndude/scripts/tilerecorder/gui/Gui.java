package com.someazndude.scripts.tilerecorder.gui;

import com.someazndude.scripts.tilerecorder.Variables;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Tile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public Gui() {
        initialize();
    }

    private void initialize() {
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 501, 322);
        final JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        final JButton btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Variables.startTile = Players.getLocal().getLocation();
            }
        });

        final JButton btnRecord = new JButton("Record");
        btnRecord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (btnRecord.getText().equals("Record") && Variables.forward.size() + Variables.backward.size() == 0) {
                    if (Variables.startTile != null) {
                        btnRecord.setText("Stop");
                        Variables.recording = true;
                        tableModel.addRow(new String[]{Players.getLocal().getLocation().toString(), String.valueOf(Variables.startTile.getX() - Players.getLocal().getLocation().getX()), String.valueOf(Variables.startTile.getY() - Players.getLocal().getLocation().getY()), String.valueOf(Players.getLocal().getLocation().getPlane())});
                    }
                } else {
                    btnRecord.setText("Record");
                    Variables.recording = false;
                    tableModel.addRow(new String[]{Players.getLocal().getLocation().toString(), String.valueOf(Variables.startTile.getX() - Players.getLocal().getLocation().getX()), String.valueOf(Variables.startTile.getY() - Players.getLocal().getLocation().getY()), String.valueOf(Players.getLocal().getLocation().getPlane())});
                }
            }
        });

        final JButton btnTravel = new JButton("Travel");
        btnTravel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableModel.getRowCount() != 0 && !Variables.recording) {
                    if (btnTravel.getText().equals("Travel")) {
                        btnTravel.setText("Stop");
                    } else btnTravel.setText("Travel");

                    if (Variables.forward.size() + Variables.backward.size() != 0) {
                        Variables.forward.clear();
                        Variables.backward.clear();
                    } else {
                        for (int i = 0; i < tableModel.getRowCount(); i++) {

                            String[] s = ((String) tableModel.getValueAt(i, 0)).replaceAll("[^0-9,]", "").split(",");
                            int x = Integer.parseInt(s[0]);
                            int y = Integer.parseInt(s[1]);
                            int plane = Integer.parseInt(s[2]);

                            Variables.forward.addLast(new Tile(x, y, plane));
                            Variables.backward.addFirst(new Tile(x, y, plane));
                        }
                    }
                }
            }
        });

        final JScrollPane scrollPane = new JScrollPane();

        table = new JTable();
        table.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Tile", "X Derive", "Y Derive", "Plane"}) {
            final Class[] columnTypes = new Class[]{String.class, String.class,
                    String.class, String.class};

            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }

            final boolean[] columnEditables = new boolean[]{false, false, false,
                    false};

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableModel = (DefaultTableModel) table.getModel();

        scrollPane.setViewportView(table);

        final JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Variables.gui2.setVisible(true);
            }
        });

        final JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (Variables.startTile != null) {
                    tableModel.addRow(new String[]{Players.getLocal().getLocation().toString(), String.valueOf(Variables.startTile.getX() - Players.getLocal().getLocation().getX()), String.valueOf(Variables.startTile.getY() - Players.getLocal().getLocation().getY()), String.valueOf(Players.getLocal().getLocation().getPlane())});
                }
            }
        });

        final JButton btnRemove = new JButton("Remove");
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (table.getSelectedRow() != -1) {
                    tableModel.removeRow(table.getSelectedRow());
                }
            }
        });

        final JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                tableModel.setRowCount(0);
            }
        });

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(
                GroupLayout.Alignment.LEADING).addGroup(
                gl_contentPane
                        .createSequentialGroup()
                        .addGap(7)
                        .addGroup(
                                gl_contentPane
                                        .createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(btnStart,
                                                GroupLayout.PREFERRED_SIZE, 73,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnRecord,
                                                GroupLayout.PREFERRED_SIZE, 73,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnTravel,
                                                GroupLayout.PREFERRED_SIZE, 73,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSave,
                                                GroupLayout.PREFERRED_SIZE, 73,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAdd,
                                                GroupLayout.PREFERRED_SIZE, 73,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnRemove)
                                        .addComponent(btnClear,
                                                GroupLayout.PREFERRED_SIZE, 73,
                                                GroupLayout.PREFERRED_SIZE))
                        .addGap(4)
                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE,
                                383, GroupLayout.PREFERRED_SIZE)));
        gl_contentPane.setVerticalGroup(gl_contentPane
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                        gl_contentPane.createSequentialGroup().addGap(23)
                                .addComponent(btnStart).addGap(4)
                                .addComponent(btnRecord).addGap(4)
                                .addComponent(btnTravel).addGap(4)
                                .addComponent(btnSave).addGap(4)
                                .addComponent(btnAdd).addGap(4)
                                .addComponent(btnRemove).addGap(4)
                                .addComponent(btnClear))
                .addGroup(
                        gl_contentPane
                                .createSequentialGroup()
                                .addGap(7)
                                .addComponent(scrollPane,
                                        GroupLayout.PREFERRED_SIZE, 273,
                                        GroupLayout.PREFERRED_SIZE)));
        contentPane.setLayout(gl_contentPane);
    }

    public TableModel getTable() {
        return table.getModel();
    }
}
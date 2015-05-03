package com.someazndude.scripts.castlewars.misc;

import com.someazndude.scripts.api.methods.actions.Idle;
import com.someazndude.scripts.castlewars.CastleWars;
import com.someazndude.scripts.castlewars.constants.Helm;
import com.someazndude.scripts.castlewars.constants.Portal;
import com.someazndude.scripts.castlewars.methods.Portals;
import com.someazndude.scripts.castlewars.nodes.InGame;
import com.someazndude.scripts.castlewars.nodes.JoinGame;
import com.someazndude.scripts.castlewars.nodes.Lobby;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame {

    private JComboBox<Helm> comboBoxHelms;
    public JTable table;
    private JComboBox<Portal> comboBoxPortal;
    private JLabel lblTime;
    private JLabel lblNextActionIn;
    private JLabel lblGamesPlayed;
    private JLabel lblGamesWon;

    public Gui() {
        initialize();
        new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setText();
            }
        }).start();
    }

    private void initialize() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Some Castle War AFKer");
        setBounds(100, 100, 368, 389);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        final JScrollPane scrollPane = new JScrollPane();

        table = new JTable();
        table.setModel(new DefaultTableModel(new Helm[][]{},
                new String[]{"Helms Queue"}) {
            final boolean[] columnEditables = new boolean[]{false};

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table.getColumnModel().getColumn(0).setResizable(false);
        scrollPane.setViewportView(table);
        final DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

        final JButton btnApply = new JButton("Start");
        btnApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (btnApply.getText().equals("Start")) {
                    btnApply.setText("Apply");
                }

                Portals.chosenPortal = (Portal) comboBoxPortal.getSelectedItem();

                CastleWars.jobContainer = new Tree(new Node[]{new JoinGame(), new Lobby(), new InGame()});

            }
        });

        final JLabel lblHelms = new JLabel("Helms :");

        comboBoxHelms = new JComboBox<>(Helm.values());

        final JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                tableModel.addRow(new Helm[]{(Helm) comboBoxHelms.getSelectedItem()});
            }
        });

        final JButton btnRemove = new JButton("Remove");
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1) {
                    tableModel.removeRow(table.getSelectedRow());
                }
            }
        });

        final JLabel lblPortal = new JLabel("Portal :");

        comboBoxPortal = new JComboBox<>(Portal.values());

        lblTime = new JLabel("Time Running : " + Variables.time.toElapsedString());

        lblNextActionIn = new JLabel("Next Action in : " + Idle.getTimer().toRemainingString());

        lblGamesPlayed = new JLabel("Games Played : " + Variables.gamesPlayed);

        lblGamesWon = new JLabel("Games Won : " + Variables.gamesWon);

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane
                .setHorizontalGroup(gl_contentPane
                        .createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(
                                gl_contentPane
                                        .createSequentialGroup()
                                        .addGroup(
                                                gl_contentPane
                                                        .createParallelGroup(
                                                                GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblTime)
                                                        .addGroup(
                                                                gl_contentPane
                                                                        .createSequentialGroup()
                                                                        .addGroup(
                                                                                gl_contentPane
                                                                                        .createParallelGroup(
                                                                                                GroupLayout.Alignment.LEADING)
                                                                                        .addGroup(
                                                                                                gl_contentPane
                                                                                                        .createSequentialGroup()
                                                                                                        .addGap(7)
                                                                                                        .addGroup(
                                                                                                                gl_contentPane
                                                                                                                        .createParallelGroup(
                                                                                                                                GroupLayout.Alignment.LEADING)
                                                                                                                        .addGroup(
                                                                                                                                gl_contentPane
                                                                                                                                        .createSequentialGroup()
                                                                                                                                        .addGap(43)
                                                                                                                                        .addComponent(
                                                                                                                                                lblHelms))
                                                                                                                        .addComponent(
                                                                                                                                comboBoxHelms,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                116,
                                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                                        .addComponent(
                                                                                                                                btnAdd,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                116,
                                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                                        .addComponent(
                                                                                                                                btnRemove,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                116,
                                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                                        .addGroup(
                                                                                                                                gl_contentPane
                                                                                                                                        .createSequentialGroup()
                                                                                                                                        .addGap(44)
                                                                                                                                        .addComponent(
                                                                                                                                                lblPortal))
                                                                                                                        .addComponent(
                                                                                                                                comboBoxPortal,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                116,
                                                                                                                                GroupLayout.PREFERRED_SIZE)))
                                                                                        .addComponent(
                                                                                                lblNextActionIn)
                                                                                        .addComponent(
                                                                                                lblGamesPlayed)
                                                                                        .addComponent(
                                                                                                lblGamesWon))
                                                                        .addGap(4)
                                                                        .addComponent(
                                                                                scrollPane,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                216,
                                                                                GroupLayout.PREFERRED_SIZE)))
                                        .addContainerGap(
                                                GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE))
                        .addGroup(
                                gl_contentPane.createSequentialGroup()
                                        .addContainerGap(151, Short.MAX_VALUE)
                                        .addComponent(btnApply).addGap(141)));
        gl_contentPane
                .setVerticalGroup(gl_contentPane
                        .createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(
                                gl_contentPane
                                        .createSequentialGroup()
                                        .addGap(7)
                                        .addComponent(lblTime)
                                        .addGap(4)
                                        .addGroup(
                                                gl_contentPane
                                                        .createParallelGroup(
                                                                GroupLayout.Alignment.LEADING)
                                                        .addGroup(
                                                                gl_contentPane
                                                                        .createSequentialGroup()
                                                                        .addComponent(
                                                                                lblNextActionIn)
                                                                        .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(
                                                                                lblGamesPlayed)
                                                                        .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(
                                                                                lblGamesWon)
                                                                        .addGap(11)
                                                                        .addComponent(
                                                                                lblHelms)
                                                                        .addGap(4)
                                                                        .addComponent(
                                                                                comboBoxHelms,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(4)
                                                                        .addComponent(
                                                                                btnAdd)
                                                                        .addGap(4)
                                                                        .addComponent(
                                                                                btnRemove)
                                                                        .addGap(24)
                                                                        .addComponent(
                                                                                lblPortal)
                                                                        .addGap(4)
                                                                        .addComponent(
                                                                                comboBoxPortal,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(
                                                                scrollPane,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                293,
                                                                GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(
                                                LayoutStyle.ComponentPlacement.RELATED, 11,
                                                Short.MAX_VALUE)
                                        .addComponent(btnApply)));
        contentPane.setLayout(gl_contentPane);
    }

    private void setText() {
        lblTime.setText("Time Running : " + Variables.time.toElapsedString());
        lblNextActionIn.setText("Next Action in : " + Idle.getTimer().toRemainingString());
        lblGamesPlayed.setText("Games Played : " + Variables.gamesPlayed);
        lblGamesWon.setText("Games Won : " + Variables.gamesWon);
    }
}
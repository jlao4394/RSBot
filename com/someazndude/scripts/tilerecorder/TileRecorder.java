package com.someazndude.scripts.tilerecorder;

import com.someazndude.scripts.tilerecorder.gui.Gui;
import com.someazndude.scripts.tilerecorder.gui.Gui2;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.TilePath;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Manifest(authors = {"someazndude"}, name = "Tile Recorder", description = "Gets Tiles, saves them in %temp%/Tile Recorder", version = 1.0)
public class TileRecorder extends ActiveScript {

    @Override
    public int loop() {

        if (Game.getClientState() != 11) {
            return 1000;
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (Variables.gui != null) {
                    if (!Variables.gui.isVisible()) {
                        Variables.gui.setVisible(true);
                    }
                } else {
                    Variables.gui = new Gui();
                    Variables.gui2 = new Gui2();
                }
            }
        });

        if (Variables.recording) {

            Tile last = Players.getLocal().getLocation();

            DefaultTableModel tableModel = (DefaultTableModel) Variables.gui.getTable();

            while (Variables.recording) {
                if (Calculations.distanceTo(last) > 5 && Game.getClientState() == 11 && Calculations.distanceTo(last) < 10) {
                    tableModel.addRow(new String[]{Players.getLocal().getLocation().toString(), String.valueOf(Variables.startTile.getX() - Players.getLocal().getLocation().getX()), String.valueOf(Variables.startTile.getY() - Players.getLocal().getLocation().getY()), String.valueOf(Players.getLocal().getLocation().getPlane())});
                    last = Players.getLocal().getLocation();
                }
            }
        } else if (Variables.forward.size() + Variables.backward.size() != 0 && Players.getLocal().isIdle()) {

            TilePath forward = new TilePath(Variables.forward.toArray(new Tile[Variables.forward.size()]));
            TilePath backward = new TilePath(Variables.backward.toArray(new Tile[Variables.backward.size()]));

            if (Calculations.distanceTo(forward.getEnd()) > 5) {
                while (Calculations.distanceTo(forward.getEnd()) > 5 && Variables.forward.size() + Variables.backward.size() != 0) {
                    forward.traverse();
                }
            } else if (Calculations.distanceTo(backward.getEnd()) > 5) {
                while (Calculations.distanceTo(backward.getEnd()) > 5 && Variables.forward.size() + Variables.backward.size() != 0) {
                    backward.traverse();
                }
            }
        }

        return 200;
    }

    @Override
    public void onStop() {
        Variables.gui.dispose();
        Variables.recording = false;
        Variables.forward.clear();
        Variables.backward.clear();
    }
}

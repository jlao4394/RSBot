package com.someazndude.scripts.tilerecorder;

import com.someazndude.scripts.tilerecorder.gui.Gui;
import com.someazndude.scripts.tilerecorder.gui.Gui2;
import org.powerbot.game.api.wrappers.Tile;

import java.util.LinkedList;

public class Variables {

    public static Gui gui;
    public static Gui2 gui2;
    public static Tile startTile = null;

    public static boolean recording = false;
    public static final LinkedList<Tile> forward = new LinkedList<>();
    public static final LinkedList<Tile> backward = new LinkedList<>();

}

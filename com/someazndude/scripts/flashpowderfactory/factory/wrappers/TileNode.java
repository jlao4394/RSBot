package com.someazndude.scripts.flashpowderfactory.factory.wrappers;

import com.someazndude.scripts.flashpowderfactory.factory.interfaces.Node;
import org.powerbot.game.api.wrappers.Tile;

public class TileNode implements Node {
    private final Tile tile;
    private TileNode parent;
    private final double fScore;
    private final double gScore;
    private final double hScore;

    public TileNode(final Tile tile, final TileNode parent, final double fScore, final double gScore, final double hScore) {
        this.tile = tile;
        this.parent = parent;
        this.fScore = fScore;
        this.gScore = gScore;
        this.hScore = hScore;
    }

    public Tile getTile() {
        return tile;
    }

    public TileNode getParent() {
        return parent;
    }

    @Override
    public double getFScore() {
        return fScore;
    }

    @Override
    public double getGScore() {
        return gScore;
    }

    @Override
    public double getHScore() {
        return hScore;
    }

    public void setParent(final TileNode parent) {
        this.parent = parent;
    }
}

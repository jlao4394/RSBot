package com.someazndude.scripts.flashpowderfactory.factory.wrappers;

import com.someazndude.scripts.flashpowderfactory.factory.interfaces.Node;

public class RoomNode implements Node {
    private final Room room;
    private RoomNode parent;
    private final double fScore;
    private final double hScore;
    private final double gScore;

    public RoomNode(Room room, final RoomNode parent, final double fScore, final double gScore, final double hScore) {
        this.room = room;
        this.parent = parent;
        this.fScore = fScore;
        this.gScore = gScore;
        this.hScore = hScore;
    }

    public Room getRoom() {
        return room;
    }

    public RoomNode getParent() {
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

    public void setParent(final RoomNode parent) {
        this.parent = parent;
    }
}

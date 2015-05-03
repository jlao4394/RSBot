package com.someazndude.scripts.flashpowderfactory.factory.wrappers;

import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.util.HashMap;

public class Room {
    private final Tile[] tiles;
    private final SceneObject[] obstacles;
    private final HashMap<Room, SceneObject> connectedRooms = new HashMap<>();

    public Room(final Tile[] tiles, final SceneObject[] obstacles) {
        this.tiles = tiles;
        this.obstacles = obstacles;
    }

    public void connect(final Room room, final SceneObject connector) {
        connectedRooms.put(room, connector);
    }

    public Room[] getConnectedRooms() {
        return connectedRooms.keySet().toArray(new Room[connectedRooms.size()]);
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public boolean contains(final Tile tile) {
        for (Tile t : tiles) {
            if (tile.equals(t)) {
                return true;
            }
        }
        return false;
    }

    public SceneObject[] getObstacles() {
        return obstacles;
    }

    public Tile getCenterTile() {
        return tiles[0];
    }

    public boolean isConnected(final Room room) {
        return connectedRooms.containsKey(room);
    }

    public SceneObject getRoomConnector(final Room connectedRoom) {
        if (!isConnected(connectedRoom)) {
            return null;
        } else return connectedRooms.get(connectedRoom);
    }
}

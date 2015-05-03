package com.someazndude.scripts.flashpowderfactory.factory.methods;

import com.someazndude.scripts.api.methods.util.Util;
import com.someazndude.scripts.flashpowderfactory.factory.constants.CallButton;
import com.someazndude.scripts.flashpowderfactory.factory.constants.Flag;
import com.someazndude.scripts.flashpowderfactory.factory.wrappers.Room;
import com.someazndude.scripts.flashpowderfactory.factory.wrappers.TileNode;
import com.someazndude.scripts.flashpowderfactory.nodes.PlayFactory;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.util.*;

public class RoomTiles {
    private static SceneObject door;
    private static SceneObject callButton;
    private static Map<Tile, Map<Room, Tile[]>> cache = new Hashtable<>();

    private static final Filter<SceneObject> CALL_BUTTON_FILTER = new Filter<SceneObject>() {
        @Override
        public boolean accept(SceneObject sceneObject) {
            for (int i : CallButton.UNLOCKED.getIds()) {
                if (sceneObject.getId() == i && Calculations.distance(sceneObject, door) <= 2) {
                    return true;
                }
            }
            return false;
        }
    };

    public static boolean isWalkable(final Tile tile) {
        return (0 & Flag.WALKABLE) == 0;
    }

    private static boolean containsTile(List<TileNode> open, Tile tile) {
        for (TileNode tileNode : open) {
            if (tileNode.getTile().equals(tile)) {
                return true;
            }
        }
        return false;
    }


    public static boolean canReach(final Room room, final Tile destination) {
        door = null;
        callButton = null;

        if (room == null || destination == null) {
            return false;
        }

        Tile[] path = new Tile[0];

        if (!destination.equals(Players.getLocal().getLocation()) || cache.get(destination) == null || cache.get(destination).get(room) == null) {
            LinkedList<Tile> tilePath = new LinkedList<>();
            TileNode start = new TileNode(room.getCenterTile(), null, 0, 0, 0);

            Tile destinationTile;
            if (!PlayFactory.map.contains(destination)) {
                destinationTile = Util.closestTile(room.getTiles(), destination);
            } else destinationTile = destination;

            List<TileNode> open = new ArrayList<>();
            open.add(start);

            List<Tile> closed = new ArrayList<>();

            int destinationX = destination.getX();
            int destinationY = destination.getY();
            int distanceStartToGoalX = room.getCenterTile().getX() - destination.getX();
            int distanceStartToGoalY = room.getCenterTile().getY() - destination.getY();

            outer:
            while (!open.isEmpty()) {
                TileNode current = null;
                for (TileNode tileNode : open) {
                    if (current != null) {
                        if (tileNode.getFScore() < current.getFScore() || (tileNode.getFScore() == current.getFScore() && tileNode.getHScore() < current.getHScore())) {
                            current = tileNode;
                        }
                    } else current = tileNode;
                }

                if (current != null) {
                    open.remove(current);
                    closed.add(current.getTile());

                    inner:
                    for (final Tile tile : Util.get8SurroundingTiles(current.getTile())) {
                        if (PlayFactory.map.contains(tile)) {
                            if (!closed.contains(tile)) {
                                if (Math.abs(tile.getX() - current.getTile().getX()) == 1 && Math.abs(tile.getY() - current.getTile().getY()) == 1) {
                                    for (Tile t : Util.get4SurroundingTiles(tile)) {
                                        if (!PlayFactory.map.contains(t)) {
                                            for (Tile t2 : Util.get4SurroundingTiles(current.getTile())) {
                                                if (t.equals(t2)) {
                                                    continue inner;
                                                }
                                            }
                                        }
                                    }
                                }

                                double gScore = current.getGScore() + 1;
                                double hScore = Util.manhattanDistance(tile, destination) + (Math.abs(((tile.getX() - destinationX) * distanceStartToGoalY) - ((tile.getY() - destinationY) * distanceStartToGoalX)) * 0.001);
                                double fScore = gScore + hScore;
                                TileNode tileNode = new TileNode(tile, current, fScore, gScore, hScore);

                                if (!containsTile(open, tile)) {
                                    if (!tile.equals(destinationTile)) {
                                        open.add(tileNode);
                                    } else {
                                        while (tileNode.getParent() != null) {
                                            tilePath.addFirst(tileNode.getTile());
                                            tileNode = tileNode.getParent();
                                        }
                                        path = tilePath.toArray(new Tile[tilePath.size()]);
                                        break outer;
                                    }
                                } else if (current.getGScore() < tileNode.getParent().getGScore()) {
                                    tileNode.setParent(current);
                                }
                            }
                        }
                    }
                }
            }
        } else path = cache.get(destination).get(room);

        if (path.length != 0) {
            if (!destination.equals(Players.getLocal().getLocation())) {
                if (!cache.containsKey(destination)) {
                    final Map<Room, Tile[]> hashMap = new Hashtable<>();
                    hashMap.put(room, path);
                    cache.put(destination, hashMap);
                } else if (!cache.get(destination).containsKey(room)) {
                    cache.get(destination).put(room, path);
                }
            }

            for (Tile tile : path) {
                SceneObject sceneObject = SceneEntities.getAt(tile);
                if (!tile.equals(destination) && (sceneObject != null && (sceneObject.getId() == 64674 || sceneObject.getId() == 64675))) {
                    door = sceneObject;
                    callButton = SceneEntities.getNearest(CALL_BUTTON_FILTER);
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    public static void clearCache() {
        cache = new Hashtable<>();
    }

    public static SceneObject getBlockingDoor() {
        return door;
    }

    public static SceneObject getCallButton() {
        return callButton;
    }
}

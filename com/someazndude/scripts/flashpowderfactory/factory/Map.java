package com.someazndude.scripts.flashpowderfactory.factory;

import com.someazndude.scripts.api.methods.util.Util;
import com.someazndude.scripts.flashpowderfactory.factory.constants.Machine;
import com.someazndude.scripts.flashpowderfactory.factory.constants.Obstacle;
import com.someazndude.scripts.flashpowderfactory.factory.methods.RoomTiles;
import com.someazndude.scripts.flashpowderfactory.factory.wrappers.Room;
import com.someazndude.scripts.flashpowderfactory.factory.wrappers.RoomNode;
import com.someazndude.scripts.flashpowderfactory.nodes.PlayFactory;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.HintArrow;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Map {
    private List<Room> rooms;
    private Tile mapBase;
    private List<Room> path;

    private Room lastRoom;

    public Map() {
        build();
    }

    public Room[] getRooms() {
        return rooms.toArray(new Room[rooms.size()]);
    }

    public Tile[] getTiles() {
        List<Tile> tiles = new ArrayList<>();
        for (Room room : rooms) {
            for (Tile tile : room.getTiles()) {
                tiles.add(tile);
            }
        }
        return tiles.toArray(new Tile[tiles.size()]);
    }

    public Tile getMapBase() {
        return mapBase;
    }

    public boolean contains(final Room room) {
        return rooms.contains(room);
    }

    public boolean contains(final Tile tile) {
        for (Room room : rooms) {
            for (Tile t : room.getTiles()) {
                if (t.equals(tile)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void build() {
        rooms = new ArrayList<>();
        mapBase = Game.getMapBase();

        Queue<SceneObject> machines = new LinkedList<>();
        for (SceneObject sceneObject : SceneEntities.getLoaded(Machine.ALL_MACHINES.getIds())) {
            machines.add(sceneObject);
        }

        while (!machines.isEmpty()) {
            SceneObject nextMachine = machines.poll();

            Queue<Tile> tiles = new LinkedList<>();
            tiles.add(nextMachine.getLocation());

            List<Tile> walkable = new ArrayList<>();
            List<Tile> used = new ArrayList<>();

            List<SceneObject> obstacles = new ArrayList<>();

            while (!tiles.isEmpty()) {
                Tile current = tiles.poll();
                walkable.add(current);

                for (Tile tile : Util.get8SurroundingTiles(current)) {
                    if (!used.contains(tile) && RoomTiles.isWalkable(tile)) {
                        SceneObject sceneObject = SceneEntities.getAt(tile);
                        if (sceneObject == null || sceneObject.getId() == 64676 || sceneObject.getId() == 64675 || sceneObject.getId() == 64674 || sceneObject.getId() == 64584 || sceneObject.getId() == 64687 || sceneObject.getId() == 64693 || sceneObject.getId() == 64685 || sceneObject.getId() == 64686 || sceneObject.getId() == 64688 || sceneObject.getId() == 64691 || sceneObject.getId() == 64692 || sceneObject.getId() == 64585 || sceneObject.getId() == 64693 || sceneObject.getId() == 64690 || sceneObject.getId() == 64679 || sceneObject.getId() == 64680 || sceneObject.getId() == 64678 || sceneObject.getId() == 64677 || sceneObject.getId() == 64681 || sceneObject.getId() == 64682 || sceneObject.getId() == 64683 || sceneObject.getId() == 64689 || sceneObject.getId() == 64684) {
                            tiles.add(tile);
                            used.add(tile);
                        } else {
                            for (int i : Obstacle.ALL_OBSTACLES.getIds()) {
                                if (sceneObject.getId() == i && !obstacles.contains(sceneObject)) {
                                    obstacles.add(sceneObject);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            rooms.add(new Room(walkable.toArray(new Tile[walkable.size()]), obstacles.toArray(new SceneObject[obstacles.size()])));
        }

        for (Room room1 : rooms) {
            for (SceneObject sceneObject1 : room1.getObstacles()) {
                outer:
                for (Room room2 : rooms) {
                    for (SceneObject sceneObject2 : room2.getObstacles()) {
                        if (!room1.equals(room2) && sceneObject1.equals(sceneObject2)) {
                            room1.connect(room2, sceneObject1);
                            break outer;
                        }
                    }
                }
            }
        }
    }

    public Room getPlayerRoom() {
        for (Room room : rooms) {
            if (room.contains(Players.getLocal().getLocation())) {
                return room;
            }
        }

        return null;
    }

    private boolean containsRoom(List<RoomNode> open, Room room) {
        for (RoomNode roomNode : open) {
            if (roomNode.getRoom().equals(room)) {
                return true;
            }
        }

        return false;
    }

    public List<Room> getPath() {
        return path;
    }

    public void setMachineRoom() {
        if (path != null) {
            path.clear();
            path.add(getPlayerRoom());
        }
    }

    public void setLastRoom(Room room) {
        lastRoom = room;
    }

    private Room[] getRoomPath(final Room destination) {
        if (getPlayerRoom().equals(destination)) {
            return new Room[]{getPlayerRoom()};
        }

        path = null;

        RoomNode start = new RoomNode(getPlayerRoom(), null, 0, 0, 0);

        final List<RoomNode> open = new ArrayList<>();
        open.add(start);

        final List<Room> closed = new ArrayList<>();
        if (lastRoom != null) {
            closed.add(lastRoom);
        }

        final LinkedList<Room> roomPath = new LinkedList<>();

        outer:
        while (!open.isEmpty()) {
            RoomNode current = null;
            for (RoomNode roomNode : open) {
                if (current != null) {
                    if (roomNode.getFScore() < current.getFScore() || (roomNode.getFScore() == current.getFScore() && roomNode.getHScore() < current.getHScore())) {
                        current = roomNode;
                    }
                } else current = roomNode;
            }

            if (current != null) {
                open.remove(current);
                closed.add(current.getRoom());

                for (final Room room : current.getRoom().getConnectedRooms()) {
                    if (!closed.contains(room)) {
                        double gScore = current.getParent() == null ? Calculations.distance(Players.getLocal(), current.getRoom().getRoomConnector(room)) : current.getGScore() + 1;
                        double hScore = Calculations.distance(room.getCenterTile(), destination.getCenterTile());
                        double fScore = gScore + hScore;
                        RoomNode roomNode = new RoomNode(room, current, fScore, gScore, hScore);

                        if (!containsRoom(open, room)) {
                            SceneObject catalystMachine = SceneEntities.getNearest(new Filter<SceneObject>() {
                                @Override
                                public boolean accept(SceneObject sceneObject) {
                                    for (int i : Machine.CATALYST_MACHINE.getIds()) {
                                        if (sceneObject.getId() == i && room.contains(sceneObject.getLocation())) {
                                            return true;
                                        }
                                    }
                                    return false;
                                }
                            });

                            if (RoomTiles.canReach(current.getRoom(), current.getRoom().getRoomConnector(room).getLocation()) || (RoomTiles.getCallButton() != null && catalystMachine == null)) {
                                if (!room.equals(destination)) {
                                    open.add(roomNode);
                                } else {
                                    while (roomNode.getParent() != null) {
                                        roomPath.addFirst(roomNode.getRoom());
                                        roomNode = roomNode.getParent();
                                    }
                                    break outer;
                                }
                            }
                        } else if (current.getGScore() < roomNode.getParent().getGScore()) {
                            roomNode.setParent(current);
                        }
                    }
                }
            }
        }

        if (roomPath.isEmpty()) {
            path = null;
            return null;
        }

        Room[] next = roomPath.toArray(new Room[roomPath.size()]);

        return next;
    }

    public Room[] getArrowRoomPath() {
        Room[] roomPath = null;
        if (Inventory.getCount(true, 22954) == 1) {
                for (final Room room : getRooms()) {
                    if (SceneEntities.getNearest(new Filter<SceneObject>() {
                        @Override
                        public boolean accept(SceneObject sceneObject) {
                            for (int i : Machine.CATALYST_MACHINE.getIds()) {
                                if (sceneObject.getId() == i && room.contains(sceneObject.getLocation())) {
                                    return true;
                                }
                            }
                            return false;
                        }
                    }) != null) {
                        roomPath = getRoomPath(room);
                    }
                }
        } else {
            for (HintArrow hintArrow : Game.getHintArrows()) {
                Room arrowRoom = null;
                for (Room room : getRooms()) {
                    if (room.contains(hintArrow.getLocation())) {
                        arrowRoom = room;
                    }
                }

                if (roomPath == null) {
                    roomPath = getRoomPath(arrowRoom);
                } else {
                    Room[] arrowRoomPath = getRoomPath(arrowRoom);
                    if (arrowRoomPath.length < roomPath.length) {
                        roomPath = arrowRoomPath;
                    }
                }
            }
        }

        if (roomPath != null) {
            List<Room> paintPath = new ArrayList<>();
            paintPath.add(getPlayerRoom());
            for (Room room : roomPath) {
                paintPath.add(room);
            }
            path = paintPath;

            return roomPath;
        } else {
            PlayFactory.clearMap();
        }

        return null;
    }
}

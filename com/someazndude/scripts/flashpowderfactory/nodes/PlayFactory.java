package com.someazndude.scripts.flashpowderfactory.nodes;

import com.someazndude.scripts.api.interfaces.Condition;
import com.someazndude.scripts.api.methods.actions.Camera;
import com.someazndude.scripts.api.methods.actions.Inventory;
import com.someazndude.scripts.api.methods.actions.walking.Walking;
import com.someazndude.scripts.api.methods.util.Wait;
import com.someazndude.scripts.flashpowderfactory.factory.Map;
import com.someazndude.scripts.flashpowderfactory.factory.constants.Machine;
import com.someazndude.scripts.flashpowderfactory.factory.constants.Obstacle;
import com.someazndude.scripts.flashpowderfactory.factory.methods.RoomTiles;
import com.someazndude.scripts.flashpowderfactory.factory.wrappers.Room;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.ChatOptions;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.ChatOption;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import java.awt.*;

public class PlayFactory extends Node {
    public static Map map;

    public static int leaveTime = 2;

    private final WidgetChild gameScreen = Widgets.get(1180, 6);
    private final WidgetChild timeRemaining = Widgets.get(1180, 49);
    private final WidgetChild canContinue = Widgets.get(1189, 1);

    private boolean checkedCharge;
    private boolean hasCharge;

    @Override
    public boolean activate() {
        return gameScreen.isOnScreen();
    }

    @Override
    public void execute() {
        if (map == null) {
            map = new Map();
        }

        final Room playerRoom = map.getPlayerRoom();

        final int time = Integer.parseInt(timeRemaining.getText().replaceAll("[^0-9]", ""));
        if (time <= leaveTime) {
            final ChatOption yes = ChatOptions.getOption("Yes");
            if (yes != null) {
                if (yes.select(true) == 0) {
                    Wait.waitFor(10000, 12000, new Condition() {
                        @Override
                        public boolean complete() {
                            return Game.getClientState() == 11 && !gameScreen.isOnScreen();
                        }

                        @Override
                        public boolean fail() {
                            return false;
                        }

                        @Override
                        public void doWhile() {
                        }
                    });
                }
            } else if (canContinue.isOnScreen()) {
                Keyboard.sendText(" ", false);
                Wait.waitFor(4000, 4500, new Condition() {
                    @Override
                    public boolean complete() {
                        return ChatOptions.getOption("Yes") != null;
                    }

                    @Override
                    public boolean fail() {
                        return false;
                    }

                    @Override
                    public void doWhile() {
                    }
                });
            } else if (Inventory.getItems(new Filter<Item>() {
                @Override
                public boolean accept(Item item) {
                    return !item.getName().toLowerCase().contains("rogue");
                }
            })[Random.nextInt(0, Inventory.getItems().length)].getWidgetChild().interact("Abandon")) {
                Wait.waitFor(4000, 4500, new Condition() {
                    @Override
                    public boolean complete() {
                        return canContinue.isOnScreen();
                    }

                    @Override
                    public boolean fail() {
                        return false;
                    }

                    @Override
                    public void doWhile() {
                    }
                });
            }
        } else if (!map.getMapBase().equals(Game.getMapBase()) || playerRoom == null) {
            clearMap();
            return;
        } else if (!RoomTiles.canReach(playerRoom, Players.getLocal().getLocation()) && RoomTiles.getBlockingDoor() != null) {
            final SceneObject door = RoomTiles.getBlockingDoor();
            if (!door.isOnScreen() || !door.getLocation().isOnScreen()) {
                Walking.walkToEntity(door);
            } else if (door.interact("Open")) {
                Wait.waitFor(500, 1000, new Condition() {
                    @Override
                    public boolean complete() {
                        return RoomTiles.canReach(playerRoom, Players.getLocal().getLocation());
                    }

                    @Override
                    public boolean fail() {
                        return ChatOptions.canContinue();
                    }

                    @Override
                    public void doWhile() {
                    }
                });
            } else Walking.walkToEntity(door);
        } else {
            final SceneObject rubble = SceneEntities.getNearest(new Filter<SceneObject>() {
                @Override
                public boolean accept(SceneObject sceneObject) {
                    return sceneObject.getId() == 64694 && playerRoom.contains(sceneObject.getLocation());
                }
            });
            if (rubble != null) {
                if (rubble.isOnScreen() && rubble.isOnScreen()) {
                    if (rubble.interact("Search")) {
                        if (Wait.waitFor(1500, 2000, new Condition() {
                            @Override
                            public boolean complete() {
                                return Players.getLocal().getAnimation() != -1;
                            }

                            @Override
                            public boolean fail() {
                                return false;
                            }

                            @Override
                            public void doWhile() {
                            }
                        })) {
                            Wait.waitFor(8000, 8500, new Condition() {
                                @Override
                                public boolean complete() {
                                    return Players.getLocal().getAnimation() == -1 && !rubble.validate();
                                }

                                @Override
                                public boolean fail() {
                                    return false;
                                }

                                @Override
                                public void doWhile() {
                                }
                            });
                        }
                    } else Walking.walkToEntity(rubble);
                } else Walking.walkToEntity(rubble);
            } else {
                final Room[] roomPath = map.getArrowRoomPath();
                if (roomPath == null) {
                    return;
                }
                final Room arrowRoom = roomPath[roomPath.length - 1];

                if (playerRoom.equals(arrowRoom) || hasCharge) {
                    if (!hasCharge) {
                        map.setMachineRoom();
                    }

                    final SceneObject machine = SceneEntities.getNearest(new Filter<SceneObject>() {
                        @Override
                        public boolean accept(SceneObject sceneObject) {
                            if (playerRoom.contains(sceneObject.getLocation())) {
                                for (int i : Machine.ALL_MACHINES.getIds()) {
                                    if (sceneObject.getId() == i) {
                                        return true;
                                    }
                                }
                            }
                            return false;
                        }
                    });
                    if (machine != null && machine.validate()) {
                        if (machine.isOnScreen() && machine.isOnScreen()) {
                            String interaction = null;
                            for (Machine m : Machine.values()) {
                                if (!m.equals(Machine.ALL_MACHINES)) {
                                    for (int i : m.getIds()) {
                                        if (machine.getId() == i) {
                                            interaction = m.getInteraction();
                                        }
                                    }
                                }
                            }
                            if (Mouse.apply(machine, new Filter<Point>() {
                                @Override
                                public boolean accept(Point point) {
                                    if (!machine.isOnScreen() || !machine.getLocation().isOnScreen()) {
                                        Camera.turnToConcurrently(machine);
                                    }
                                    return Menu.getActions().length > 2;
                                }
                            })) {
                                if (Menu.contains(interaction) && Menu.select(interaction)) {
                                    if (Wait.waitFor(1500, 2000, new Condition() {
                                        @Override
                                        public boolean complete() {
                                            return Players.getLocal().getAnimation() != -1 || Widgets.get(1186, 1).isOnScreen() || ChatOptions.canContinue() || canContinue.isOnScreen();
                                        }

                                        @Override
                                        public boolean fail() {
                                            return false;
                                        }

                                        @Override
                                        public void doWhile() {
                                        }
                                    })) {
                                        boolean waitLonger = false;
                                        for (int i : Machine.CHARGE_MACHINE.getIds()) {
                                            if (machine.getId() == i || machine.getId() == 64650) {
                                                waitLonger = true;
                                            }
                                        }

                                        if (Wait.waitFor(waitLonger ? 8000 : 2000, waitLonger ? 8500 : 2500, new Condition() {
                                            @Override
                                            public boolean complete() {
                                                return Widgets.get(1186, 1).isOnScreen() || ChatOptions.canContinue() || canContinue.isOnScreen();
                                            }

                                            @Override
                                            public boolean fail() {
                                                return false;
                                            }

                                            @Override
                                            public void doWhile() {
                                            }
                                        })) {
                                            hasCharge = false;
                                            map.setLastRoom(null);
                                        }
                                    }
                                } else {
                                    hasCharge = false;
                                }
                            }
                        } else Walking.walkToEntity(machine);
                    } else clearMap();
                } else {
                    final Room next = roomPath[0];

                    if (next == null || !playerRoom.isConnected(next)) {
                        clearMap();
                        return;
                    }

                    final SceneObject obstacle = playerRoom.getRoomConnector(next);

                    if (obstacle != null && obstacle.validate()) {
                        if (!RoomTiles.canReach(playerRoom, obstacle.getLocation())) {
                            final SceneObject callButton = RoomTiles.getCallButton();
                            if (callButton == null || !callButton.validate()) {
                                clearMap();
                                return;
                            } else {
                                if (callButton.isOnScreen() && callButton.getLocation().isOnScreen()) {
                                    if (callButton.interact("Operate")) {
                                        if (Wait.waitFor(1500, 2000, new Condition() {
                                            @Override
                                            public boolean complete() {
                                                return Players.getLocal().getAnimation() != -1;
                                            }

                                            @Override
                                            public boolean fail() {
                                                return false;
                                            }

                                            @Override
                                            public void doWhile() {
                                            }
                                        })) {
                                            Wait.waitFor(4000, 4500, new Condition() {
                                                @Override
                                                public boolean complete() {
                                                    return Players.getLocal().getAnimation() == -1 && !callButton.validate();
                                                }

                                                @Override
                                                public boolean fail() {
                                                    return false;
                                                }

                                                @Override
                                                public void doWhile() {
                                                }
                                            });
                                        }
                                    } else Walking.walkToEntity(callButton);
                                } else Walking.walkToEntity(callButton);
                            }
                        } else {
                            if (obstacle.isOnScreen() && obstacle.getLocation().isOnScreen()) {
                                String interaction = null;
                                for (Obstacle o : Obstacle.values()) {
                                    if (!o.equals(Obstacle.ALL_OBSTACLES)) {
                                        for (int i : o.getIds()) {
                                            if (obstacle.getId() == i) {
                                                interaction = o.getInteraction();
                                            }
                                        }
                                    }
                                }
                                if (obstacle.getId() != 64575 ? obstacle.interact(interaction) : obstacle.getLocation().interact(interaction)) {
                                    if (Wait.waitFor(1500, 2000, new Condition() {
                                        @Override
                                        public boolean complete() {
                                            return Players.getLocal().getAnimation() != -1 && Players.getLocal().getAnimation() != 15545;
                                        }

                                        @Override
                                        public boolean fail() {
                                            return false;
                                        }

                                        @Override
                                        public void doWhile() {
                                        }
                                    })) {
                                        final SceneObject chargeMachine = SceneEntities.getNearest(new Filter<SceneObject>() {
                                            @Override
                                            public boolean accept(SceneObject sceneObject) {
                                                for (int i : Machine.CHARGE_MACHINE.getIds()) {
                                                    if (sceneObject.getId() == i && next.contains(sceneObject.getLocation())) {
                                                        return true;
                                                    }
                                                }
                                                return false;
                                            }
                                        });

                                        if (Wait.waitFor(8000, 8500, new Condition() {
                                            @Override
                                            public boolean complete() {
                                                return Players.getLocal().getAnimation() == -1 && map.getPlayerRoom() != null && map.getPlayerRoom().equals(next);
                                            }

                                            @Override
                                            public boolean fail() {
                                                return Players.getLocal().getAnimation() == -1 && map.getPlayerRoom() != null && (map.getPlayerRoom().equals(playerRoom) || !map.getPlayerRoom().equals(next));
                                            }

                                            @Override
                                            public void doWhile() {
                                                if (chargeMachine != null && !checkedCharge) {
                                                    if (chargeMachine.isOnScreen() && chargeMachine.getLocation().isOnScreen()) {
                                                        if (Mouse.apply(chargeMachine, new Filter<Point>() {
                                                            @Override
                                                            public boolean accept(Point point) {
                                                                if (!chargeMachine.isOnScreen() || !chargeMachine.getLocation().isOnScreen()) {
                                                                    Camera.turnToConcurrently(chargeMachine);
                                                                }
                                                                return Menu.getActions().length > 2;
                                                            }
                                                        })) {
                                                            checkedCharge = true;
                                                            if (Menu.contains(Machine.CHARGE_MACHINE.getInteraction())) {
                                                                hasCharge = true;
                                                            }
                                                        }
                                                    } else Camera.turnToConcurrently(chargeMachine);
                                                }
                                            }
                                        })) {
                                            map.setLastRoom(playerRoom);
                                            checkedCharge = false;
                                        }
                                    }
                                } else Walking.walkToEntity(obstacle);
                            } else Walking.walkToEntity(obstacle);
                        }
                    } else clearMap();
                }
            }
        }
    }

    public static void clearMap() {
        map = null;
        RoomTiles.clearCache();
    }
}
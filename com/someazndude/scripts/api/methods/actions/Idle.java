package com.someazndude.scripts.api.methods.actions;

import com.someazndude.scripts.api.interfaces.Condition;
import com.someazndude.scripts.api.methods.util.Wait;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.util.ArrayList;

public class Idle {
    private static final Timer timer = new Timer(Random.nextInt(60000, 180000));

    public synchronized static void idle() {
        if (!timer.isRunning()) {
            switch (Random.nextInt(0, 4 + 1)) {
                case 0: {
                    Camera.setAngle(Random.nextInt(-180, 180 + 1));
                    resetTimer();
                    break;
                }

                case 1: {
                    ArrayList<Entity> entities = new ArrayList<>();

                    for (Player player : Players.getLoaded(new Filter<Player>() {
                        @Override
                        public boolean accept(Player player) {
                            return !player.equals(Players.getLocal()) && player.isOnScreen();
                        }
                    })) {
                        entities.add(player);
                    }

                    for (SceneObject sceneObject : SceneEntities.getLoaded(new Filter<SceneObject>() {
                        @Override
                        public boolean accept(SceneObject sceneObject) {
                            return sceneObject.isOnScreen();
                        }
                    })) {
                        entities.add(sceneObject);
                    }

                    for (GroundItem groundItem : GroundItems.getLoaded(new Filter<GroundItem>() {
                        @Override
                        public boolean accept(GroundItem groundItem) {
                            return groundItem.isOnScreen();
                        }
                    })) {
                        entities.add(groundItem);
                    }

                    for (NPC npc : NPCs.getLoaded(new Filter<NPC>() {
                        @Override
                        public boolean accept(NPC npc) {
                            return npc.isOnScreen();
                        }
                    })) {
                        entities.add(npc);
                    }

                    Entity entity = entities.get(Random.nextInt(0, entities.size()));

                    if (entity != null) {
                        if (entity.click(false)) {
                            if (Wait.waitFor(4000, 4500, new Condition() {
                                @Override
                                public boolean complete() {
                                    return Menu.isOpen();
                                }

                                @Override
                                public boolean fail() {
                                    return false;
                                }

                                @Override
                                public void doWhile() {
                                }
                            })) {
                                if (Wait.waitFor(4000, 4500, new Condition() {
                                    @Override
                                    public boolean complete() {
                                        return !Menu.isOpen();
                                    }

                                    @Override
                                    public boolean fail() {
                                        return false;
                                    }

                                    @Override
                                    public void doWhile() {
                                        Mouse.moveRandomly();
                                    }
                                })) {
                                    resetTimer();
                                }
                            }
                        }
                    }

                    break;
                }

                case 2: {
                    if (Players.getLocal().getNpcId() == -1) {

                        Tabs tab = Tabs.values()[Random.nextInt(0, 16 + 1)];

                        if (tab != null) {
                            if (!tab.isOpen()) {
                                if (tab.open()) {

                                    Task.sleep(0, 3000);

                                    if (Tabs.INVENTORY.open()) {
                                        resetTimer();
                                    }
                                }
                            }
                        }
                    }

                    break;
                }

                case 3: {
                    Mouse.moveRandomly();
                    break;
                }

                case 4: {
                    Mouse.moveToRandomSide();
                    break;
                }
            }

            switch (Random.nextInt(0, 10 + 1)) {
                case 1: {
                    Mouse.moveRandomly();
                    break;
                }

                case 2: {
                    Mouse.moveToRandomSide();
                    break;
                }
            }

        }
    }

    public static void resetTimer() {
        timer.setEndIn(Random.nextInt(60000, 180000));
    }

    public static void endTimer() {
        timer.setEndIn(0);
    }

    public static Timer getTimer() {
        return timer;
    }
}

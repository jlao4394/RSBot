package com.someazndude.scripts.shantaycooker.nodes;

import com.someazndude.scripts.api.interfaces.Condition;
import com.someazndude.scripts.api.methods.actions.*;
import com.someazndude.scripts.api.methods.util.Wait;
import com.someazndude.scripts.shantaycooker.misc.GlobalVariables;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import java.awt.*;


public class Cook extends Node {
    private SceneObject fire;
    private Item item;

    private final WidgetChild cook = Widgets.get(1370, 38);
    private final WidgetChild cancel = Widgets.get(1251, 48);

    private final Rectangle cookRectangle = new Rectangle(255, 347, 232, 38);
    private final Rectangle doneRectangle = new Rectangle(215, 209, 92, 32);

    private final int[] fireIds = new int[]{70765, 70761, 70764, 70758};

    @Override
    public boolean activate() {
        final Entity bank = Bank.getNearest();
        fire = SceneEntities.getNearest(new Filter<SceneObject>() {
            @Override
            public boolean accept(SceneObject sceneObject) {
                if (Calculations.distance((Locatable) bank, sceneObject) <= 5) {
                    for (int id : fireIds) {
                        if (sceneObject.getId() == id) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        item = Inventory.closestItem(GlobalVariables.itemId);
        return item != null && fire != null && !Bank.isOpen();
    }

    @Override
    public void execute() {
        if (cancel.isOnScreen()) {
            if (Action.interact(cancel, "Cancel")) {
                Wait.waitFor(1000, 1500, new Condition() {
                    @Override
                    public boolean complete() {
                        return !cancel.isOnScreen();
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
        } else if (!fire.isOnScreen()) {
            Camera.turnToConcurrently(fire);
        } else if (cook.isOnScreen()) {
            if (Action.interact(cook, "Make")) {
                waitWhileAnimating();
            }
        } else if (!Inventory.isItemSelected() || Inventory.getSelectedItem().getId() != GlobalVariables.itemId) {
            if (item.getWidgetChild().interact("Use")) {
                Wait.waitFor(1000, 1500, new Condition() {
                    @Override
                    public boolean complete() {
                        return Inventory.isItemSelected();
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
        } else if (!Menu.contains("Use", String.format("%s -> Fire", item.getName())) || Menu.getIndex("Use", String.format("%s -> Fire", item.getName())) > 30) {
            if (Menu.isOpen()) {
                Mouse.moveToRandomSide();
            } else if (!Mouse.apply(fire, new Filter<Point>() {
                @Override
                public boolean accept(Point point) {
                    return Menu.contains("Use", String.format("%s -> Fire", item.getName())) && Menu.getIndex("Use", String.format("%s -> Fire", item.getName())) < 30;
                }
            })) {
                Camera.turnToConcurrently(fire);
            }
        } else if (Menu.select("Use", String.format("%s -> Fire", item.getName()))) {
            if (!Wait.waitFor(1000, 1500, new Condition() {
                @Override
                public boolean complete() {
                    return cook.isOnScreen();
                }

                @Override
                public boolean fail() {
                    return Players.getLocal().getAnimation() != -1;
                }

                @Override
                public void doWhile() {
                    if (item.getName().toLowerCase().contains("raw") && Inventory.getCount() > 1) {
                        if (!cookRectangle.contains(Mouse.getLocation())) {
                            Mouse.move(Random.nextInt(cookRectangle.x, cookRectangle.x + cookRectangle.width), Random.nextInt(cookRectangle.y, cookRectangle.y + cookRectangle.height));
                        }
                    }
                }
            })) {
                if (Players.getLocal().getAnimation() != -1) {
                    waitWhileAnimating();
                }
            }
        }
    }

    private void waitWhileAnimating() {
        Timer timer = new Timer(Random.nextInt(5000, 5500));
        final int readyNumber = Random.nextInt(0, 5 + 1);
        final Entity bank = Bank.getNearest();

        while (timer.isRunning() && Inventory.getItem(GlobalVariables.itemId) != null && fire.validate()) {
            final NPC fireSpirit = NPCs.getNearest(15451);
            if (fireSpirit != null) {
                if (!fireSpirit.isOnScreen()) {
                    Camera.turnToConcurrently(fireSpirit);
                } else if (!Menu.contains("Collect") || !(Menu.getIndex("Collect") < 30)) {
                    if (Menu.isOpen()) {
                        Mouse.moveRandomly();
                    } else if (!Mouse.apply(fireSpirit, new Filter<Point>() {
                        @Override
                        public boolean accept(Point point) {
                            return Menu.contains("Collect") && Menu.getIndex("Collect") < 30;
                        }
                    })) {
                        Camera.turnToConcurrently(fireSpirit);
                    }
                } else if (Menu.select("Collect")) {
                    if (!Wait.waitFor(1000, 1500, new Condition() {
                        @Override
                        public boolean complete() {
                            return !fireSpirit.validate();
                        }

                        @Override
                        public boolean fail() {
                            return false;
                        }

                        @Override
                        public void doWhile() {
                        }
                    })) {
                        return;
                    }
                }
            }

            if (Inventory.getCount(GlobalVariables.itemId) > readyNumber) {
                Idle.idle();
            } else if (cancel.isOnScreen() && !doneRectangle.contains(Mouse.getLocation())) {
                Mouse.move(Random.nextInt(doneRectangle.x, doneRectangle.x + doneRectangle.width), Random.nextInt(doneRectangle.y, doneRectangle.y + doneRectangle.height));
            } else if (!bank.isOnScreen()) {
                Camera.turnToConcurrently((Locatable) bank);
            } else if (!bank.contains(Mouse.getLocation())) {
                if (!Mouse.apply(bank, new Filter<Point>() {
                    @Override
                    public boolean accept(Point point) {
                        return (Menu.contains("Open") || Menu.contains("Bank")) && (Menu.getIndex("Open") < 30 || Menu.getIndex("Bank") < 30);
                    }
                })) {
                    Camera.turnToConcurrently((Locatable) bank);
                }
            }

            if (Players.getLocal().getAnimation() != -1) {
                timer.reset();
            }
        }
    }
}
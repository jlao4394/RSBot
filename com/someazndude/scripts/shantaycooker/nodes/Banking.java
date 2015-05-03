package com.someazndude.scripts.shantaycooker.nodes;

import com.someazndude.scripts.api.interfaces.Condition;
import com.someazndude.scripts.api.methods.actions.Action;
import com.someazndude.scripts.api.methods.actions.Bank;
import com.someazndude.scripts.api.methods.actions.Camera;
import com.someazndude.scripts.api.methods.actions.Inventory;
import com.someazndude.scripts.api.methods.util.Wait;
import com.someazndude.scripts.shantaycooker.misc.GlobalVariables;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import java.awt.*;

public class Banking extends Node {
    private Item item;

    private final WidgetChild done = Widgets.get(1251, 54);
    private final WidgetChild close = Widgets.get(762, 45);
    private final WidgetChild depositInventory = Widgets.get(762, 34);
    private final WidgetChild bankWidget = Widgets.get(762, 95);

    private final Rectangle depositInventoryRectangle = new Rectangle(355, 349, 29, 21);

    @Override
    public boolean activate() {
        item = Inventory.getItem(GlobalVariables.itemId);
        return item == null || Bank.isOpen();
    }

    @Override
    public void execute() {
        final Item bankItem = Bank.getItem(GlobalVariables.itemId);
        final Entity bank = Bank.getNearest();

        if (item != null) {
            if (Action.interact(close, "Close")) {
                Wait.waitFor(4000, 4500, new Condition() {
                    @Override
                    public boolean complete() {
                        return !Bank.isOpen();
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
        } else if (!Bank.isOpen()) {
            if (done.isOnScreen()) {
                if (Action.interact(done, "Close")) {
                    Wait.waitFor(1000, 1500, new Condition() {
                        @Override
                        public boolean complete() {
                            return !done.isOnScreen();
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
            } else if (Inventory.isItemSelected()) {
                if (Inventory.getSelectedItem().getWidgetChild().interact("Use")) {
                    Wait.waitFor(1000, 1500, new Condition() {
                        @Override
                        public boolean complete() {
                            return !Inventory.isItemSelected();
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
            } else if (!bank.isOnScreen()) {
                Camera.turnToConcurrently((Locatable) bank);
            } else if (!(Menu.contains("Open") || Menu.contains("Bank")) || (Menu.getIndex("Open") > 30 || Menu.getIndex("Bank") > 30)) {
                if (!Mouse.apply(bank, new Filter<Point>() {
                    @Override
                    public boolean accept(Point point) {
                        return (Menu.contains("Open") || Menu.contains("Bank")) && (Menu.getIndex("Open") < 30 || Menu.getIndex("Bank") < 30);
                    }
                })) {
                    Camera.turnToConcurrently((Locatable) bank);
                }
            } else if (Menu.select("Open") || Menu.select("Bank")) {
                Wait.waitFor(1000, 1500, new Condition() {
                    @Override
                    public boolean complete() {
                        return Bank.isOpen();
                    }

                    @Override
                    public boolean fail() {
                        return false;
                    }

                    @Override
                    public void doWhile() {
                        if (Inventory.getCount() != 0 && !depositInventoryRectangle.contains(Mouse.getLocation())) {
                            Mouse.move(Random.nextInt(depositInventoryRectangle.x, depositInventoryRectangle.x + depositInventoryRectangle.width), Random.nextInt(depositInventoryRectangle.y, depositInventoryRectangle.y + depositInventoryRectangle.height));
                        }
                    }
                });
            }
        } else if (Inventory.getCount() != 0) {
            if (Action.interact(depositInventory, "Deposit carried items")) {
                Wait.waitFor(4000, 4500, new Condition() {
                    @Override
                    public boolean complete() {
                        return Inventory.getCount() == 0;
                    }

                    @Override
                    public boolean fail() {
                        return false;
                    }

                    @Override
                    public void doWhile() {
                        if (!bankWidget.contains(bankItem.getWidgetChild().getNextViewportPoint())) {
                            Bank.scrollTo(bankItem);
                        } else if (!bankItem.getWidgetChild().contains(Mouse.getLocation())) {
                            bankItem.getWidgetChild().hover();
                        }
                    }
                });
            }
        } else if (Bank.customWithdraw(bankItem, 99, false)) {
            Wait.waitFor(4000, 4500, new Condition() {
                @Override
                public boolean complete() {
                    return Inventory.getItem(GlobalVariables.itemId) != null;
                }

                @Override
                public boolean fail() {
                    return false;
                }

                @Override
                public void doWhile() {
                    if (!close.contains(Mouse.getLocation())) {
                        close.hover();
                    }
                }
            });
        }
    }
}
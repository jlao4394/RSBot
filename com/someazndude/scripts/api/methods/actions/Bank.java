package com.someazndude.scripts.api.methods.actions;

import com.someazndude.scripts.api.interfaces.Condition;
import com.someazndude.scripts.api.methods.util.Util;
import com.someazndude.scripts.api.methods.util.Wait;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class Bank extends org.powerbot.game.api.methods.widget.Bank {

    public static boolean customDeposit(final Item item, final int amount) {
        if (item != null && amount > 0) {
            int itemAmount = Inventory.getCount(true, item.getId());

            if (Inventory.getCount(true) == itemAmount) {
                depositInventory();
            } else if (amount == 1) {
                return item.getWidgetChild().interact("Deposit");
            } else if (itemAmount <= amount) {
                return item.getWidgetChild().interact("Deposit-All");
            } else if (item.getWidgetChild().contains(Mouse.getLocation())) {
                if (Menu.contains("Deposit-" + String.valueOf(amount))) {
                    return item.getWidgetChild().interact("Deposit-" + String.valueOf(amount));
                } else if (item.getWidgetChild().interact("Deposit-X")) {
                    if (Wait.waitFor(4000, 4500, new Condition() {
                        @Override
                        public boolean complete() {
                            return Widgets.get(752, 3).isOnScreen();
                        }

                        @Override
                        public boolean fail() {
                            return false;
                        }

                        @Override
                        public void doWhile() {
                        }
                    })) {
                        Keyboard.sendText(String.valueOf(amount), true);
                        return true;
                    }
                }
            } else item.getWidgetChild().hover();
        }

        return false;
    }

    public static boolean customWithdraw(final Item item, final int amount, final boolean stackable) {
        final WidgetChild bank = Widgets.get(762, 95);

        if (item != null) {
            //  if (Bank.getCurrentTab().equals(Bank.Tab.ALL)) {
            if (bank.contains(item.getWidgetChild().getNextViewportPoint())) {
                if (amount == 0) {
                    return false;
                } else if (amount == 1) {
                    return Action.interact(item.getWidgetChild(), "Withdraw");
                } else if (amount >= 28 - Inventory.getCount() && !stackable) {
                    return Action.interact(item.getWidgetChild(), "Withdraw-All");
                } else if (item.getWidgetChild().contains(Mouse.getLocation())) {
                    if (Menu.contains("Withdraw-" + String.valueOf(amount))) {
                        return Action.interact(item.getWidgetChild(), "Withdraw-" + String.valueOf(amount));
                    } else if (Action.interact(item.getWidgetChild(), "Withdraw-X")) {
                        if (Wait.waitFor(4000, 4500, new Condition() {
                            @Override
                            public boolean complete() {
                                return Widgets.get(752, 3).isOnScreen();
                            }

                            @Override
                            public boolean fail() {
                                return false;
                            }

                            @Override
                            public void doWhile() {
                            }
                        })) {
                            Keyboard.sendText(String.valueOf(amount), true);
                            return true;
                        }
                    }
                } else item.getWidgetChild().hover();
            } else scrollTo(item);
            //  } else Bank.setCurrentTab(Bank.Tab.ALL);
        }

        return false;
    }

    public static void scrollTo(Item item) {
        final WidgetChild bank = Widgets.get(762, 95);

        if (bank.contains(Mouse.getLocation())) {
            if (item.getWidgetChild().getCentralPoint().getY() < bank.getCentralPoint().getY()) {
                Mouse.scroll(false);
                Task.sleep(100, 500);
            } else {
                Mouse.scroll(true);
                Task.sleep(100, 500);
            }
        } else bank.hover();
    }

    public static Item closestItem(final int... ids) {
        return closestItem(new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                for (int i : ids) {
                    if (item.getId() == i) {
                        return true;
                    }
                }

                return false;
            }
        });
    }

    public static Item closestItem(final Filter<Item> itemFilter) {
        Item closest = null;

        for (Item item : Bank.getItems(itemFilter)) {
            if (closest != null) {
                if (Util.distanceBetween(Mouse.getLocation(), item.getWidgetChild().getNextViewportPoint()) < Util.distanceBetween(Mouse.getLocation(), closest.getWidgetChild().getNextViewportPoint())) {
                    closest = item;
                }
            } else closest = item;
        }

        return closest;
    }

}

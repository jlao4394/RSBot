package com.someazndude.scripts.api.methods.actions;

import com.someazndude.scripts.api.methods.util.Util;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.Item;

public class Inventory extends org.powerbot.game.api.methods.tab.Inventory {

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
        for (Item item : Inventory.getItems(itemFilter)) {
            if (closest != null) {
                if (Util.distanceBetween(Mouse.getLocation(), item.getWidgetChild().getCentralPoint()) < Util.distanceBetween(Mouse.getLocation(), closest.getWidgetChild().getCentralPoint())) {
                    closest = item;
                }
            } else closest = item;
        }

        return closest;
    }
}

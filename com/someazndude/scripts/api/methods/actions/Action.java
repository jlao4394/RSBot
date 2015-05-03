package com.someazndude.scripts.api.methods.actions;

import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.wrappers.Entity;

public class Action {
    public static boolean interact(Entity entity, final String action) {
        return interact(entity, action, null);
    }

    public static boolean interact(final Entity entity, final String action, final String option) {
        if (entity.contains(Mouse.getLocation()) && Menu.contains(action)) {
            return option == null ? Menu.select(action) : Menu.select(action, option);
        } else return option == null ? entity.interact(action) : entity.interact(action, option);
    }
}

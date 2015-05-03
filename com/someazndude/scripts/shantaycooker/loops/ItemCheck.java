package com.someazndude.scripts.shantaycooker.loops;

import com.someazndude.scripts.api.methods.actions.Inventory;
import com.someazndude.scripts.shantaycooker.misc.GlobalVariables;
import com.someazndude.scripts.shantaycooker.misc.Paint;
import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Tabs;

public class ItemCheck {
    public static int itemCheckTab;

    public final LoopTask itemCheck = new LoopTask() {
        @Override
        public int loop() {
            int amount = Tabs.INVENTORY.isOpen() ? Inventory.getCount(GlobalVariables.itemId) : 28;
            if (amount < itemCheckTab) {
                Paint.itemsUsed += itemCheckTab - amount;
            }
            itemCheckTab = amount;

            return 200;
        }
    };
}

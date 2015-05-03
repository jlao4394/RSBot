package com.someazndude.scripts.api.methods.actions;

import org.powerbot.game.api.methods.Widgets;

public class Misc {
    public static String getContinueText() {
        return Widgets.get(1184, 13).getText();
    }
}

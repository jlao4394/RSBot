package com.someazndude.scripts.api.methods.util;

import com.someazndude.scripts.api.interfaces.Condition;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;

public class Wait {
    public static boolean waitFor(final int waitMin, final int waitMax, final Condition condition) {

        Timer t = new Timer(Random.nextInt(waitMin, waitMax + 1));

        while (t.isRunning()) {
            if (Game.getClientState() == 11 && Players.getLocal().isMoving()) {
                t.reset();
            }

            if (condition.complete()) {
                Task.sleep(0, 250);
                return true;
            } else if (condition.fail()) {
                Task.sleep(0, 250);
                return false;
            } else {
                condition.doWhile();
                Task.sleep(10);
            }
        }

        return false;
    }
}

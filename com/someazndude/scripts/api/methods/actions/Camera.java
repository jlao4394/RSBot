package com.someazndude.scripts.api.methods.actions;

import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Locatable;

public class Camera extends org.powerbot.game.api.methods.widget.Camera {

    private static Locatable turnTo = null;

    public final LoopTask turn = new LoopTask() {
        @Override
        public int loop() {
            if (turnTo != null) {
                turnTo(turnTo, 90);
                turnTo = null;
            }

            if (getPitch() < 70) {
                setPitch(true);
            }

            return Random.nextInt(200, 400);
        }
    };

    public synchronized static void turnToConcurrently(final Locatable locatable) {
        if (turnTo == null) {
            turnTo = locatable;
        }
    }
}

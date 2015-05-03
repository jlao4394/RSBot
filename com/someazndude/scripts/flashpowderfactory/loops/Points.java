package com.someazndude.scripts.flashpowderfactory.loops;

import com.someazndude.scripts.flashpowderfactory.misc.Paint;
import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Settings;

public class Points extends LoopTask {
    @Override
    public int loop() {
        if (Game.getClientState() == 11) {
            if ((Settings.get(1688) & 0xFFFF) > Paint.brianPoints) {
                Paint.brianPointsAdded += (Settings.get(1688) & 0xFFFF) - Paint.brianPoints;
                Paint.brianPoints = (Settings.get(1688) & 0xFFFF);
            }
        }
        return 1000;
    }
}

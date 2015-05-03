package com.someazndude.scripts.flashpowderfactory;

import com.someazndude.scripts.api.methods.actions.Camera;
import com.someazndude.scripts.flashpowderfactory.loops.Points;
import com.someazndude.scripts.flashpowderfactory.misc.Paint;
import com.someazndude.scripts.flashpowderfactory.nodes.EnterFactory;
import com.someazndude.scripts.flashpowderfactory.nodes.PlayFactory;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.methods.widget.WidgetCache;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;

import java.awt.*;

@Manifest(authors = {"someazndude"}, name = "Some Flash Powder Factory", description = "v.1.8. Plays Flash Powder Factory.", website = "http://www.powerbot.org/community/topic/845516-some-flash-powder-factory/", version = 1.8)
public class FlashPowderFactory extends ActiveScript implements PaintListener {
    private final Tree jobContainer = new Tree(new Node[]{new EnterFactory(), new PlayFactory()});
    private Client client = Context.client();

    @Override
    public void onStart() {
        Paint.brianPoints = (Settings.get(1688) & 0xFFFF);
        getContainer().submit(new Points());
        getContainer().submit(new Camera().turn);
        if (Equipment.getAppearanceId(Equipment.Slot.BODY) == 22958 || Equipment.getAppearanceId(Equipment.Slot.BODY) == 5553) {
            PlayFactory.leaveTime = 4;
        }
        Environment.enableRandom(org.powerbot.core.randoms.WidgetCloser.class, false);
    }

    @Override
    public int loop() {
        if (Widgets.get(640, 21).visible()) {
            Widgets.get(640, 30).interact("Minimise");
        }

        if (Game.getClientState() != Game.INDEX_MAP_LOADED) {
            return 1000;
        }

        if (client != Context.client()) {
            WidgetCache.purge();
            Context.get().getEventManager().addListener(this);
            client = Context.client();
        }

        final Node job = jobContainer.state();
        if (job != null) {
            jobContainer.set(job);
            getContainer().submit(job);
            job.join();
        }

        return Random.nextInt(10, 50);
    }

    @Override
    public void onStop() {
        System.gc();
    }

    @Override
    public void onRepaint(Graphics graphics) {
        Paint.onRepaint(graphics);
    }
}

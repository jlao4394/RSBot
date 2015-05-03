package com.someazndude.scripts.castlewars;

import com.someazndude.scripts.api.methods.actions.Camera;
import com.someazndude.scripts.castlewars.methods.Portals;
import com.someazndude.scripts.castlewars.misc.Gui;
import com.someazndude.scripts.castlewars.misc.Paint;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.widget.WidgetCache;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;

import javax.swing.*;
import java.awt.*;

@Manifest(authors = {"someazndude"}, name = "Some Castle Wars AFKer", description = "v.1.5. AFKs at Castle Wars.", website = "http://www.powerbot.org/community/topic/829042-some-castle-wars-afker/", version = 1.5)
public class CastleWars extends ActiveScript implements PaintListener, MessageListener {
    public static Tree jobContainer = null;
    public static Gui gui;
    private Client client = Context.client();

    @Override
    public void onStart() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui = new Gui();
                gui.setVisible(true);
            }
        });
        getContainer().submit(new Camera().turn);

        Context.setLoginWorld(24);
    }

    @Override
    public int loop() {
        if (Game.getClientState() != Game.INDEX_MAP_LOADED) {
            return 1000;
        }

        if (client != Context.client()) {
            WidgetCache.purge();
            Context.get().getEventManager().addListener(this);
            client = Context.client();
        }

        if (jobContainer != null) {
            final Node job = jobContainer.state();
            if (job != null) {
                jobContainer.set(job);
                getContainer().submit(job);
                job.join();
            }
        }

        return Random.nextInt(200, 400);
    }

    @Override
    public void onStop() {
        gui.dispose();
    }

    @Override
    public void onRepaint(Graphics graphics) {
        Paint.onRepaint(graphics);
    }

    @Override
    public void messageReceived(MessageEvent messageEvent) {
        if (messageEvent.getSender().equals("") && messageEvent.getMessage().contains("balance")) {
            Portals.failTick++;
        }
    }
}

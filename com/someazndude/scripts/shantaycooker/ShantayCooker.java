package com.someazndude.scripts.shantaycooker;

import com.someazndude.scripts.api.methods.actions.Camera;
import com.someazndude.scripts.shantaycooker.loops.ItemCheck;
import com.someazndude.scripts.shantaycooker.misc.GlobalVariables;
import com.someazndude.scripts.shantaycooker.misc.Gui;
import com.someazndude.scripts.shantaycooker.misc.Paint;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.widget.WidgetCache;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

@Manifest(authors = {"someazndude"}, name = "Some Bonfire Item User", description = "Uses items on a bonfire. Start near a bonfire and a bank.", website = "", version = 1.0)
public class ShantayCooker extends ActiveScript implements PaintListener {
    private static Tree jobContainer;
    private static Properties properties;
    private static File path;

    private Client client = Context.client();
    private Gui gui;

    @Override
    public void onStart() {
        path = new File(Environment.getStorageDirectory(), "settings.ini");
        properties = new Properties();

        try {
            properties.load(new FileReader(path));
            GlobalVariables.itemId = Integer.parseInt(String.valueOf(properties.get("id")));
        } catch (IOException e) {
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui = new Gui();
                gui.setVisible(true);
            }
        });

        getContainer().submit(new Camera().turn);
        getContainer().submit(new ItemCheck().itemCheck);
        Context.setLoginWorld(100);
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

        return Random.nextInt(10, 50);
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onRepaint(Graphics graphics) {
        Paint.onRepaint(graphics);
    }

    public static void putProperties(Object key, Object value) {
        properties.put(key, value);
    }

    public static void storeProperties() {
        try {
            properties.store(new FileWriter(path), "settings");
        } catch (IOException e) {
        }
    }

    public static void setJobContainer(Tree tree) {
        jobContainer = tree;
    }
}

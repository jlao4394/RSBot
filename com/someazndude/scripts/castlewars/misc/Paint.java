package com.someazndude.scripts.castlewars.misc;

import org.powerbot.game.api.methods.input.Mouse;

import java.awt.*;

public class Paint {

    public static void onRepaint(Graphics graphics) {

        Graphics2D g = (Graphics2D) graphics;

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));

        g.drawLine(Mouse.getX() + 10 + 1, Mouse.getY() + 1, Mouse.getX() - 10 + 1, Mouse.getY() + 1);
        g.drawLine(Mouse.getX() + 10 + 1, Mouse.getY() - 1, Mouse.getX() - 10 + 1, Mouse.getY() + 1);
        g.drawLine(Mouse.getX() + 10 + 1, Mouse.getY() + 1, Mouse.getX() - 10 - 1, Mouse.getY() + 1);
        g.drawLine(Mouse.getX() + 10 + 1, Mouse.getY() + 1, Mouse.getX() - 10 + 1, Mouse.getY() - 1);
        g.drawLine(Mouse.getX() + 10 - 1, Mouse.getY() + 1, Mouse.getX() - 10 + 1, Mouse.getY() + 1);
        g.drawLine(Mouse.getX() + 10 - 1, Mouse.getY() - 1, Mouse.getX() - 10 + 1, Mouse.getY() + 1);
        g.drawLine(Mouse.getX() + 10 - 1, Mouse.getY() + 1, Mouse.getX() - 10 - 1, Mouse.getY() + 1);
        g.drawLine(Mouse.getX() + 10 - 1, Mouse.getY() + 1, Mouse.getX() - 10 + 1, Mouse.getY() - 1);
        g.drawLine(Mouse.getX() + 10 - 1, Mouse.getY() - 1, Mouse.getX() - 10 - 1, Mouse.getY() - 1);
        g.drawLine(Mouse.getX() + 10 + 1, Mouse.getY() - 1, Mouse.getX() - 10 - 1, Mouse.getY() + 1);
        g.drawLine(Mouse.getX() + 10 + 1, Mouse.getY() - 1, Mouse.getX() - 10 - 1, Mouse.getY() - 1);
        g.drawLine(Mouse.getX() + 10 + 1, Mouse.getY() - 1, Mouse.getX() - 10 + 1, Mouse.getY() - 1);
        g.drawLine(Mouse.getX() + 10 - 1, Mouse.getY() + 1, Mouse.getX() - 10 - 1, Mouse.getY() - 1);
        g.drawLine(Mouse.getX() + 10 - 1, Mouse.getY() - 1, Mouse.getX() - 10 + 1, Mouse.getY() - 1);
        g.drawLine(Mouse.getX() + 10 + 1, Mouse.getY() + 1, Mouse.getX() - 10 - 1, Mouse.getY() - 1);
        g.drawLine(Mouse.getX() + 10 - 1, Mouse.getY() - 1, Mouse.getX() - 10 - 1, Mouse.getY() + 1);

        g.drawLine(Mouse.getX() + 1, Mouse.getY() + 10 + 1, Mouse.getX() + 1, Mouse.getY() - 10 + 1);
        g.drawLine(Mouse.getX() + 1, Mouse.getY() + 10 - 1, Mouse.getX() + 1, Mouse.getY() - 10 + 1);
        g.drawLine(Mouse.getX() + 1, Mouse.getY() + 10 + 1, Mouse.getX() - 1, Mouse.getY() - 10 + 1);
        g.drawLine(Mouse.getX() + 1, Mouse.getY() + 10 + 1, Mouse.getX() + 1, Mouse.getY() - 10 - 1);
        g.drawLine(Mouse.getX() - 1, Mouse.getY() + 10 + 1, Mouse.getX() + 1, Mouse.getY() - 10 + 1);
        g.drawLine(Mouse.getX() - 1, Mouse.getY() + 10 - 1, Mouse.getX() + 1, Mouse.getY() - 10 + 1);
        g.drawLine(Mouse.getX() - 1, Mouse.getY() + 10 + 1, Mouse.getX() - 1, Mouse.getY() - 10 + 1);
        g.drawLine(Mouse.getX() - 1, Mouse.getY() + 10 + 1, Mouse.getX() + 1, Mouse.getY() - 10 - 1);
        g.drawLine(Mouse.getX() - 1, Mouse.getY() + 10 - 1, Mouse.getX() - 1, Mouse.getY() - 10 - 1);
        g.drawLine(Mouse.getX() + 1, Mouse.getY() + 10 - 1, Mouse.getX() - 1, Mouse.getY() - 10 + 1);
        g.drawLine(Mouse.getX() + 1, Mouse.getY() + 10 - 1, Mouse.getX() - 1, Mouse.getY() - 10 - 1);
        g.drawLine(Mouse.getX() + 1, Mouse.getY() + 10 - 1, Mouse.getX() + 1, Mouse.getY() - 10 - 1);
        g.drawLine(Mouse.getX() - 1, Mouse.getY() + 10 + 1, Mouse.getX() - 1, Mouse.getY() - 10 - 1);
        g.drawLine(Mouse.getX() - 1, Mouse.getY() + 10 - 1, Mouse.getX() + 1, Mouse.getY() - 10 - 1);
        g.drawLine(Mouse.getX() + 1, Mouse.getY() + 10 + 1, Mouse.getX() - 1, Mouse.getY() - 10 - 1);
        g.drawLine(Mouse.getX() - 1, Mouse.getY() + 10 - 1, Mouse.getX() - 1, Mouse.getY() - 10 + 1);

        g.setColor(Color.WHITE);

        if (Mouse.isPressed()) {
            g.setColor(Color.RED);
        }

        g.drawLine(Mouse.getX() + 10, Mouse.getY(), Mouse.getX() - 10, Mouse.getY());
        g.drawLine(Mouse.getX(), Mouse.getY() + 10, Mouse.getX(), Mouse.getY() - 10);
    }
}

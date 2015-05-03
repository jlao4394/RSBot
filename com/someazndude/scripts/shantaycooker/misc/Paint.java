package com.someazndude.scripts.shantaycooker.misc;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.Timer;

import java.awt.*;
import java.text.DecimalFormat;

public class Paint {
    private static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###");
    private static final Timer time = new Timer(0);
    public static int itemsUsed;

    public static void onRepaint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        g.setFont(new Font("Times New Roman", 0, 14));
        g.setColor(Color.BLACK);
        g.drawString(String.format("Time Running: %s", time.toElapsedString()), 3 + 1, 356 + 1);
        g.drawString(String.format("Time Running: %s", time.toElapsedString()), 3 + 1, 356 - 1);
        g.drawString(String.format("Time Running: %s", time.toElapsedString()), 3 - 1, 356 + 1);
        g.drawString(String.format("Time Running: %s", time.toElapsedString()), 3 - 1, 356 - 1);

        g.drawString(String.format("Items Used: %s", decimalFormat.format(itemsUsed)), 5 + 1, 371 + 1);
        g.drawString(String.format("Items Used: %s", decimalFormat.format(itemsUsed)), 5 + 1, 371 - 1);
        g.drawString(String.format("Items Used: %s", decimalFormat.format(itemsUsed)), 5 - 1, 371 + 1);
        g.drawString(String.format("Items Used: %s", decimalFormat.format(itemsUsed)), 5 - 1, 371 - 1);

        g.setColor(Color.WHITE);
        g.drawString(String.format("Time Running: %s", time.toElapsedString()), 3, 356);
        g.drawString(String.format("Items Used: %s", decimalFormat.format(itemsUsed)), 5, 371);

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

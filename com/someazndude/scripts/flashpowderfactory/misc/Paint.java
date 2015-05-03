package com.someazndude.scripts.flashpowderfactory.misc;

import com.someazndude.scripts.flashpowderfactory.factory.wrappers.Room;
import com.someazndude.scripts.flashpowderfactory.nodes.PlayFactory;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Paint {
    private static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###");
    private static final Timer time = new Timer(0);
    public static int brianPoints = 0;
    public static int brianPointsAdded = 0;

    public static void onRepaint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        double brianPointsPerHour = Math.abs((double) brianPointsAdded * ((double) 3600000 / (double) time.getElapsed()));
        double agilityExp = brianPoints * 95;
        double agilityExpAdded = brianPointsAdded * 95;
        double agilityExpPerHour = brianPointsPerHour * 95;

        if (PlayFactory.map != null) {
            List<Room> path = PlayFactory.map.getPath();
            if (path != null) {
                List<Rectangle> rectangles = new ArrayList<>();

                Point lastPoint = null;
                for (Room room : path) {
                    Tile tile = room.getCenterTile();
                    if (tile.isOnMap()) {
                        Point point = tile.getMapPoint();
                        if (lastPoint != null) {
                            g.drawLine(lastPoint.x, lastPoint.y, point.x, point.y);
                        }
                        lastPoint = point;
                        rectangles.add(new Rectangle(point.x - 5, point.y - 5, 10, 10));
                    } else break;
                }

                for (Rectangle rectangle : rectangles) {
                    g.setColor(Color.GREEN);
                    g.fill(rectangle);
                }
            }
        }

        g.setFont(new Font("Times New Roman", 0, 14));
        g.setColor(Color.BLACK);

        g.drawString(String.format("Time Running: %s", time.toElapsedString()), 3 + 1, 311 + 1);
        g.drawString(String.format("Time Running: %s", time.toElapsedString()), 3 + 1, 311 - 1);
        g.drawString(String.format("Time Running: %s", time.toElapsedString()), 3 - 1, 311 + 1);
        g.drawString(String.format("Time Running: %s", time.toElapsedString()), 3 - 1, 311 - 1);

        g.drawString(String.format("Brian Points: %s(%s)", decimalFormat.format(brianPoints), decimalFormat.format(brianPointsAdded)), 5 + 1, 326 + 1);
        g.drawString(String.format("Brian Points: %s(%s)", decimalFormat.format(brianPoints), decimalFormat.format(brianPointsAdded)), 5 + 1, 326 - 1);
        g.drawString(String.format("Brian Points: %s(%s)", decimalFormat.format(brianPoints), decimalFormat.format(brianPointsAdded)), 5 - 1, 326 + 1);
        g.drawString(String.format("Brian Points: %s(%s)", decimalFormat.format(brianPoints), decimalFormat.format(brianPointsAdded)), 5 - 1, 326 - 1);

        g.drawString(String.format("Brian Points/Hr: %s", decimalFormat.format(brianPointsPerHour)), 5 + 1, 341 + 1);
        g.drawString(String.format("Brian Points/Hr: %s", decimalFormat.format(brianPointsPerHour)), 5 + 1, 341 - 1);
        g.drawString(String.format("Brian Points/Hr: %s", decimalFormat.format(brianPointsPerHour)), 5 - 1, 341 + 1);
        g.drawString(String.format("Brian Points/Hr: %s", decimalFormat.format(brianPointsPerHour)), 5 - 1, 341 - 1);

        g.drawString(String.format("Potential Agility Exp: %s(%s)", decimalFormat.format(agilityExp), decimalFormat.format(agilityExpAdded)), 5 + 1, 356 + 1);
        g.drawString(String.format("Potential Agility Exp: %s(%s)", decimalFormat.format(agilityExp), decimalFormat.format(agilityExpAdded)), 5 + 1, 356 - 1);
        g.drawString(String.format("Potential Agility Exp: %s(%s)", decimalFormat.format(agilityExp), decimalFormat.format(agilityExpAdded)), 5 - 1, 356 + 1);
        g.drawString(String.format("Potential Agility Exp: %s(%s)", decimalFormat.format(agilityExp), decimalFormat.format(agilityExpAdded)), 5 - 1, 356 - 1);

        g.drawString(String.format("Potential Agility Exp/Hr: %s", decimalFormat.format(agilityExpPerHour)), 5 + 1, 371 + 1);
        g.drawString(String.format("Potential Agility Exp/Hr: %s", decimalFormat.format(agilityExpPerHour)), 5 + 1, 371 - 1);
        g.drawString(String.format("Potential Agility Exp/Hr: %s", decimalFormat.format(agilityExpPerHour)), 5 - 1, 371 + 1);
        g.drawString(String.format("Potential Agility Exp/Hr: %s", decimalFormat.format(agilityExpPerHour)), 5 - 1, 371 - 1);

        g.setColor(Color.WHITE);
        g.drawString(String.format("Time Running: %s", time.toElapsedString()), 3, 311);
        g.drawString(String.format("Brian Points: %s(%s)", decimalFormat.format(brianPoints), decimalFormat.format(brianPointsAdded)), 5, 326);
        g.drawString(String.format("Brian Points/Hr: %s", decimalFormat.format(brianPointsPerHour)), 5, 341);
        g.drawString(String.format("Potential Agility Exp: %s(%s)", decimalFormat.format(agilityExp), decimalFormat.format(agilityExpAdded)), 5, 356);
        g.drawString(String.format("Potential Agility Exp/Hr: %s", decimalFormat.format(agilityExpPerHour)), 5, 371);

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

package com.someazndude.scripts.api.methods.util;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.Tile;

import java.awt.*;

public class Util {
    public static double distanceBetween(final Point current, final Point destination) {
        return Math.sqrt(((current.x - destination.x) * (current.x - destination.x)) + ((current.y - destination.y) * (current.y - destination.y)));
    }

    public static Tile[] get4SurroundingTiles(Tile tile) {
        return new Tile[]{tile.derive(-1, 0), tile.derive(0, 1), tile.derive(1, 0), tile.derive(0, -1)};
    }

    public static Tile[] get8SurroundingTiles(Tile tile) {
        return new Tile[]{tile.derive(-1, 1), tile.derive(0, 1), tile.derive(1, 1), tile.derive(-1, 0), tile.derive(1, 0), tile.derive(-1, -1), tile.derive(0, -1), tile.derive(1, -1)};
    }

    public static Tile closestTile(Tile[] tiles, Tile destination) {
        Tile closest = null;

        for (Tile tile : tiles) {
            if (closest != null) {
                if (Calculations.distance(tile, destination) < Calculations.distance(closest, destination)) {
                    closest = tile;
                }
            } else closest = tile;
        }
        return closest;
    }

    public static double manhattanDistance(Locatable locatable1, Locatable locatable2) {
        return Math.max(Math.abs(locatable1.getLocation().getX() - locatable2.getLocation().getX()), Math.abs(locatable1.getLocation().getY() - locatable2.getLocation().getY()));
    }
}

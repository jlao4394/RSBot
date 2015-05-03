package com.someazndude.scripts.api.methods.actions;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.util.Random;

import java.awt.*;

public class Mouse extends org.powerbot.game.api.methods.input.Mouse {
    public static void moveRandomly() {
        Dimension game = Game.getDimensions();
        Mouse.move(Random.nextInt(0, game.width), Random.nextInt(0, game.height));
    }

    public static void moveToRandomSide() {
        Dimension game = Game.getDimensions();

        switch (Random.nextInt(0, 3 + 1)) {
            case 0: {
                Mouse.move(Random.nextInt(0, game.width), 0); //Top
                break;
            }

            case 1: {
                Mouse.move(Random.nextInt(0, game.width), game.height); //Bottom
                break;
            }

            case 2: {
                Mouse.move(0, Random.nextInt(0, game.height)); //Left
                break;
            }

            case 3: {
                Mouse.move(game.width, Random.nextInt(0, game.height));
                break;
            }
        }
    }
}

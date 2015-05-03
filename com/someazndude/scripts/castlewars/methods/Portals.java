package com.someazndude.scripts.castlewars.methods;

import com.someazndude.scripts.castlewars.constants.Portal;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.util.Random;

public class Portals {
    public static Portal chosenPortal = null;
    public static Portal lastWon = null;
    public static Portal lastLost = null;
    public static int redWins = 0;
    public static int blueWins = 0;
    public static int failTick = 0;
    private static int failNumber = Random.nextInt(4, 8 + 1);

    public static Portal getPortal() {
        if (Widgets.get(59, 0).isOnScreen()) {
            return Portal.RED_PORTAL;
        } else if (Widgets.get(58, 0).isOnScreen()) {
            return Portal.BLUE_PORTAL;
        }

        if (failTick > failNumber) {
            failNumber = Random.nextInt(4, 8 + 1);
            return Portal.GREEN_PORTAL;
        }

        switch (chosenPortal) {
            case LAST_WON: {
                if (lastWon != null) {
                    return lastWon;
                }
                break;
            }

            case LAST_LOST: {
                if (lastLost != null) {
                    return lastLost;
                }
                break;
            }

            case MOST_WON: {
                if (redWins > blueWins) {
                    return Portal.RED_PORTAL;
                } else if (blueWins > redWins) {
                    return Portal.BLUE_PORTAL;
                }
                break;
            }

            default: {
                return chosenPortal;
            }
        }

        return Portal.GREEN_PORTAL;
    }
}

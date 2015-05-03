package com.someazndude.scripts.api.methods.actions.walking;

import com.someazndude.scripts.api.interfaces.Condition;
import com.someazndude.scripts.api.methods.actions.Camera;
import com.someazndude.scripts.api.methods.util.Wait;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.TilePath;

public class Walking extends org.powerbot.game.api.methods.Walking {

    private static int turnNumber = Random.nextInt(6, 12);
    private static int runNumber = Random.nextInt(30, 51);

    public static void walkToEntity(final Entity entity) {
        if (Settings.get(463) != 1 && getEnergy() > runNumber) {
            setRun(true);
            runNumber = Random.nextInt(30, 51);
        }

        if (Walking.walk((Locatable) entity)) {
            if (Wait.waitFor(2000, 3000, new Condition() {

                @Override
                public boolean complete() {
                    return Players.getLocal() != null && Players.getLocal().isMoving();
                }

                @Override
                public boolean fail() {
                    return false;
                }

                @Override
                public void doWhile() {
                }
            })) {
                Wait.waitFor(2000, 3000, new Condition() {
                    @Override
                    public boolean complete() {
                        return (Players.getLocal() != null && !Players.getLocal().isMoving()) || (entity.isOnScreen() && ((Locatable) entity).getLocation().isOnScreen());
                    }

                    @Override
                    public boolean fail() {
                        return false;
                    }

                    @Override
                    public void doWhile() {
                        if (Calculations.distanceTo((Locatable) entity) < turnNumber) {
                            Camera.turnToConcurrently((Locatable) entity);
                            turnNumber = Random.nextInt(6, 12);
                        }
                    }
                });
            }
        }
    }

    public static void walkPathToEntity(final TilePath path, final Entity entity) {
        if (Settings.get(463) != 1 && getEnergy() > runNumber) {
            setRun(true);
            runNumber = Random.nextInt(30, 51);
        }

        final Tile next = path.getNext();

        if (next != null) {
            if (Walking.walk(next)) {
                if (Wait.waitFor(2000, 3000, new Condition() {

                    @Override
                    public boolean complete() {
                        return Players.getLocal().isMoving();
                    }

                    @Override
                    public boolean fail() {
                        return false;
                    }

                    @Override
                    public void doWhile() {
                    }
                })) {
                    Wait.waitFor(2000, 3000, new Condition() {
                        @Override
                        public boolean complete() {
                            return !Players.getLocal().isMoving() || !path.getNext().equals(next) || (entity != null && (entity.isOnScreen() && ((Locatable) entity).getLocation().isOnScreen()));
                        }

                        @Override
                        public boolean fail() {
                            return false;
                        }

                        @Override
                        public void doWhile() {
                            if (entity != null && Calculations.distanceTo((Locatable) entity) < turnNumber) {
                                Camera.turnToConcurrently((Locatable) entity);
                                turnNumber = Random.nextInt(6, 12);
                            }
                        }
                    });
                }
            }
        }
    }
}

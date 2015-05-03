package com.someazndude.scripts.flashpowderfactory.nodes;

import com.someazndude.scripts.api.interfaces.Condition;
import com.someazndude.scripts.api.methods.actions.Bank;
import com.someazndude.scripts.api.methods.actions.Inventory;
import com.someazndude.scripts.api.methods.actions.walking.Walking;
import com.someazndude.scripts.api.methods.util.Wait;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.ChatOptions;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class EnterFactory extends Node {
    private NPC brian;
    private Entity banker;
    private final Tile brianTile = new Tile(3037, 4968, 0);
    private final Tile bankTile = new Tile(3031, 4957, 0);
    private final WidgetChild gameScreen = Widgets.get(1180, 6);

    @Override
    public boolean activate() {
        brian = NPCs.getNearest(14706);
        banker = Bank.getNearest();
        return brian != null || banker != null;
    }

    @Override
    public void execute() {
        if (Inventory.getCount() != 0 || Bank.isOpen()) {
            if (Bank.isOpen()) {
                if (Inventory.getCount() != 0) {
                    Bank.depositInventory();
                } else Bank.close();
            } else {
                if (banker != null) {
                    if (banker.isOnScreen()) {
                        if (banker.interact("Bank")) {
                            Wait.waitFor(4000, 4500, new Condition() {
                                @Override
                                public boolean complete() {
                                    return Bank.isOpen();
                                }

                                @Override
                                public boolean fail() {
                                    return false;
                                }

                                @Override
                                public void doWhile() {
                                }
                            });
                        }
                    } else Walking.walkToEntity(banker);
                } else Walking.walk(bankTile);
            }
        } else if (ChatOptions.canContinue()) {
            if (ChatOptions.getContinueOption().select(true) == 0) {
                if (Wait.waitFor(5000, 6000, new Condition() {
                    @Override
                    public boolean complete() {
                        return Game.getClientState() != 11;
                    }

                    @Override
                    public boolean fail() {
                        return false;
                    }

                    @Override
                    public void doWhile() {
                    }
                })) {
                    if (Wait.waitFor(20000, 25000, new Condition() {
                        @Override
                        public boolean complete() {
                            return Game.getClientState() == 11 && gameScreen.isOnScreen() && Players.getLocal() != null;
                        }

                        @Override
                        public boolean fail() {
                            return false;
                        }

                        @Override
                        public void doWhile() {
                        }
                    })) {
                        if (PlayFactory.map != null) {
                            PlayFactory.map.setLastRoom(null);
                        }
                    }
                }
            }
        } else if (brian != null) {
            if (brian.isOnScreen()) {
                if (brian.interact("Quick start")) {
                    Wait.waitFor(4000, 4500, new Condition() {
                        @Override
                        public boolean complete() {
                            return ChatOptions.canContinue();
                        }

                        @Override
                        public boolean fail() {
                            return false;
                        }

                        @Override
                        public void doWhile() {
                        }
                    });
                }
            } else Walking.walkToEntity(brian);
        } else Walking.walkToEntity(brianTile);
    }
}
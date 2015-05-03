package com.someazndude.scripts.castlewars.nodes;

import com.someazndude.scripts.api.interfaces.Condition;
import com.someazndude.scripts.api.methods.actions.Action;
import com.someazndude.scripts.api.methods.actions.Idle;
import com.someazndude.scripts.api.methods.actions.walking.Walking;
import com.someazndude.scripts.api.methods.util.Wait;
import com.someazndude.scripts.castlewars.CastleWars;
import com.someazndude.scripts.castlewars.constants.Helm;
import com.someazndude.scripts.castlewars.constants.Portal;
import com.someazndude.scripts.castlewars.methods.Portals;
import com.someazndude.scripts.castlewars.misc.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import javax.swing.table.DefaultTableModel;

public class JoinGame extends Node {

    private boolean needWait = false;

    private final DefaultTableModel tableModel = (DefaultTableModel) CastleWars.gui.table.getModel();

    private final WidgetChild close = Widgets.get(985, 77);
    private final WidgetChild outcome = Widgets.get(985, 78);
    private final WidgetChild redScore = Widgets.get(985, 13);
    private final WidgetChild blueScore = Widgets.get(985, 14);

    private Portal portal;

    private SceneObject outsidePortal;
    private WidgetChild helm;


    @Override
    public boolean activate() {
        portal = Portals.getPortal();
        outsidePortal = SceneEntities.getNearest(portal.getOutsideId());
        Helm next = tableModel.getRowCount() != 0 ? (Helm) tableModel.getValueAt(0, 0) : null;
        helm = next != null ? Widgets.get(1127, next.getWidgetChild()) : Widgets.get(1127, Helm.values()[Random.nextInt(0, Helm.values().length)].getWidgetChild());

        return portal != null ? outsidePortal != null : false;
    }

    @Override
    public void execute() {

        Idle.endTimer();

        if (needWait) {

            Task.sleep(0, 20000);

            Wait.waitFor(1000, 1500, new Condition() {
                @Override
                public boolean complete() {
                    return close.isOnScreen();
                }

                @Override
                public boolean fail() {
                    return false;
                }

                @Override
                public void doWhile() {
                }
            });

            needWait = false;

        } else if (close.isOnScreen()) {

            Variables.gamesPlayed++;

            if (outcome.getText().contains("won")) {
                Variables.gamesWon++;
            }

            if (Integer.parseInt(redScore.getText()) > Integer.parseInt(blueScore.getText())) {
                Portals.lastWon = Portal.RED_PORTAL;
                Portals.redWins++;
                Portals.lastLost = Portal.BLUE_PORTAL;
            } else if (Integer.parseInt(blueScore.getText()) > Integer.parseInt(redScore.getText())) {
                Portals.lastWon = Portal.BLUE_PORTAL;
                Portals.blueWins++;
                Portals.lastLost = Portal.RED_PORTAL;
            } else {
                Portals.lastWon = Portal.GREEN_PORTAL;
                Portals.lastLost = Portal.GREEN_PORTAL;
            }

            if (close.interact("Close")) {
                Wait.waitFor(4000, 4500, new Condition() {
                    @Override
                    public boolean complete() {
                        return !close.isOnScreen();
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
        } else if (helm.isOnScreen()) {
            if (helm.click(true)) {
                if (Wait.waitFor(4000, 4500, new Condition() {
                    @Override
                    public boolean complete() {
                        return !helm.isOnScreen();
                    }

                    @Override
                    public boolean fail() {
                        return false;
                    }

                    @Override
                    public void doWhile() {
                    }
                })) {
                    if (tableModel.getRowCount() != 0) {
                        tableModel.removeRow(0);
                    }
                }
            }
        } else if (outsidePortal != null) {
            if (outsidePortal.isOnScreen() && outsidePortal.getLocation().isOnScreen()) {
                if (Action.interact(outsidePortal, "Enter")) {
                    final int lastFailTick = Portals.failTick;

                    if (Wait.waitFor(4000, 4500, new Condition() {
                        @Override
                        public boolean complete() {
                            return SceneEntities.getNearest(portal.getOutsideId()) == null;
                        }

                        @Override
                        public boolean fail() {
                            return Portals.failTick > lastFailTick;
                        }

                        @Override
                        public void doWhile() {
                        }
                    })) {
                        Portals.failTick = 0;
                        Idle.resetTimer();
                        needWait = true;
                    } else Task.sleep(250, 1000);
                } else Walking.walkToEntity(outsidePortal);
            } else Walking.walkToEntity(outsidePortal);
        }
    }
}

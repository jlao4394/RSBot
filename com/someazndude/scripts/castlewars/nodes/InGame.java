package com.someazndude.scripts.castlewars.nodes;

import com.someazndude.scripts.api.interfaces.Condition;
import com.someazndude.scripts.api.methods.actions.Idle;
import com.someazndude.scripts.api.methods.actions.walking.Walking;
import com.someazndude.scripts.api.methods.util.Wait;
import com.someazndude.scripts.castlewars.methods.Portals;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class InGame extends Node {

    private boolean needWait = true;

    @Override
    public boolean activate() {
        return Widgets.get(58, 0).isOnScreen() || Widgets.get(59, 0).isOnScreen();
    }

    @Override
    public void execute() {

        final SceneObject ladder = SceneEntities.getNearest(Portals.getPortal().getLadderId());

        if (ladder != null) {
            Idle.endTimer();

            if (!needWait) {
                if (ladder.isOnScreen() && ladder.getLocation().isOnScreen()) {
                    if (ladder.interact("Climb-up")) {
                        if (Wait.waitFor(4000, 4500, new Condition() {

                            @Override
                            public boolean complete() {
                                return SceneEntities.getNearest(ladder.getId()) == null;
                            }

                            @Override
                            public boolean fail() {
                                return false;
                            }

                            @Override
                            public void doWhile() {
                            }
                        })) {
                            needWait = true;
                            Idle.resetTimer();
                        }
                    } else Walking.walkToEntity(ladder);
                } else Walking.walkToEntity(ladder);
            } else {
                Task.sleep(0, 20000);
                needWait = false;
            }
        } else Idle.idle();
    }
}
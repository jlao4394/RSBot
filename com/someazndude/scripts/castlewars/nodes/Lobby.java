package com.someazndude.scripts.castlewars.nodes;

import com.someazndude.scripts.api.interfaces.Condition;
import com.someazndude.scripts.api.methods.actions.Idle;
import com.someazndude.scripts.api.methods.util.Wait;
import com.someazndude.scripts.castlewars.methods.Portals;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.ChatOptions;
import org.powerbot.game.api.wrappers.widget.ChatOption;

public class Lobby extends Node {
    @Override
    public boolean activate() {
        return Portals.getPortal() != null ? SceneEntities.getNearest(Portals.getPortal().getInsideId()) != null : false;
    }

    @Override
    public void execute() {

        final ChatOption yes = ChatOptions.getOption("Yes, please!");
        final ChatOption confirm = ChatOptions.getContinueOption();

        if (yes != null) {
            Task.sleep(0, 5000);
            if (yes.select(true) == 0) {
                Wait.waitFor(4000, 4500, new Condition() {
                    @Override
                    public boolean complete() {
                        return ChatOptions.getOption("Yes, please!") == null;
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
        } else if (ChatOptions.canContinue()) {
            if (confirm.select(true) == 0) {
                Wait.waitFor(4000, 4500, new Condition() {
                    @Override
                    public boolean complete() {
                        return !ChatOptions.canContinue() || !confirm.equals(ChatOptions.getContinueOption());
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
        } else Idle.idle();
    }
}
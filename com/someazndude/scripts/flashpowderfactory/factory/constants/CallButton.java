package com.someazndude.scripts.flashpowderfactory.factory.constants;

public enum CallButton {
    ALL_BUTTONS(64693, 64677, 64678, 64679, 64680, 64689, 64690, 64691, 64692, 64685, 64686, 64687, 64688, 64681, 64682, 64683, 64684),
    LOCKED(64693),
    UNLOCKED(64677, 64678, 64679, 64680, 64689, 64690, 64691, 64692, 64685, 64686, 64687, 64688, 64681, 64682, 64683, 64684);

    private final int[] ids;

    private CallButton(int... ids) {
        this.ids = ids;
    }

    public int[] getIds() {
        return ids;
    }
}

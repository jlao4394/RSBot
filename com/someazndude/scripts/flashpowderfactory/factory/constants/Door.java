package com.someazndude.scripts.flashpowderfactory.factory.constants;

public enum Door {
    ALL_DOORS(64676, 64675, 64674),
    UNLOCKED_DOOR(64676),
    LOCKED_DOOR(64674, 64675);

    private final int[] ids;

    private Door(int... ids) {
        this.ids = ids;
    }

    public int[] getIds() {
        return ids;
    }
}

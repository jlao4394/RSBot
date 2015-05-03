package com.someazndude.scripts.flashpowderfactory.factory.constants;

public enum Obstacle {
    ALL_OBSTACLES("", 64668, 64665, 64667, 64672, 64669, 64671, 64670, 64575),
    INCONVENIENT_WALL("Squeeze through", 64668),
    GEAR_WHEELS("Leap through", 64575),
    GLOWING_VOID("Cross", 64665),
    ENERGY_FIELD("Traverse", 64667),
    VENT_CHAMBER("Pass through", 64672),
    UNSTABLE_FLOOR("Run across", 64669),
    POORLY_STACKED_BOXES("Navigate", 64671),
    COUNTER_WEIGHTS("Leap across", 64670);

    private final String interaction;
    private final int[] ids;

    private Obstacle(String interaction, int... ids) {
        this.ids = ids;
        this.interaction = interaction;
    }

    public String getInteraction() {
        return interaction;
    }

    public int[] getIds() {
        return ids;
    }
}

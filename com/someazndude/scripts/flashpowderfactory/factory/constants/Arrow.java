package com.someazndude.scripts.flashpowderfactory.factory.constants;

public enum Arrow {
    BLUE(-15816706, -15751170),
    ORANGE(-1062896, -1062897, -2249146),
    RED(-108537, -108281);

    private final int[] RGBs;

    private Arrow(final int... RGBs) {
        this.RGBs = RGBs;
    }

    public int[] getRGBs() {
        return RGBs;
    }
}

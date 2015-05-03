package com.someazndude.scripts.castlewars.constants;

public enum Portal {
    RED_PORTAL("Red Portal", 4388, new int[]{4390}, new int[]{6281}),
    GREEN_PORTAL("Green Portal", 4408, new int[]{4390, 4389}, new int[]{6281, 6280}),
    BLUE_PORTAL("Blue Portal", 4387, new int[]{4389}, new int[]{6280}),
    LAST_WON("Last Won", -1, new int[]{-1}, new int[]{-1}),
    LAST_LOST("Last Lost", -1, new int[]{-1}, new int[]{-1}),
    MOST_WON("Most Won", -1, new int[]{-1}, new int[]{-1});

    private final String name;
    private final int outsideId;
    private final int[] insideId;
    private final int[] ladderId;

    private Portal(String name, int outsideId, int[] insideId, int[] ladderId) {
        this.name = name;
        this.outsideId = outsideId;
        this.insideId = insideId;
        this.ladderId = ladderId;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getOutsideId() {
        return outsideId;
    }

    public int[] getInsideId() {
        return insideId;
    }

    public int[] getLadderId() {
        return ladderId;
    }
}

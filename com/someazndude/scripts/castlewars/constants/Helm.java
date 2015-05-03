package com.someazndude.scripts.castlewars.constants;

public enum Helm {
    BATTLEMAGE_HELM("Battlemage Helm", 6),
    TRICKSTER_HELM("Trickster Helm", 7),
    VANGUARD_HELM("Vanguard Helm", 8);


    private final String name;
    private final int widgetChild;

    private Helm(String name, int widgetChild) {
        this.name = name;
        this.widgetChild = widgetChild;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getWidgetChild() {
        return widgetChild;
    }
}

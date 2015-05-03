package com.someazndude.scripts.flashpowderfactory.factory.constants;

public enum Machine {
    ALL_MACHINES("", 64664, 64663, 64662, 64661, 64660, 64659, 64658, 64657, 64637, 64642, 64644, 64649, 64647, 64646, 64643, 64645, 64648, 64650),
    CHARGE_MACHINE("Charge-funnel", 64664, 64663, 64662, 64661, 64660, 64659, 64658, 64657),
    MIXER_MACHINE("Mix powder", 64637),
    REAGENT_MACHINE("Fill flask", 64642, 64644, 64649, 64647, 64646, 64643, 64645, 64648),
    CATALYST_MACHINE("Unlock", 64650);

    private final String interaction;
    private final int[] ids;

    private Machine(String interaction, int... ids) {
        this.interaction = interaction;
        this.ids = ids;
    }

    public String getInteraction() {
        return interaction;
    }

    public int[] getIds() {
        return ids;
    }
}

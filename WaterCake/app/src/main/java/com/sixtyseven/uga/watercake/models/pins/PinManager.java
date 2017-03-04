package com.sixtyseven.uga.watercake.models.pins;

import java.util.HashSet;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Thor on 2017-03-01.
 * The purpose of this class is to enable this class and only this class to be changed to implement
 * a database to hold the pins.
 */

public class PinManager {
    private static Set<Pin> pins = loadPins();

    /**
     * Temporary method to load hardcoded pins. When permanence is added, should be replaced
     *
     * @return the pins that should be loaded at the start
     */
    private static Set<Pin> loadPins() {
        HashSet<Pin> preloaded = new HashSet<Pin>();
        preloaded.add(new Pin("Marker in Sydney", new Location(-34, 151)));
        preloaded.add(new Pin("Whole lot of water", new Location(-74, -70)));
        preloaded.add(new Pin("Mirage water", new Location(22, 11)));
        return preloaded;
    }

    /**
     * adds the pin to the set
     *
     * @param pin
     */
    public static void add(Pin pin) {
        pins.add(pin);
    }

    /**
     * removes the pin from the set
     *
     * @param pin
     */
    public static void remove(Pin pin) {
        pins.remove(pin);
    }

    /**
     * gives an UNMODIFIABLE set of pins - note that the pins themselves can be edited
     *
     * @return the unmodifiable set of pins
     */
    public static Set<Pin> getPins() {
        return Collections.unmodifiableSet(pins);
    }
}

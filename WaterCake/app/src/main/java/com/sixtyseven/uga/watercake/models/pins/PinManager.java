package com.sixtyseven.uga.watercake.models.pins;

import java.util.HashSet;
import java.util.Collections;
import java.util.Set;


public class PinManager {
    private static PinManager pinManager = new PinManager();
    private Set<Pin> pins;

    public static PinManager getPinManager() {
        return pinManager;
    }

    /**
     * Constructor - loads hard coded pins. Should load pins from other source later
     */
    private PinManager() {
        pins = new HashSet<Pin>();
        pins.add(new Pin("Marker in Sydney", new Location(-34, 151)));
        pins.add(new Pin("Whole lot of water", new Location(-74, -70)));
        pins.add(new Pin("Mirage water", new Location(22, 11)));
    }

    /**
     * adds the pin to the set
     *
     * @param pin
     */
    public void add(Pin pin) {
        pins.add(pin);
    }

    /**
     * removes the pin from the set
     *
     * @param pin
     */
    public void remove(Pin pin) {
        pins.remove(pin);
    }

    /**
     * gives an UNMODIFIABLE set of pins - note that the pins themselves can be edited
     *
     * @return the unmodifiable set of pins
     */
    public Set<Pin> getPins() {
        return Collections.unmodifiableSet(pins);
    }
}

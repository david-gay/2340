package com.sixtyseven.uga.watercake.models.pins;

import java.util.HashSet;
import java.util.Collections;
import java.util.Set;

import com.sixtyseven.uga.watercake.models.report.Location;

/**
 * Manager for containing Pins.
 */
public class PinManager {
    private static PinManager pinManager = new PinManager();
    private Set<Pin> pins;

    /**
     * Returns the current PinManager instance
     * @return the current PinManager instance
     */
    public static PinManager getInstance() {
        return pinManager;
    }

    /**
     * Constructor - loads hard coded pins. Should load pins from other source later
     */
    private PinManager() {
        pins = new HashSet<>();
        pins.add(new Pin("Marker in Sydney", new Location(-34, 151)));
        pins.add(new Pin("Whole lot of water", new Location(-74, -70)));
        pins.add(new Pin("Mirage water", new Location(22, 11)));
    }

    /**
     * Adds the pin to the set
     *
     * @param pin a pin to add to the pin set
     */
    public void add(Pin pin) {
        pins.add(pin);
    }

    /**
     * Removes the pin from the set
     *
     * @param pin the pin to remove from the map set
     */
    public void remove(Pin pin) {
        pins.remove(pin);
    }

    /**
     * Returns an UNMODIFIABLE set of pins - note that the pins themselves can be edited
     *
     * @return the unmodifiable set of pins
     */
    public Set<Pin> getPins() {
        return Collections.unmodifiableSet(pins);
    }
}

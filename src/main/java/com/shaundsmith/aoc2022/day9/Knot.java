package com.shaundsmith.aoc2022.day9;

import com.shaundsmith.aoc2022.Coordinate;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

class Knot {

    private final LinkedList<Coordinate> locations;

    Knot() {

        this.locations = new LinkedList<>();
        this.locations.addLast(new Coordinate(0, 0));
    }

    void updateLocation(Coordinate location) {
        this.locations.addLast(location);
    }

    Coordinate getCurrentLocation() {
        return locations.getLast();
    }

    Set<Coordinate> getUniqueLocations() {
        return new HashSet<>(locations);
    }

}

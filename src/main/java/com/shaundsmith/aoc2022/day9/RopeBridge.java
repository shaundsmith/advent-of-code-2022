package com.shaundsmith.aoc2022.day9;

import com.shaundsmith.aoc2022.Coordinate;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

class RopeBridge {

    private static final Map<Direction, Function<Coordinate, Coordinate>> MOVEMENT_MAP = Map.of(
            Direction.UP, c -> new Coordinate(c.x(), c.y() + 1),
            Direction.DOWN, c -> new Coordinate(c.x(), c.y() - 1),
            Direction.LEFT, c -> new Coordinate(c.x() - 1, c.y()),
            Direction.RIGHT, c -> new Coordinate(c.x() + 1, c.y()));


    private final Knot head = new Knot();
    private final LinkedList<Knot> tails = new LinkedList<>();

    RopeBridge(int numberOfTails) {
        for (int i = 0; i < numberOfTails; i++) {
            tails.add(new Knot());
        }
    }

    void move(Direction direction, int spaces) {
        for (int i = 0; i < spaces; i++) {
            move(direction);
        }
    }

    Set<Coordinate> getUniqueTailLocations() {
        return tails.getLast().getUniqueLocations();
    }

    private void move(Direction direction) {

        Coordinate currentLocation = head.getCurrentLocation();
        Coordinate newLocation = MOVEMENT_MAP.get(direction).apply(currentLocation);
        head.updateLocation(newLocation);
        System.out.println("Moving head to " + newLocation);
        moveTail(head, 0);
    }

    private void moveTail(Knot currentKnot, int tailIndex) {
        Knot tail = tails.get(tailIndex);

        if (isTailTooFarAwayStraightLine(currentKnot, tail)) {
            moveTailStraight(currentKnot, tail);
        } else if (isTailTooFarAwayDiagonally(currentKnot, tail)) {
            moveTailDiagonally(currentKnot, tail);
        }

        if (tailIndex < tails.size() - 1) {
            moveTail(tail, tailIndex + 1);
        }
    }

    private void moveTailDiagonally(Knot currentKnot, Knot tail) {

        Coordinate newLocation;
        // Up-Right
        if (currentKnot.getCurrentLocation().y() > tail.getCurrentLocation().y() &&
                currentKnot.getCurrentLocation().x() > tail.getCurrentLocation().x()) {
            newLocation = new Coordinate(tail.getCurrentLocation().x() + 1, tail.getCurrentLocation().y() + 1);
        // Down-Right
        } else if (currentKnot.getCurrentLocation().y() < tail.getCurrentLocation().y() &&
                currentKnot.getCurrentLocation().x() > tail.getCurrentLocation().x()) {
            newLocation = new Coordinate(tail.getCurrentLocation().x() + 1, tail.getCurrentLocation().y() - 1);
        // Up-Left
        } else if (currentKnot.getCurrentLocation().y() > tail.getCurrentLocation().y() &&
                currentKnot.getCurrentLocation().x() < tail.getCurrentLocation().x()) {
            newLocation = new Coordinate(tail.getCurrentLocation().x() - 1, tail.getCurrentLocation().y() + 1);
        // Down-Left
        } else {
            newLocation = new Coordinate(tail.getCurrentLocation().x() - 1, tail.getCurrentLocation().y() - 1);
        }
        System.out.println("Moving tail to " + newLocation);
        tail.updateLocation(newLocation);

    }

    private void moveTailStraight(Knot currentKnot, Knot tail) {
        Direction tailMovement;

        if (currentKnot.getCurrentLocation().y() > tail.getCurrentLocation().y()) {
            tailMovement = Direction.UP;
        } else if (currentKnot.getCurrentLocation().y() < tail.getCurrentLocation().y()) {
            tailMovement = Direction.DOWN;
        } else if (currentKnot.getCurrentLocation().x() > tail.getCurrentLocation().x()) {
            tailMovement = Direction.RIGHT;
        } else {
            tailMovement = Direction.LEFT;
        }

        var newLocation = MOVEMENT_MAP.get(tailMovement).apply(tail.getCurrentLocation());
        System.out.println("Moving tail to " + newLocation);
        tail.updateLocation(newLocation);
    }

    private boolean isTailTooFarAwayDiagonally(Knot lead, Knot tail) {
        return Math.abs(lead.getCurrentLocation().x() - tail.getCurrentLocation().x()) > 1 ||
                Math.abs(lead.getCurrentLocation().y() - tail.getCurrentLocation().y()) > 1;
    }

    private boolean isTailTooFarAwayStraightLine(Knot lead, Knot tail) {
        boolean isTooFarHorizontally = Math.abs(lead.getCurrentLocation().x() - tail.getCurrentLocation().x()) > 1 && lead.getCurrentLocation().y() == tail.getCurrentLocation().y();
        boolean isTooFarVertically = Math.abs(lead.getCurrentLocation().y() - tail.getCurrentLocation().y()) > 1 && lead.getCurrentLocation().x() == tail.getCurrentLocation().x();
        return isTooFarHorizontally || isTooFarVertically;
    }

}
